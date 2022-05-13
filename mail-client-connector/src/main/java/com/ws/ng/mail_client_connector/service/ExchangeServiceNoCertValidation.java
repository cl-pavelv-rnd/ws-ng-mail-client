package com.ws.ng.mail_client_connector.service;

import lombok.SneakyThrows;
import microsoft.exchange.webservices.data.core.ExchangeService;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class ExchangeServiceNoCertValidation extends ExchangeService {

    public ExchangeServiceNoCertValidation() {
        super();
    }

    @SneakyThrows
    @Override
    protected Registry<ConnectionSocketFactory> createConnectionSocketFactoryRegistry() {
        TrustManager[] trustManagers = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustManagers, new SecureRandom());
        return RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("https", new SSLConnectionSocketFactory(sc, (s, sslSession) -> true))
                .build();
    }

}
