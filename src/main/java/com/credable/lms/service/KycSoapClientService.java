package com.credable.lms.service;

import com.credable.lms.integration.cbs.kyc.CustomerRequest;
import com.credable.lms.integration.cbs.kyc.CustomerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Slf4j
@Service
public class KycSoapClientService {
    private final WebServiceTemplate webServiceTemplate;


    @Value("${cbs.kyc.url}")
    private String kycApiUrl;

    public KycSoapClientService(@Qualifier("kycWebServiceTemplate") WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public CustomerResponse getKycDetails(String customerNumber) {
        CustomerRequest requestPayload = new CustomerRequest();
        requestPayload.setCustomerNumber(customerNumber);

        return (CustomerResponse)  webServiceTemplate.marshalSendAndReceive(
                kycApiUrl,
                requestPayload
        );

    }
}


