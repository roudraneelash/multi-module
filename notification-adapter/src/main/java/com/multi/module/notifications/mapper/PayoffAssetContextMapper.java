package com.multi.module.notifications.mapper;

import com.multi.module.domain.financeRequest.model.FinanceRequest;
import com.multi.module.domain.notifications.enums.Notification;
import com.multi.module.notifications.model.context.PayoffAsset;
import org.springframework.stereotype.Component;

@Component
public class PayoffAssetContextMapper
        implements NotificationContextMapper<FinanceRequest, PayoffAsset> {

    @Override
    public Notification getNotificationType() {
        return Notification.PAYOFF_ASSET;
    }

    @Override
    public Class<FinanceRequest> sourceType() {
        return FinanceRequest.class;
    }

    @Override
    public PayoffAsset map(FinanceRequest fr) {
        return PayoffAsset.builder()
                .financeEntity_name(fr.getFinanceEntityName())
                .dealer_name(fr.getDealerName())
                .asset_Name(fr.getAssetName())
                .creator(fr.getCreator())
                .payoff_status(fr.getPayoffStatus())
                .approver(fr.getApprover())
                .type_of_business(fr.getTypeOfBusiness())
                .serial_number(fr.getSerialNumber())
                .manufacturer(fr.getManufacturer())
                .internal_ref_number(fr.getInternalRefNumber())
                .disb_date(fr.getDisbursementDate().toString())
                .outstanding_amount(fr.getOutstandingAmount().toString())
                .build();
    }
}