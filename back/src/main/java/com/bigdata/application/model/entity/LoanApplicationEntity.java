package com.bigdata.application.model.entity;

import com.bigdata.user.model.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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

    @Column(name = "consent_to_the_processinf_of_personal_data")
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
