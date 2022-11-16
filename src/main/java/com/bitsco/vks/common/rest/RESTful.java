package com.bitsco.vks.common.rest;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;

public class RESTful {

    static {
        disableSslVerification();
    }

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> initHeader() {
        return new HashMap<String, String>() {{
            put("Content-Type", Constant.CONTENT_TYPE_APPLICATION_JSON_UTF8);
        }};
    }

    public static Map<String, String> call(String keyRequest, Logger LOGGER, String uri, String api, String method, Map header, Map params, String jsonBody, int timeOutConnect, int timeOutRead) throws Exception {
        HttpURLConnection conn = null;
        long startTime = System.currentTimeMillis();
        StringBuffer response = new StringBuffer();
        Map<String, String> result = new HashMap<>();
        InputStream in = null;
        OutputStream os = null;
        InputStreamReader inReader = null;
        BufferedReader br = null;
        try {
            uri += api;
            if (params != null && !params.isEmpty()) {
                List<String> listParams = new ArrayList<>();
                Iterator<Map.Entry<String, String>> paramsIterator = params.entrySet().iterator();
                while (paramsIterator.hasNext()) {
                    Map.Entry<String, String> pair = paramsIterator.next();
                    listParams.add(pair.getKey() + "=" + URLEncoder.encode(pair.getValue(), StandardCharsets.UTF_8.name()));
                }
                StringBuilder builder = new StringBuilder();
                builder.append(uri);
                if (listParams != null && !listParams.isEmpty()) {
                    int i = 0;
                    for (String s : listParams) {
                        if (i == 0) {
                            builder.append("?");
                        } else {
                            builder.append("&");
                        }
                        builder.append(s);
                        i++;
                    }
                    uri = builder.toString();
                }
            }
            LOGGER.info("[B][" + keyRequest + "] RESTful.call url: " + uri + " timeout connect: " + timeOutConnect
                    + " timeout read " + timeOutRead + ((jsonBody == null) ? "" : " \nbody: " + jsonBody));
            URL url = new URL(uri);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeOutConnect);
            conn.setReadTimeout(timeOutRead);
            conn.setRequestMethod(method);

            if (header != null && !header.isEmpty()) {
                Iterator<Map.Entry<String, String>> headerIterator = header.entrySet().iterator();
                while (headerIterator.hasNext()) {
                    Map.Entry<String, String> pair = headerIterator.next();
                    conn.setRequestProperty(pair.getKey(), pair.getValue());
                }
            }

            if (method.equals("POST")) {
                if (!StringCommon.isNullOrBlank(jsonBody)) {
                    conn.setDoOutput(true);
                    os = conn.getOutputStream();
                    os.write(jsonBody.getBytes());
                    os.flush();
                }
            }
            result.put(Constant.REST_FULL.HTTP_STATUS, String.valueOf(conn.getResponseCode()));
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = conn.getInputStream();
            } else {
                /* error from server */
                in = conn.getErrorStream();
            }
            if (in != null) {
                inReader = new InputStreamReader(in);
                br = new BufferedReader(inReader);
                String output;
                while ((output = br.readLine()) != null) {
                    try {
                        response.append(output);
                    } catch (Exception e) {
                        LOGGER.error("[Exception][" + keyRequest + "][Duration = " + (System.currentTimeMillis() - startTime) + "] lỗi khi thực hiện quá trình đọc dữ liệu response ", e);
                        throw e;
                    }
                }
                result.put(Constant.REST_FULL.RESPONSE_BODY, response.toString());
            }
        } catch (java.net.SocketTimeoutException e) {
            LOGGER.error("[SocketTimeoutException][" + keyRequest + "][Duration = " + (System.currentTimeMillis() - startTime) + "] quá thời gian chờ phản hồi của request ", e);
            throw new CommonException(Response.SUPPLIER_TIME_OUT);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + keyRequest + "][Duration = " + (System.currentTimeMillis() - startTime) + "] lỗi khi thực hiện call API ", e);
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inReader != null) {
                try {
                    inReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            LOGGER.info("[E][" + keyRequest + "][Duration = " + (System.currentTimeMillis() - startTime) + "] RESTful.call response " + JsonCommon.mapToJsonFull(result));
        }
        return result;
    }
}
