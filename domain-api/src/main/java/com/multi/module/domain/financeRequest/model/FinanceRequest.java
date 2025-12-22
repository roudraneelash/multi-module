package com.multi.module.domain.financeRequest.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class FinanceRequest {

    /* --- Core Identifiers --- */
    private Long financeRequestId;
    private String financeEntityName;
    private Integer financeEntityId;

    /* --- Dealer Info --- */
    private String dealerName;

    /* --- Dates --- */
    private LocalDate requestDate;
    private LocalDate expiryDate;
    private LocalDate insuranceExpiryDate;
    private LocalDate disbursementDate;

    /* --- Asset Info (used by payoff / transformation) --- */
    private String assetName;
    private String serialNumber;
    private String manufacturer;

    /* --- Business Flow Info --- */
    private String typeOfBusiness;     // e.g. LEASE / LOAN / HIRE_PURCHASE
    private String payoffStatus;       // e.g. INITIATED / APPROVED
    private String creator;
    private String approver;

    /* --- Financial Info --- */
    private BigDecimal outstandingAmount;
    private String internalRefNumber;
}