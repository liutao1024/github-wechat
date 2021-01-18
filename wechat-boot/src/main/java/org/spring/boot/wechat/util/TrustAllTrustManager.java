package org.spring.boot.wechat.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @Author LiuTao @Date 2021年1月18日 上午11:06:52
 * @ClassName: TrustAllTrustManager 
 * @Description: 跳过证书认证
 */
public class TrustAllTrustManager implements TrustManager, X509TrustManager {

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    @Override
    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        return;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        return;
    }
}