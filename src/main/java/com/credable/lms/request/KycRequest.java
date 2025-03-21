package com.credable.lms.request;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@XmlRootElement(name = "KycRequest")
public class KycRequest {
    private String customerNumber;

    public KycRequest() {}

    public KycRequest(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    @XmlElement(name = "customerNumber")
    public String getCustomerNumber() {
        return customerNumber;
    }

}

