package core.api.interfaceservice;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Map;

/**
 * http 请求
 */
public class DoHttp {

    private Logger logger = Logger.getLogger(DoHttp.class);
    private Boolean isHttps = false;
    private URI url;
    private String requestBody;
    private Map<String, String> mapGet;
    private Map<String, String> mapPost;

    public Map<String, String> getMapGet() {
        return mapGet;
    }

    public void setMapGet(Map<String, String> mapGet) {
        this.mapGet = mapGet;
    }

    public Map<String, String> getMapPost() {
        return mapPost;
    }

    public void setMapPost(Map<String, String> mapPost) {
        this.mapPost = mapPost;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Boolean getHttps() {
        return isHttps;
    }

    public void setHttps(Boolean https) {
        isHttps = https;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    /**
     * Get
     *
     * @return
     */
    public ResponseDetail doGet() {
        ResponseDetail responseDetail = new ResponseDetail();
        CloseableHttpClient httpClient = getHttpClient(isHttps);
        HttpGet httpGet = new HttpGet(url);
        if (mapGet != null && !mapGet.isEmpty()) {
            for (Map.Entry<String, String> entry : mapGet.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse response = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);
            String headerMsg = "GET: " + url + "\n";
            for (Header header : httpGet.getAllHeaders()) {
                headerMsg += header.toString() + "\n";
            }
            logger.debug("++++++++++RequestHeader++++++++++:" + headerMsg);
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            responseDetail.setStatus(response.getStatusLine().getStatusCode());
            if (responseEntity != null) {
                responseDetail.setResponse_msg(EntityUtils.toString(responseEntity));
            }
            logger.debug("++++++++++ResponseHeader++++++++++:" + responseDetail.getStatus());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseDetail;
    }

    /**
     * Post
     *
     * @return
     */
    public ResponseDetail doPost() {
        ResponseDetail responseDetail = new ResponseDetail();
        CloseableHttpClient httpClient = getHttpClient(isHttps);
        HttpPost httpPost = new HttpPost(url);
        if (mapPost != null && !mapPost.isEmpty()) {
            for (Map.Entry<String, String> entry : mapPost.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        if (requestBody != null) {
            StringEntity entity = new StringEntity(requestBody, "UTF-8");
            httpPost.setEntity(entity);
        }
        CloseableHttpResponse response = null;
        try {
            String headerMsg = "POST: " + url + "\n";
            for (Header header : httpPost.getAllHeaders()) {
                headerMsg += header.toString() + "\n";
            }
//            logger.debug("RequestHeader:\n"+headerMsg);
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            responseDetail.setStatus(response.getStatusLine().getStatusCode());
            Header[] headers = response.getAllHeaders();
            responseDetail.setHeaders(headers);
            if (responseEntity != null) {
                responseDetail.setResponse_msg(EntityUtils.toString(responseEntity));
            }
//            logger.debug("ResponseCode:"+ url + "\n" + "status:" + responseDetail.getStatus()+ "\n" + responseDetail.getResponse_msg());
            logger.debug("----------------------------------POST START-------------------------------------------" + "\n"
                    + "++++RequestHeader:++++\n" + headerMsg
                    + "++++ResponseCode:++++\n" + url + "\n" + "status:" + responseDetail.getStatus() + "\n"
                    + responseDetail.getResponse_msg() + "\n" +
                    "----------------------------------POST END-------------------------------------------"
            );
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseDetail;
    }

    private CloseableHttpClient getHttpClient(boolean isHttps) {
        CloseableHttpClient httpClient;
        if (isHttps) {
            SSLConnectionSocketFactory sslSocketFactory;
            try {
                /// 如果不作证书校验的话
                sslSocketFactory = getSocketFactory(false, null, null);

                /// 如果需要证书检验的话
                // 证书
                //InputStream ca = this.getClass().getClassLoader().getResourceAsStream("client/ds.crt");
                // 证书的别名，即:key。 注:cAalias只需要保证唯一即可，不过推荐使用生成keystore时使用的别名。
                // String cAalias = System.currentTimeMillis() + "" + new SecureRandom().nextInt(1000);
                //sslSocketFactory = getSocketFactory(true, ca, cAalias);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            httpClient = HttpClientBuilder.create().setSSLSocketFactory(sslSocketFactory).build();
            return httpClient;
        }
        httpClient = HttpClientBuilder.create().build();
        return httpClient;
    }

    /**
     * HTTPS辅助方法, 为HTTPS请求 创建SSLSocketFactory实例、TrustManager实例
     *
     * @param needVerifyCa  是否需要检验CA证书(即:是否需要检验服务器的身份)
     * @param caInputStream CA证书。(若不需要检验证书，那么此处传null即可)
     * @param cAalias       别名。(若不需要检验证书，那么此处传null即可)
     *                      注意:别名应该是唯一的， 别名不要和其他的别名一样，否者会覆盖之前的相同别名的证书信息。别名即key-value中的key。
     * @return SSLConnectionSocketFactory实例
     * @throws NoSuchAlgorithmException 异常信息
     * @throws CertificateException     异常信息
     * @throws KeyStoreException        异常信息
     * @throws IOException              异常信息
     * @throws KeyManagementException   异常信息
     * @date 2019/6/11 19:52
     */
    private static SSLConnectionSocketFactory getSocketFactory(boolean needVerifyCa, InputStream caInputStream, String cAalias)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException,
            IOException, KeyManagementException {
        X509TrustManager x509TrustManager;
        // https请求，需要校验证书
        if (needVerifyCa) {
            KeyStore keyStore = getKeyStore(caInputStream, cAalias);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            x509TrustManager = (X509TrustManager) trustManagers[0];
            // 这里传TLS或SSL其实都可以的
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
            return new SSLConnectionSocketFactory(sslContext);
        }
        // https请求，不作证书校验
        x509TrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
                // 不验证
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
        return new SSLConnectionSocketFactory(sslContext);
    }

    /**
     * 获取(密钥及证书)仓库
     * 注:该仓库用于存放 密钥以及证书
     *
     * @param caInputStream CA证书(此证书应由要访问的服务端提供)
     * @param cAalias       别名
     *                      注意:别名应该是唯一的， 别名不要和其他的别名一样，否者会覆盖之前的相同别名的证书信息。别名即key-value中的key。
     * @return 密钥、证书 仓库
     * @throws KeyStoreException        异常信息
     * @throws CertificateException     异常信息
     * @throws IOException              异常信息
     * @throws NoSuchAlgorithmException 异常信息
     * @date 2019/6/11 18:48
     */
    private static KeyStore getKeyStore(InputStream caInputStream, String cAalias)
            throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        // 证书工厂
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        // 秘钥仓库
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        keyStore.setCertificateEntry(cAalias, certificateFactory.generateCertificate(caInputStream));
        return keyStore;
    }

}
