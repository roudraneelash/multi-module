package com.multi.module.notifications.mapper;

import com.multi.module.domain.TransformationRequest.model.TransformationRequest;
import com.multi.module.domain.financeRequest.model.FinanceRequest;
import com.multi.module.notifications.model.context.InsuranceExpiry;
import com.multi.module.notifications.model.context.LimitExpiry;
import com.multi.module.notifications.model.context.PayoffAsset;

public class ContextMapper {

    public static LimitExpiry mapToLimitExpiryContext(FinanceRequest fr) {
        return LimitExpiry.builder()
                .financeEntity_name(fr.getFinanceEntityName())
                .dealer_name(fr.getDealerName())
                .expiry_Date(fr.getExpiryDate().toString())
                .build();
    }

    public static InsuranceExpiry mapToInsuranceExpiryContext(FinanceRequest fr) {
        return InsuranceExpiry.builder()
                .financeEntity_name(fr.getFinanceEntityName())
                .dealer_name(fr.getDealerName())
                .expiry_Date(fr.getInsuranceExpiryDate().toString())
                .build();
    }

    public static PayoffAsset mapToPayoffAssetContext(FinanceRequest fr) {
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