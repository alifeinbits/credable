package com.credable.lms.response;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@XmlRootElement(name = "KycResponse")
@Getter
public class KycResponse {
    private String customerNumber;
    private String name;
    private String phoneNumber;

    public KycResponse() {}

    public KycResponse(String customerNumber, String name, String phoneNumber) {
        this.customerNumber = customerNumber;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @XmlElement(name = "customerNumber")
    public String getCustomerNumber() {
        return customerNumber;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

}

