package com.multi.module.domain.notifications.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class Recipients {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String emailId;
}
