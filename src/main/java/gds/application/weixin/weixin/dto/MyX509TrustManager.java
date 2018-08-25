package gds.application.weixin.weixin.dto;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;


/** 
 *  微信公共模块：对外访问证书认证控制器
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public class MyX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] arg0, String authType) throws CertificateException {
		
	}

	public void checkServerTrusted(X509Certificate[] arg0, String authType) throws CertificateException {
		
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}



}
