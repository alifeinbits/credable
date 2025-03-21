package com.credable.lms.service;

import com.credable.lms.request.KycRequest;
import com.credable.lms.response.KycResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;

@Service
public class KycService {
    private final WebServiceTemplate webServiceTemplate;

    @Value("${cbs.kyc.url}")
    private String kycApiUrl;

    public KycService(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public KycResponse getKycDetails(String customerNumber) {
        KycRequest request = new KycRequest(customerNumber);

        JAXBElement<KycResponse> response = (JAXBElement<KycResponse>)
                webServiceTemplate.marshalSendAndReceive(kycApiUrl, request);

        return response.getValue();
    }
}


