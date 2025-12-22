package com.multi.module.domain.TransformationRequest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransformationRequest {
    long tranformationrequestId;
    String dealerName;
}
