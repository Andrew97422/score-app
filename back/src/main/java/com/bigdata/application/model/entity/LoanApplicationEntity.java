package com.bigdata.application.model.entity;

import com.bigdata.application.model.enums.ApplicationStatus;
import com.bigdata.lending.model.enums.LendingType;
import com.bigdata.user.model.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loan_application")
public class LoanApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "date_and_time_of_application")
    private LocalDateTime applicationDateTime;

    @Column(name = "consent_to_the_processing_of_personal_data")
    private boolean consentPersonalData;

    @Column(name = "consent_to_a_request_to_the_credit_bureau")
    private boolean consentRequestToCreditBureau;

    @Column(name = "consent_to_advertising")
    private boolean consentToAdvertising;

    @Column(name = "final_scoring")
    private float finalScoring;

    @Column(name = "opportunity_to_offer_a_loan_product")
    private boolean opportunityToOfferLoan;

    @OneToOne
    @JoinColumn(name = "id_work_experience")
    private WorkExperienceEntity workExperience;

    @OneToOne
    @JoinColumn(name = "id_type_loan_collateral")
    private TypeLoanCollateralEntity typeLoanCollateral;

    @Column(name = "availability_of_open_loans")
    private boolean openLoans;

    @OneToOne
    @JoinColumn(name = "current_debt_load")
    private CurrentDebtLoadEntity currentDebtLoad;

    @Column(name = "amount_of_credit")
    private float creditAmount;

    @Column(name = "desired_loan_term")
    private float loanTerm;

    @Column(name = "minimum_desired_bid")
    private float minBid;

    @Column(name = "maximum_desired_bid")
    private float maxBid;

    @Column(name = "having_down_payment")
    private boolean downPayment;

    @Column(name = "military")
    private boolean isMilitary;

    @Column(name = "state_employee")
    private boolean isStateEmployee;

    @Column(name = "is_psb_client")
    private boolean isPsbClient;

    @Column(name = "far_east_inhabitant")
    private boolean isFarEastInhabitant;

    @Column(name = "resident_of_the_new_subjects")
    private boolean isNewSubjectsResident;

    @Column(name = "it_specialist")
    private boolean isItSpecialist;

    @Column(name = "lending_type")
    @Enumerated(EnumType.STRING)
    private LendingType lendingType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public float scoreCalculate(LocalDate birthday) {
        float total = 0;

        int age = Period.between(birthday, LocalDate.now()).getYears();
        if (21 <= age && age <= 22) total += 9;
        else if (23 <= age && age <= 45) total += 15;
        else if (46 <= age && age <= 64) total += 34;
        else if (65 <= age && age <= 70) total += 10;

        switch (getWorkExperience().getName()) {
            case LESS_THAN_YEAR_AND_HALF -> total += 14;
            case ONE_AND_HALF_TO_TEN -> total += 27;
            case ELEVEN_TO_TWENTY, MORE_THAN_TWENTY -> total += 34;
        }

        switch (getTypeLoanCollateral().getName()) {
            case APARTMENT, CAR -> total += 47;
            case HOUSE -> total += 42;
            case LAND -> total += 32;
            case WITHOUT_COLLATERAL -> total += 15;
        }

        if (getCurrentDebtLoad().getAmountLoanPayments() >= 0 && getCurrentDebtLoad().getMonthlyIncome() > 0) {
            float currentDebtLoad = getCurrentDebtLoad().getAmountLoanPayments() / getCurrentDebtLoad().getMonthlyIncome();

            if (currentDebtLoad > 0 && currentDebtLoad < 0.1)   total += 58;
            else if (currentDebtLoad > 0.11 && currentDebtLoad < 0.5)   total += 43;
            else if (currentDebtLoad > 0.51 && currentDebtLoad < 0.7)   total += 21;
            else if (currentDebtLoad > 0.71)   total += 10;
        }

        switch (getCurrentDebtLoad().getCountActiveLoans()) {
            case NO_CREDITS -> total += 40;
            case FROM_ONE_TO_TWO -> total += 34;
            case FROM_THREE_TO_FIVE -> total += 15;
            case MORE_THAN_FIVE -> total += 3;
        }

        return total;
    }
}
