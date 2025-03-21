package com.credable.lms.response;

import lombok.Data;

@Data
public class SubscriptionResponse {
    private String customerNumber;
    private String status;
    private String message;
}
