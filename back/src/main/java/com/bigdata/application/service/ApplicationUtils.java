package com.bigdata.application.service;

import com.bigdata.application.model.dto.ApplicationResponse;
import com.bigdata.application.model.dto.ScoringApplicationWithAuthRequest;
import com.bigdata.application.model.dto.ScoringApplicationWithoutAuthRequest;
import com.bigdata.application.model.entity.CurrentDebtLoadEntity;
import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.model.entity.WorkExperienceEntity;
import com.bigdata.application.model.enums.ApplicationStatus;
import com.bigdata.products.common.model.CommonEntity;
import com.bigdata.products.common.model.LendingType;
import com.bigdata.user.model.entity.UserEntity;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationUtils {
    public LoanApplicationEntity mapToEntity(ScoringApplicationWithAuthRequest request, UserEntity user) {
        CurrentDebtLoadEntity currentDebtLoadEntity = CurrentDebtLoadEntity.builder()
                .amountLoanPayments(request.getCurrentDebtLoad())
                .monthlyIncome(request.getMonthlyIncome())
                .countActiveLoans(request.getCountActiveLoans())
                .build();

        WorkExperienceEntity workExperienceEntity = WorkExperienceEntity.builder()
                .name(request.getWorkExperience()).build();

        return LoanApplicationEntity.builder()
                .workExperience(workExperienceEntity)
                .currentDebtLoad(currentDebtLoadEntity)
                .lendingType(request.getLendingType())
                .creditAmount(request.getAmount())
                .status(ApplicationStatus.IN_PROGRESS)
                .applicationDateTime(LocalDateTime.now())
                .loanTerm(request.getTerm())
                .isMilitary(request.isMilitary())
                .isStateEmployee(request.isStateEmployee())
                .isPsbClient(request.isPsbClient())
                .isFarEastInhabitant(request.isFarEastInhabitant())
                .isNewSubjectsResident(request.isNewSubjectsResident())
                .isItSpecialist(request.isItSpecialist())
                .user(user)
                .build();
    }

    public LoanApplicationEntity mapToEntity(ScoringApplicationWithoutAuthRequest request) {
        CurrentDebtLoadEntity currentDebtLoadEntity = CurrentDebtLoadEntity.builder()
                .amountLoanPayments(request.getCurrentDebtLoad())
                .monthlyIncome(request.getMonthlyIncome())
                .countActiveLoans(request.getCountActiveLoans())
                .build();

        return LoanApplicationEntity.builder()
                .workExperience(WorkExperienceEntity.builder().name(request.getWorkExperience()).build())
                .currentDebtLoad(currentDebtLoadEntity)
                .creditAmount(request.getAmount())
                .status(ApplicationStatus.IN_PROGRESS)
                .applicationDateTime(LocalDateTime.now())
                .loanTerm(request.getTerm())
                .isMilitary(request.isMilitary())
                .isStateEmployee(request.isStateEmployee())
                .isPsbClient(request.isPsbClient())
                .isFarEastInhabitant(request.isFarEastInhabitant())
                .isNewSubjectsResident(request.isNewSubjectsResident())
                .isItSpecialist(request.isItSpecialist())
                .user(null)
                .build();
    }

    public ApplicationResponse mapToApplicationResponse(LoanApplicationEntity application) {
        StringBuilder builder = new StringBuilder();
        builder.append(application.getUser().getLastName().trim());
        builder.append(" ");
        builder.append(application.getUser().getFirstName().trim());
        builder.append(" ");
        builder.append(application.getUser().getSurName().trim());
        builder.append(" ").append("(").append(application.getUser().getLogin().trim()).append(")");
        return ApplicationResponse.builder()
                .id(application.getId())
                .consentPersonalData(application.isConsentPersonalData())
                .consentRequestToCreditBureau(application.isConsentRequestToCreditBureau())
                .consentToAdvertising(application.isConsentToAdvertising())
                .downPayment(application.isDownPayment())
                .openLoans(application.isOpenLoans())
                .opportunityToOfferLoan(application.isOpportunityToOfferLoan())
                .amountLoanPayments(application.getCurrentDebtLoad().getAmountLoanPayments())
                .applicationDateTime(application.getApplicationDateTime())
                .countActiveLoans(application.getCurrentDebtLoad().getCountActiveLoans())
                .creditAmount(application.getCreditAmount())
                .isFarEastInhabitant(application.isFarEastInhabitant())
                .isItSpecialist(application.isItSpecialist())
                .monthlyIncome(application.getCurrentDebtLoad().getMonthlyIncome())
                .isStateEmployee(application.isStateEmployee())
                .lendingType(application.getLendingType())
                .workExperience(application.getWorkExperience().getName())
                .loanTerm(application.getLoanTerm())
                .isMilitary(application.isMilitary())
                .isPsbClient(application.isPsbClient())
                .isNewSubjectsResident(application.isNewSubjectsResident())
                .status(application.getStatus())
                .user(builder.toString())
                .userId(application.getUser().getId())
                .build();
    }

    public byte[] formPdfDoc(LoanApplicationEntity application,
                             List<CommonEntity> guides) throws DocumentException,
            IOException {

        //Path path = Paths.get("back/src/main/resources/img/logo.jpg");

        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        Image img = Image.getInstance("/img/logo.jpg");
        //Image img = Image.getInstance(path.toAbsolutePath().toString());
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
        } else {
            chunk2 = new Chunk("""
                    Вам доступно несколько кредитных
                    предложений, выбрали самые выгодные
                     для Вас""", font1);
        }

        Paragraph paragraph2 = new Paragraph(chunk2);
        paragraph2.setSpacingBefore(paragraph1.getSpacingAfter() + 10F);
        paragraph2.setSpacingAfter(15F);
        paragraph2.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph2);

        for (int i = 0; i < guides.size(); i++) {
            Chunk chunk = new Chunk((i + 1) + ".  " + guides.get(i).getName() +
                    ", для Вас ставка - " + String.format("%.2f", guides.get(i).getMinLoanRate()) + " %\n");
            chunk.setAnchor(guides.get(i).getUrl());
            chunk.setFont(font1);
            Paragraph paragraph = new Paragraph(chunk);
            paragraph.setSpacingAfter(15F);
            paragraph.setSpacingBefore(paragraph.getSpacingAfter() + 10F);
            document.add(paragraph);
        }
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
