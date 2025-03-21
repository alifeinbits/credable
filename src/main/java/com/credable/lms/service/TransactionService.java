package com.credable.lms.service;

import com.credable.lms.integration.cbs.transaction.TransactionsRequest;
import com.credable.lms.integration.cbs.transaction.TransactionsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
@Slf4j
public class TransactionService {
    private final WebServiceTemplate webServiceTemplate;

    private static final String CONTEXT_PATH = "com.credable.lms.integration.cbs.transaction";

    @Value("${cbs.transactions.url}")
    private String transactionsApiUrl;

    public TransactionService(@Qualifier("transactionWebServiceTemplate") WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public TransactionsResponse getTransactions(String customerNumber) {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(CONTEXT_PATH);
        TransactionsRequest request = new TransactionsRequest();
        request.setCustomerNumber(customerNumber);
        return (TransactionsResponse) webServiceTemplate.marshalSendAndReceive(transactionsApiUrl, request);
    }
}

