package com.multi.module.notifications.model.context;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayoffAsset {
    private String financeEntity_name;
    private String payoff_status;
    private String creator;
    private String approver;
    private String dealer_name;
    private String asset_Name;

    private String type_of_business;
    private String serial_number;
    private String manufacturer;
    private String internal_ref_number;
    private String disb_date;
    private String outstanding_amount;
}
