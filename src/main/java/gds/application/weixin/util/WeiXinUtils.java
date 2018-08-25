package gds.application.weixin.util;

import gds.application.weixin.weixin.dto.WeixinMessageDTO;
import gds.framework.exception.AppException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;


/** 
 *  微信公共模块：微信工具类
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public class WeiXinUtils {
	 
	   /**
		 *   根据url链接和要获取的字段，查询要获取的字段的value值
		 * @param url   要访问的url链接
		 * @param messageName 要获取的字段信息
		 * @return    返回要获取的字段信息内容
		 * @throws AppException 抛出异常
		 * @author liuyunpeng
		 */
		public static String   getWeiXinInfo(String url,String messageName) throws Exception{
			
			// 链接
			URL getUrl = null;
				// 创建一个管理证书的任务管理器
				TrustManager[] tm = { new gds.application.weixin.weixin.dto.MyX509TrustManager() };
				SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new java.security.SecureRandom());
				// 从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();

				getUrl = new URL(url);
				HttpsURLConnection http = (HttpsURLConnection) getUrl.openConnection();
				http.setSSLSocketFactory(ssf);
				http.setRequestMethod("GET");
				http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				http.setDoOutput(true);
				http.setDoInput(true);
				http.connect();
				
				// 获取访问URL获取的信息
	            InputStream is = http.getInputStream();
	            
	            StringBuilder sb = new StringBuilder();
	            
	            // 定义BufferedReader输入流来读取URL的响应
	            BufferedReader in = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null){
	            	sb.append(line);
	            }
	            
	            // 关闭和第三方建立的连接通道
	            is.close();
	            
	            // 从返回信息中，获取要获取的字段信息
	            JSONObject json = JSONObject.fromObject(sb.toString());
	            if( json.containsKey(messageName) ){
	            	 messageName = json.get(messageName).toString();
	            }else{
	            	messageName =  json.toString();
	            }

			return messageName;
		}
		
		
		
		/**
		 *  通过Post访问URL
		 * @param url
		 * @param jsonObject
		 * @return
		 * @throws NoSuchProviderException 
		 * @throws  
		 * @throws Exception
		 */
		public static  String   getWeiXinInfoByPost(String url,JSONObject jsonObject,String messageName) throws Exception{
			HttpsURLConnection http = null;
			if(!url.isEmpty()){
				URL getUrl = null;
				
				// 创建一个管理证书的任务管理器
				TrustManager[] tm = { new gds.application.weixin.weixin.dto.MyX509TrustManager() };
				SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new java.security.SecureRandom());
				// 从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				
				//3. 打开URL链接
				getUrl = new URL(url);
				http = (HttpsURLConnection) getUrl.openConnection();
				((HttpsURLConnection) http).setSSLSocketFactory(ssf);
				http.setRequestMethod("POST");
				http.setRequestProperty("Content-Type", "application/json;charset=utf-8"); 
				http.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
				http.setRequestProperty("Charset", "UTF-8");
				http.setDoOutput(true);
				http.setDoInput(true);
				http.connect();
				
				//4.1建立输入流，向指向的URL传入参数
				OutputStreamWriter   osw = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
				osw.write(jsonObject.toString());
				osw.flush();
				osw.close();
			    
			   // 4.2这句才是真正发送请求  
			   http.getInputStream();
			   
				// 获取访问URL获取的信息
	            InputStream is = http.getInputStream();
	            StringBuilder sb = new StringBuilder();
	            
	            // 定义BufferedReader输入流来读取URL的响应
	            BufferedReader in = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null){
	            	sb.append(line);
	            }
	            
	            // 关闭和第三方建立的连接通道
	            is.close();
	            
	            // 从返回信息中，获取要获取的字段信息
	            JSONObject json = JSONObject.fromObject(sb.toString());
	            if (messageName.isEmpty()) {
	                messageName = json.toString();
	            } else {
	                messageName = json.get(messageName).toString();
	            }
			}
			
			// 返回要获取的内容
			return messageName;
		}
		
		/**
		 *    发送企业微信消息
		 * @param accessToken  访问企业微信接口的证书
		 * @param url                     发送消息链接 
		 * @param toUser              消息接收人(人员NO)
		 * @param agentID            应用ID
		 * @param content            消息内容 content:文本消息、voice:语音消息、file：文件消息、textcard：卡片消息内容、news：图文消息内容
		 * @param msgtype          消息类型 
		 * @throws Exception      抛出异常
		 * @author liuyunpeng
		 */
		@SuppressWarnings("static-access")
		public static  void  sendWeixinMessageByPost(String url,String accessToken,int agentID,String toUser,String content,String msgtype) throws Exception{
			if(!url.isEmpty()){
			
					//1. 创建一个管理证书的任务管理器
					TrustManager[] tm = { new gds.application.weixin.weixin.dto.MyX509TrustManager() };
					SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
					sslContext.init(null, tm, new java.security.SecureRandom());
		
					//2.1 从上述SSLContext对象中得到SSLSocketFactory对象
					SSLSocketFactory ssf = sslContext.getSocketFactory();
					URL getUrl = null;
					
					//2.2对数据进行分装
					WeixinMessageDTO  userDTO = new WeixinMessageDTO();
					userDTO.setAgentid(agentID);
					userDTO.setMsgtype(msgtype);
					userDTO.setTouser(toUser);
					userDTO.setSafe(0);
					Map<String,String>  text	 =  new HashMap<String,String>();
					text.put("content", content);
					userDTO.setText(text);
					
					JSONObject jsonObject = new JSONObject().fromObject(userDTO);
					
					//3. 打开URL链接
					getUrl = new URL(url);
					HttpsURLConnection http = (HttpsURLConnection) getUrl.openConnection();
					http.setSSLSocketFactory(ssf);
		
					http.setRequestMethod("POST");
					http.setRequestProperty("Content-Type", "application/json;charset=utf-8"); 
					http.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
					http.setRequestProperty("Charset", "UTF-8");
					http.setDoOutput(true);
					http.setDoInput(true);
					http.connect();
					
					//4.1建立输入流，向指向的URL传入参数
					OutputStreamWriter   osw = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
					osw.write(jsonObject.toString());
					osw.flush();
					osw.close();
				    
				   // 4.2这句才是真正发送请求  
				   http.getInputStream();  
				   
				   // 关闭链接
				   http.disconnect();
			}
		}
		
		/**
		 *    发送企业微信消息
		 * @param accessToken  访问企业微信接口的证书
		 * @param url                     发送消息链接 
		 * @param toUser              消息接收人(人员NO)
		 * @param agentID            应用ID
		 * @param content            消息内容 content:文本消息、voice:语音消息、file：文件消息、textcard：卡片消息内容、news：图文消息内容
		 * @param msgtype          消息类型 
		 * @throws Exception      抛出异常
		 * @author liuyunpeng
		 */
		@SuppressWarnings("static-access")
		public static  HttpsURLConnection  sendWeixinMessageByPost1(String url,String accessToken,int agentID,String toUser,String content,String msgtype) throws Exception{
			HttpsURLConnection http = null;
			if(!url.isEmpty()){
			
					//1. 创建一个管理证书的任务管理器
					TrustManager[] tm = { new gds.application.weixin.weixin.dto.MyX509TrustManager() };
					SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
					sslContext.init(null, tm, new java.security.SecureRandom());
		
					//2.1 从上述SSLContext对象中得到SSLSocketFactory对象
					SSLSocketFactory ssf = sslContext.getSocketFactory();
					URL getUrl = null;
					
					//2.2对数据进行分装
					WeixinMessageDTO  userDTO = new WeixinMessageDTO();
					userDTO.setAgentid(agentID);
					userDTO.setMsgtype(msgtype);
					userDTO.setTouser(toUser);
					userDTO.setSafe(0);
					Map<String,String>  text	 =  new HashMap<String,String>();
					text.put("content", content);
					userDTO.setText(text);
					
					JSONObject jsonObject = new JSONObject().fromObject(userDTO);
					
					Logger log = Logger.getLogger("WeiXinUtils日志");
					log.info("发送消息内容："+jsonObject);
					log.info("发送消息AccessToken："+accessToken);
					log.info("发送消息url："+url);
				
					
					//3. 打开URL链接
					getUrl = new URL(url);
				    http = (HttpsURLConnection) getUrl.openConnection();
					http.setSSLSocketFactory(ssf);
		
					http.setRequestMethod("POST");
					http.setRequestProperty("Content-Type", "application/json;charset=utf-8"); 
					http.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
					http.setRequestProperty("Charset", "UTF-8");
					http.setDoOutput(true);
					http.setDoInput(true);
					http.connect();
					
					//4.1建立输入流，向指向的URL传入参数
					OutputStreamWriter   osw = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
					osw.write(jsonObject.toString());
					osw.flush();
					osw.close();
				    
				   // 4.2这句才是真正发送请求  
				   http.getInputStream();  
				   
				   // 关闭链接
				   http.disconnect();
			}
			
			return http;
		}
		
		
		/**
		 *  通过Post访问URL
		 * @param url
		 * @param jsonObject
		 * @return
		 * @throws Exception
		 */
		public static  HttpURLConnection  sendPhoneWarnByPost(String url,JSONObject jsonObject) {
			
			HttpURLConnection http = null;
			if(!url.isEmpty()){
			     try {
						URL getUrl = null;
						
						//3. 打开URL链接
						getUrl = new URL(url);
						http = (HttpURLConnection) getUrl.openConnection();

						http.setRequestMethod("POST");
						http.setRequestProperty("Content-Type", "application/json;charset=utf-8"); 
						http.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
						http.setRequestProperty("Charset", "UTF-8");
						http.setDoOutput(true);
						http.setDoInput(true);
						http.connect();
						
						//4.1建立输入流，向指向的URL传入参数
						OutputStreamWriter   osw = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
						osw.write(jsonObject.toString());
						osw.flush();
						osw.close();
					    
					   // 4.2这句才是真正发送请求  
					   http.getInputStream();
					   
				} catch (Exception e) {
					e.printStackTrace();
				}  
				   
			}
			
			return http;
		}
	

	/**
	 * 解决跨域JSON数据乱码问题
	 * 
	 * @param response
	 * @return
	 * @author liuyunpeng
	 */
	public static HttpServletResponse setResponseContent(HttpServletResponse response) {
		// 解决乱码问题
		response.setContentType("text/html; charset=utf-8");
		// 解决ajax跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		return response;
	}
	
	
	/**
	 *  获取安全签名
	 * @param stringStr 封装的字符串
	 * @return String 返回安全签名
	 * @throws Exception 抛出异常
	 */
	public  static String  createSignature(String stringStr) throws Exception{
     	// 获取安全签名
    	MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(stringStr.getBytes("UTF-8"));
        String signature = byteToHex(crypt.digest());
        
        // 返回安全签名
        return signature;
	}
	
    /**
     *  Byte转换
     * @param hash
     * @return
     */
    public  static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    /**
     *  生成时间戳
     * @return String 对象为timestamp字符串
     */
    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
    /**
     *  生成随机字符串
     * @return String对象为随机字符串
     */
    public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }
    
    /**
     *  微信公众号验证token
     * @param token
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String checkSignature(String token, String signature,String timestamp,String nonce){
        String[] arr=new String[]{token,timestamp,nonce};
        //排序
        Arrays.sort(arr);
        //生成字符串
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        //sha1加密
        MessageDigest md = null;
		String tmpStr = null;
 
		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
 
		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		System.out.println(tmpStr.equals(signature.toUpperCase()) +":tmpStr="+tmpStr+",signature="+signature);
		
        return tmpStr;
    }
	
	
	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}
 
	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
 
		String s = new String(tempArr);
		return s;
	}
	
	
	/**
	 *  发送公众号模板消息
	 * @param url  发送消息链接 
	 * @param jsonObject  发送消息内容 
	 * @throws Exception   抛出异常
	 * @author liuyunpeng
	 */
	public static  HttpsURLConnection  sendWeixinTemplateMessageByPost(String url,JSONObject jsonObject) throws Exception{
		HttpsURLConnection http = null;
		if(!url.isEmpty()){
		
				//1. 创建一个管理证书的任务管理器
				TrustManager[] tm = { new gds.application.weixin.weixin.dto.MyX509TrustManager() };
				SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new java.security.SecureRandom());
	
				//2.1 从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				URL getUrl = null;
				
				
				//3. 打开URL链接
				getUrl = new URL(url);
			    http = (HttpsURLConnection) getUrl.openConnection();
				http.setSSLSocketFactory(ssf);
	
				http.setRequestMethod("POST");
				http.setRequestProperty("Content-Type", "application/json;charset=utf-8"); 
				http.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
				http.setRequestProperty("Charset", "UTF-8");
				http.setDoOutput(true);
				http.setDoInput(true);
				http.connect();
				
				//4.1建立输入流，向指向的URL传入参数
				OutputStreamWriter   osw = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
				osw.write(jsonObject.toString());
				osw.flush();
				osw.close();
			    
			   // 4.2这句才是真正发送请求  
			   http.getInputStream();  
			   
			   // 关闭链接
			   http.disconnect();
		}
		
		return http;
	}

}
