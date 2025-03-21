package com.credable.lms.integration.cbs.transaction;

import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import jakarta.xml.ws.WebServiceFeature;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 4.0.2
 * 2025-03-21T18:55:33.532+03:00
 * Generated source version: 4.0.2
 *
 */
@WebServiceClient(name = "TransactionDataPortService",
                  wsdlLocation = "https://trxapitest.credable.io/service/transactionWsdl.wsdl",
                  targetNamespace = "http://credable.io/cbs/transaction")
public class TransactionDataPortService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://credable.io/cbs/transaction", "TransactionDataPortService");
    public final static QName TransactionDataPortSoap11 = new QName("http://credable.io/cbs/transaction", "TransactionDataPortSoap11");
    static {
        URL url = null;
        try {
            url = new URL("https://trxapitest.credable.io/service/transactionWsdl.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(TransactionDataPortService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "https://trxapitest.credable.io/service/transactionWsdl.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public TransactionDataPortService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public TransactionDataPortService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TransactionDataPortService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public TransactionDataPortService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public TransactionDataPortService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public TransactionDataPortService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns TransactionDataPort
     */
    @WebEndpoint(name = "TransactionDataPortSoap11")
    public TransactionDataPort getTransactionDataPortSoap11() {
        return super.getPort(TransactionDataPortSoap11, TransactionDataPort.class);
    }

    /**
     *
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TransactionDataPort
     */
    @WebEndpoint(name = "TransactionDataPortSoap11")
    public TransactionDataPort getTransactionDataPortSoap11(WebServiceFeature... features) {
        return super.getPort(TransactionDataPortSoap11, TransactionDataPort.class, features);
    }

}
