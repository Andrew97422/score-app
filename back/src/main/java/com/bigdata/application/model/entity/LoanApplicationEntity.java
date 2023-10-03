package com.bigdata.application.model.entity;

import com.bigdata.application.model.enums.ApplicationStatus;
import com.bigdata.products.common.LendingType;
import com.bigdata.user.model.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "work_experience_id")
    private WorkExperienceEntity workExperience;

    @Column(name = "availability_of_open_loans")
    private boolean openLoans;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_debt_load_id")
    private CurrentDebtLoadEntity currentDebtLoad;

    @Column(name = "amount_of_credit")
    private float creditAmount;

    @Column(name = "desired_loan_term")
    private float loanTerm;

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
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
