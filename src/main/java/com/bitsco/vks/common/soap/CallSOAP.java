package com.bitsco.vks.common.soap;


import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.NumberCommon;
import com.bitsco.vks.common.util.StringCommon;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class CallSOAP {
    public CallSOAP() {
        super();
    }

    public static CloseableHttpClient initSecureClient(Logger LOGGER, int connectionTimeout, int socketTimeout) {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs,
                                               String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs,
                                               String authType) {
                }
            }}, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("initSecureClient NoSuchAlgorithmException ", e);
        } catch (KeyManagementException e) {
            LOGGER.error("initSecureClient KeyManagementException ", e);
        }

        HostnameVerifier verifier = NoopHostnameVerifier.INSTANCE;
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslContext, verifier);
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setConnectionRequestTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout).build();
        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(factory).setDefaultRequestConfig(config).build();
        return client;
    }

    public static String callHttpSOAP(String keyRequest, Logger LOGGER, String url, int connectionTimeout, int socketTimeout, String authorization, String action, String request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + keyRequest + "] call SOAP url: " + url
                + " connect timeout: " + connectionTimeout
                + " read timeout: " + socketTimeout
                + (!StringCommon.isNullOrBlank(authorization) ? (" authorization: " + authorization) : "")
                + (!StringCommon.isNullOrBlank(action) ? (" SOAPAction: " + action) : "")
                + " request: " + request);
        CloseableHttpClient httpclient = initSecureClient(LOGGER, connectionTimeout, socketTimeout);
        String response = null;
        CloseableHttpResponse closeableHttpResponse = null;

        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(request, Charset.forName("UTF-8")));
            if (!StringCommon.isNullOrBlank(authorization))
                post.setHeader("Authorization", authorization);
            if (!StringCommon.isNullOrBlank(action))
                post.setHeader("SOAPAction", action);

            post.setHeader("Content-Type", "text/xml; charset=UTF-8");
            closeableHttpResponse = httpclient.execute(post);
            response = EntityUtils.toString(closeableHttpResponse.getEntity());
            if (closeableHttpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
                throw new CommonException(Response.SUPPLIER_ERROR, "Có lỗi khi call webservice nhà cung cấp, StatusCode = " + closeableHttpResponse.getStatusLine().getStatusCode());

        } catch (java.net.SocketTimeoutException | java.net.ConnectException e) {
            LOGGER.error("[SocketTimeoutException | ConnectException][" + keyRequest + "][Duration = " + (System.currentTimeMillis() - startTime) + "] call SOAP request = " + request, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("[Exception][" + keyRequest + "][Duration = " + (System.currentTimeMillis() - startTime) + "] call SOAP request = " + request, e);
            throw e;
        } catch (Throwable e) {
            LOGGER.error("[Throwable][" + keyRequest + "][Duration = " + (System.currentTimeMillis() - startTime) + "] call SOAP request = " + request, e);
            throw e;
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
            } finally {
                LOGGER.info("[E][" + keyRequest + "][Duration = " + (System.currentTimeMillis() - startTime) + "] call SOAP response = " + response);
            }
        }
        return response;
    }

    public static void main(String[] args) {
        String request = CallSOAP.createRequestBawase("Cus_Info", new HashMap<String, String>() {
            {
                put("MaKH", "H10023-0000858");
                put("User", "ecpay01");
                put("Pass", "ecpay01_bawaco");
            }
        });

        try {

            String resp = CallSOAP.callHttpSOAP(
                    NumberCommon.getAuditNumber() + "",
                    LogManager.getLogger(CallSOAP.class),
                    "https://capnuocbaclieu.com.vn/ws_test/Customers_Info.asmx?wsdl",
                    30000,
                    30000,
                    null,
                    null,
                    request);
            System.out.println(resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Document stringToDom(String xmlSource) throws SAXException,
            ParserConfigurationException,
            IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }

    public static String createRequestBiwase(String method, Map<String, String> input) {
        String header = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\"><soapenv:Header/><soapenv:Body>";
        String body = "<tem:" + method + ">";
        if (input != null && !input.isEmpty())
            for (Map.Entry<String, String> entry : input.entrySet()) {
                body += "<tem:" + entry.getKey() + ">" + entry.getValue() + "</tem:" + entry.getKey() + ">";
            }
        body += "</tem:" + method + ">";
        String footer = "</soapenv:Body></soapenv:Envelope>";
        return header + body + footer;
    }

    public static String createRequestBawase(String method, Map<String, String> input) {
        String header = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cap=\"http://capnuocbaclieu.com.vn/\"><soapenv:Header/><soapenv:Body>";
        String body = "<cap:" + method + ">";
        if (input != null && !input.isEmpty())
            for (Map.Entry<String, String> entry : input.entrySet()) {
                body += "<cap:" + entry.getKey() + ">" + entry.getValue() + "</cap:" + entry.getKey() + ">";
            }
        body += "</cap:" + method + ">";
        String footer = "</soapenv:Body></soapenv:Envelope>";
        return header + body + footer;
    }
}
