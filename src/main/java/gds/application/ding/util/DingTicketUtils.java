/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.ding.util;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.UUID;

/**
 * 钉钉公共模块：ticket工具类
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public class DingTicketUtils {

	/**
	 *  生成钉钉签名(signature)
	 * @param ticket 
	 * @param nonceStr
	 * @param timeStamp
	 * @param url  
	 * @return  返回 签名
	 * @throws Exception 抛出异常
	 */
	public static String getDingSignature(String ticket, String nonceStr, String timeStamp,String url) throws Exception {
		// 生成的签名所需URL
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timeStamp + "&url=" + url;
		
		MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
		sha1.reset();
		sha1.update(plain.getBytes("UTF-8"));

		// 返回钉钉签名(signature)
		return byteToHex(sha1.digest());
	}
		  
   /**
    *   对byte数组进行格式化  
    * @param hash  byte[]格式化byte数组
    * @return  String 返回格式化后的字符串
    */
	public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    /**
     * 生成签名的随机串
     * @return String 返回生成签名的随机串
     */
	public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

	/**
	 *  获取签名的时间戳
	 * @return String  返回签名的时间戳
	 */
	public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }


}
