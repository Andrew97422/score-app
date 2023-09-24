package com.bigdata.application.service;

import com.bigdata.application.model.dto.ApplicationResponse;
import com.bigdata.application.model.dto.ScoringApplicationWithAuthRequest;
import com.bigdata.application.model.dto.ScoringApplicationWithoutAuthRequest;
import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.repository.ApplicationRepository;
import com.bigdata.lending.model.entity.GuideEntity;
import com.bigdata.lending.model.enums.LendingType;
import com.bigdata.user.model.entity.UserEntity;
import com.bigdata.user.model.enums.Role;
import com.bigdata.user.repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final UserRepository userRepository;

    private final ScoringService scoringService;

    private final MailSender mailSender;

    private final ApplicationUtils applicationUtils;

    public void addNewApplicationWithoutAuth(ScoringApplicationWithoutAuthRequest request) {
        var application = applicationUtils.mapToEntity(request);

        scoringService.score(application, request.getBirthday());
    }

    @Transactional
    public void addNewApplicationWithAuth(
            ScoringApplicationWithAuthRequest request, UserEntity user
    ) {
        var application = applicationUtils.mapToEntity(request, user);
        //sendEmail("nosoff.4ndr@yandex.ru", "andryushka.nosov.03@mail.ru", "KU");
        scoringService.score(application, user.getBirthday());
    }

    public void sendEmail(String toAddress, String subject, String message) {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("nosoff.4ndr@yandex.ru");
        simpleMailMessage.setTo("andryushka.nosov.03@mail.ru");
        simpleMailMessage.setSubject("KUKU");
        simpleMailMessage.setText("KUKUKUKU");
        mailSender.send(simpleMailMessage);
        //emailService.sendSimpleEmail(toAddress, subject, message);
        log.info("Message to email {} has been sent.", toAddress);
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsList(LendingType type, UserEntity user) {
        List<ApplicationResponse> applications;
        if (user.getRole().equals(Role.USER)) {
             applications = user.getApplicationsList().stream()
                     .filter(i -> i.getLendingType().equals(type))
                     .map(applicationUtils::mapToApplicationResponse).toList();
        } else {
            applications = applicationRepository.findAll().stream()
                    .filter(i -> i.getLendingType().equals(type))
                    .map(applicationUtils::mapToApplicationResponse).toList();
        }
        log.info("Found {} loan applications", applications.size());
        log.info("Response with applications is ready");

        return applications;
    }

    @Transactional
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

    public byte[] formPdfDocument(Integer id, UserEntity user) throws DocumentException, URISyntaxException, IOException {
        LoanApplicationEntity application;
        if (user.getRole().equals(Role.USER)) {
            application = user.getApplicationsList()
                    .stream().filter(i->i.getId() == id).findFirst().orElseThrow(IllegalArgumentException::new);
        } else {
            application = applicationRepository.getReferenceById(id);
        }

        List<GuideEntity> guides = scoringService.guides(application);
        guides = scoringService.filteredGuides(guides);

        log.info("Found {} suitable loan products for the user {}.", guides.size(), application.getId());
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

        Chunk chunk1 = new Chunk("У нас для Вас отличные новости!", font1);
        Paragraph paragraph1 = new Paragraph(chunk1);
        paragraph1.setSpacingBefore(img.getScaledHeight() - 10F);
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph1);

        Chunk chunk2;
        if (application.getLendingType().equals(LendingType.MORTGAGE)) {
            chunk2 = new Chunk("Вам доступно несколько ипотечных\n" +
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

        } else {
            chunk2 = new Chunk("""
                    Вам доступно несколько кредитных
                    предложений, выбрали самые выгодные
                     для Вас""", font1);

            Paragraph paragraph2 = new Paragraph(chunk2);
            paragraph2.setSpacingBefore(paragraph1.getSpacingAfter() + 10F);
            paragraph2.setSpacingAfter(15F);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph2);

            for (int i = 0; i < guides.size(); i++) {
                Chunk chunk = new Chunk((i + 1) + ".  Кредит " + guides.get(i).getName() +
                        ", для Вас ставка - " + String.format("%.2f", guides.get(i).getMinLoanRate()) + " %\n");
                chunk.setAnchor(guides.get(i).getUrl());
                Paragraph paragraph = new Paragraph(chunk);
                paragraph.setSpacingAfter(15F);
                paragraph.setSpacingBefore(paragraph.getSpacingAfter() + 10F);
                document.add(paragraph);
            }

        }
        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationById(Integer id, UserEntity user) {
        ApplicationResponse response;
        if (user.getRole().equals(Role.USER)) {
            response = applicationUtils.mapToApplicationResponse(user.getApplicationsList().stream()
                    .filter(i -> i.getId() == id).findFirst().orElseThrow(IllegalArgumentException::new));
        } else {
            response = applicationUtils.mapToApplicationResponse(applicationRepository.getReferenceById(id));
        }
        return response;
    }
}
