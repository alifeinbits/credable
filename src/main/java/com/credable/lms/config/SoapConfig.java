package com.credable.lms.config;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

@Configuration
public class SoapConfig {

    private static final String CONTEXT_PATH = "com.credable.lms.integration.cbs.kyc";


    @Value("${cbs.username}")
    private String username;

    @Value("${cbs.password}")
    private String password;

    @Value("${cbs.kyc.url}")
    private String kycApiUrl;

    @Value("${cbs.transactions.url}")
    private String transactionsApiUrl;

    @Bean
    public SaajSoapMessageFactory messageFactory() {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.setSoapVersion(SoapVersion.SOAP_11);
        return messageFactory;
    }
    @Bean
    public Jaxb2Marshaller kycMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(CONTEXT_PATH);
        return marshaller;
    }

    @Bean
    public Jaxb2Marshaller transactionMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.credable.lms.integration.cbs.transaction");
        return marshaller;
    }
    @Bean
    @Qualifier("kycWebServiceTemplate")
    public WebServiceTemplate kycWebServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(kycMarshaller());
        webServiceTemplate.setUnmarshaller(kycMarshaller());
        webServiceTemplate.setMessageFactory(messageFactory());
        webServiceTemplate.setMessageSender(httpComponentsMessageSender());
        webServiceTemplate.setDefaultUri(kycApiUrl);
        return webServiceTemplate;
    }

    @Bean
    @Qualifier("transactionWebServiceTemplate")
    public WebServiceTemplate transactionWebServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(transactionMarshaller());
        webServiceTemplate.setUnmarshaller(transactionMarshaller());
        webServiceTemplate.setMessageFactory(messageFactory());
        webServiceTemplate.setMessageSender(httpComponentsMessageSender());
        webServiceTemplate.setDefaultUri(transactionsApiUrl);
        return webServiceTemplate;
    }

    @Bean
    public HttpComponentsMessageSender httpComponentsMessageSender() {
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
        messageSender.setHttpClient(httpClient());
        return messageSender;
    }

    private CloseableHttpClient httpClient() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(null, -1),
                new UsernamePasswordCredentials(username, password));
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        return HttpClients.custom()
                .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                .disableContentCompression()
                .setConnectionManager(connectionManager)
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
    }
}
