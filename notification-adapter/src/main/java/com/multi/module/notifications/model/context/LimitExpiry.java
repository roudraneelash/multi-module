package com.multi.module.notifications.model.context;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LimitExpiry {
    private String financeEntity_name;
    private String dealer_name;
    private String expiry_Date;
}
