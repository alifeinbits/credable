package com.credable.lms.integration.cbs.transaction;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 4.0.2
 * 2025-03-21T18:55:33.510+03:00
 * Generated source version: 4.0.2
 *
 */
@WebService(targetNamespace = "http://credable.io/cbs/transaction", name = "TransactionDataPort")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface TransactionDataPort {

    @WebMethod(operationName = "Transactions")
    @WebResult(name = "TransactionsResponse", targetNamespace = "http://credable.io/cbs/transaction", partName = "TransactionsResponse")
    public TransactionsResponse transactions(

        @WebParam(partName = "TransactionsRequest", name = "TransactionsRequest", targetNamespace = "http://credable.io/cbs/transaction")
        TransactionsRequest transactionsRequest
    );
}
