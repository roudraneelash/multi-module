package com.multi.module.notifications.model.context;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayoffNotification {
    private String financeEntityName;
    private String payoffStatus;
    private String creator;
    private String approver;
    private String dealerName;
    private String assetName;
    private String outstandingAmount;
}
