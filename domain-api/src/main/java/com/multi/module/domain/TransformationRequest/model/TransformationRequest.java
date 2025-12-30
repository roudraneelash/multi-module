package com.multi.module.domain.TransformationRequest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransformationRequest {
    private Long transformationRequestId;
    private String financeEntityName;
    private String status;  // PAYOFF_INITIATED, PAYOFF_COMPLETED, etc.
    private String initiator;  // maps to creator
    private String approver;
    private String dealerName;
    private String assetName;
    private Double outstandingAmount;
}