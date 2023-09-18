package com.bigdata.application.service;

import com.bigdata.application.model.dto.ApplicationByTypeResponse;
import com.bigdata.application.model.dto.ScoringApplicationWithAuthRequest;
import com.bigdata.application.model.dto.ScoringApplicationWithoutAuthRequest;
import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.model.enums.ApplicationStatus;
import com.bigdata.application.repository.ApplicationRepository;
import com.bigdata.lending.model.entity.AutoLoanEntity;
import com.bigdata.lending.model.entity.ConsumerEntity;
import com.bigdata.lending.model.entity.GuideEntity;
import com.bigdata.lending.model.entity.MortgageEntity;
import com.bigdata.lending.model.enums.LendingType;
import com.bigdata.lending.repository.AutoLoanRepository;
import com.bigdata.lending.repository.ConsumerRepository;
import com.bigdata.lending.repository.MortgageRepository;
import com.bigdata.user.model.entity.UserEntity;
import com.bigdata.user.repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final AutoLoanRepository autoLoanRepository;
    private final ConsumerRepository consumerRepository;
    private final MortgageRepository mortgageRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public void addNewApplicationWithoutAuth(ScoringApplicationWithoutAuthRequest request) {
        var application = request.mapDtoToEntity();

        score(application, request.getBirthday(), request.getEmail());
    }

    public void addNewApplicationWithAuth(ScoringApplicationWithAuthRequest request, UserEntity user) throws Exception {
        var application = request.mapDtoToEntity(user);

        score(application, user.getBirthday(), user.getEmail());

        try {
            applicationRepository.save(application);
            log.info("Application {} was saved to the repository.", application.getId());
        } catch (Exception e) {
            log.error("Application {} wasn't saved. Reason: {}", application.getId(), e.getMessage());
            throw new Exception(e);
        }
    }

    private void score(LoanApplicationEntity application, LocalDate birthday,
                       String email2) {
        log.info("Application {} was registered.", application.getId());

        new Thread(() -> {
            float scoring = calculateScoring(application, birthday);
            log.info("The scoring is calculated for the user {}.", application.getId());

            application.setFinalScoring(scoring);

            if (scoring > 106) {
                List<GuideEntity> guides = guides(application);
                guides = filteredGuides(guides);

                if (guides.isEmpty()) {
                    log.info("No suitable loan products for the user {}.", application.getId());
                } else {
                    log.info("Found {} suitable loan products for the user {}.", guides.size(), application.getId());

                    String subject = "Ваш ответ по заявке, обнаружили для Вас доступные продукты.";
                    StringBuilder message = new StringBuilder("Для Вас обнаружено " +
                            guides.size() + " доступных продуктов.\n\n");
                    guides.forEach(i -> message.append(i.toMailString()));

                    sendEmail(email2, subject, message.toString());
                }
            } else {
                log.info("No suitable loan products for the user {}." +
                        " Scoring is not enough.", application.getId());
            }
        }).start();
    }

    private float calculateScoring(LoanApplicationEntity application, LocalDate birthday) {
        log.info("Calculate scoring for application {}, having {}", application.getId(), birthday);
        return application.scoreCalculate(birthday);
    }

    private List<GuideEntity> guides(LoanApplicationEntity loanApplication) {
        List<AutoLoanEntity> autoLoanEntities = autoLoanRepository
                .findByMinLoanAmountLessThan(loanApplication.getCreditAmount());
        List<MortgageEntity> mortgageEntities = mortgageRepository
                .findByMinLoanAmountLessThan(loanApplication.getCreditAmount());
        List<ConsumerEntity> consumerEntities = consumerRepository
                .findByMinLoanAmountLessThan(loanApplication.getCreditAmount());

        List<GuideEntity> guides = new ArrayList<>();
        guides.addAll(autoLoanEntities);
        guides.addAll(mortgageEntities);
        guides.addAll(consumerEntities);

        return guides;
    }

    private List<GuideEntity> filteredGuides(List<GuideEntity> guides) {
        return guides.stream()
                .sorted()
                .limit(5)
                .sorted((o1, o2) ->
                        (int) (o2.getMinLoanRate() - o1.getMinLoanRate()))
                .toList();
    }

    private void sendEmail(String toAddress, String subject, String message) {
        emailService.sendSimpleEmail(toAddress, subject, message);
        log.info("Message to email {} has been sent.", toAddress);
    }

    public List<ApplicationByTypeResponse> getApplicationsList(LendingType type, UserEntity user) {
        List<LoanApplicationEntity> applicationEntities =
                user.getApplicationsList().stream()
                        .filter(i -> i.getLendingType().equals(type)).toList();
        log.info("Found {} loan applications", applicationEntities.size());

        List<ApplicationStatus> statuses = applicationEntities
                .stream().map(LoanApplicationEntity::getStatus).toList();
        log.info("Found {} statuses for each applications", statuses.size());

        List<ApplicationByTypeResponse> response = new ArrayList<>();

        for (int i = 0; i < applicationEntities.size(); i++) {
            response.add(new ApplicationByTypeResponse(applicationEntities.get(i), statuses.get(i)));
        }
        log.info("Response with applications is ready");

        return response;
    }

    public void deleteApplication(Integer id, UserEntity user) {
        try {
            List<LoanApplicationEntity> newList = user.getApplicationsList();
            newList.removeIf(i->i.getId() == id);
            user.setApplicationsList(newList);
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Application {} was deleted", id);
    }

    public byte[] formPdfDocument(Integer id) throws DocumentException, URISyntaxException, IOException {
        LoanApplicationEntity application = applicationRepository.getReferenceById(id);
        List<GuideEntity> guides = guides(application);
        guides = filteredGuides(guides);

        if (guides.isEmpty()) {
            log.info("No suitable loan products for the user {}.", application.getId());
            return new byte[0];
        }
        else {
            log.info("Found {} suitable loan products for the user {}.", guides.size(), application.getId());
            if (application.getLendingType().equals(LendingType.MORTGAGE)) {
                Path path = Paths.get(ClassLoader.getSystemResource("img/logo.jpg").toURI());

                Document document = new Document();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, byteArrayOutputStream);

                Image img = Image.getInstance(path.toAbsolutePath().toString());
                img.scalePercent(PageSize.A4.getWidth() / img.getScaledWidth() * 100);
                img.setAbsolutePosition(0, PageSize.A4.getHeight() -
                        img.getScaledHeight()
                );

                document.open();
                document.setPageSize(PageSize.A4);
                document.newPage();

                document.add(img);

                BaseFont baseFont = BaseFont.createFont("fonts/Verdana-Bold.ttf",
                        BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

                com.itextpdf.text.Font font1 = new Font(baseFont, 16, Font.NORMAL);
                BaseColor color = new BaseColor(55, 56, 139);
                font1.setColor(color);

                Chunk chunk1 = new Chunk("У нас для вас отличные новости!", font1);
                Paragraph paragraph1 = new Paragraph(chunk1);
                paragraph1.setSpacingBefore(img.getScaledHeight() - 10F);
                paragraph1.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph1);

                Chunk chunk2 = new Chunk("Вам доступно несколько ипотечных\n" +
                        "предложений, вот самые выгодные из них:", font1);

                Paragraph paragraph2 = new Paragraph(chunk2);
                paragraph2.setSpacingBefore(paragraph1.getSpacingAfter() + 10F);
                paragraph2.setSpacingAfter(15F);
                paragraph2.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph2);

                for (int i = 0; i < guides.size(); i++) {
                    Chunk chunk = new Chunk((i + 1) + ".  " + guides.get(i).getName() +
                            ", для Вас ставка - " + String.format("%.2f", guides.get(i).getMinLoanRate()) + " %\n");
                    chunk.setAnchor(guides.get(i).getUrl());
                    Paragraph paragraph = new Paragraph(chunk);
                    paragraph.setSpacingAfter(15F);
                    paragraph.setSpacingBefore(paragraph.getSpacingAfter() + 10F);
                    document.add(paragraph);
                }

                document.close();
                return byteArrayOutputStream.toByteArray();
            }
        }
        return new byte[0];
    }
}
