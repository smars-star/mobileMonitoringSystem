package gds.application.weixin.listener;

import gds.application.weixin.constant.WeixinConstant;
import gds.application.weixin.util.WeiXinUtils;
import gds.framework.exception.AppException;
import gds.framework.utils.PropertyUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;


/** 
 *  微信公共模块：微信公众号\微信企业号获取访问access_token类 
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public class WeiXinAccessToken implements ServletContextListener {

	
	/** 手机智能监控平台AccessToken */
	private static  String phoneWarn_access_token = "";
	
	/** jsapi_ticket是H5应用调用企业微信JS接口的临时票据  */
	private  static String jsapi_ticket = "";

	private static Timer timer = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// access_token有效时间
		String accessTokenTimeStr = PropertyUtils.getProperty("accessTokenTime") ;
		int accessTokenTime = Integer.parseInt(accessTokenTimeStr);

		timer = new Timer(true);// 创建一个新计时器，可以指定其相关的线程作为守护程序运行。
		// 设置任务计划，启动和间隔时间
		timer.schedule(new contractTask(), 0, accessTokenTime);

	}
	
	
	/**
	 * 获取微信公众号access_token 
	 * @param tokenType String对象为token类型：accessToken、jsapi_ticket
	 * @return  返回要获取的token
	 * @throws AppException 
	 * @throws Exception  抛出异常
	 */
	public static  String  getAccessToken(String tokenType) throws AppException {
		// token内容
		String tokenStr = "";
		
		try {
			// 获取服务器IP范围，主要是用来判断access_token是否有效
			String callbackipURL = PropertyUtils.getProperty("public_weixin_callbackipURL")+phoneWarn_access_token;
		
			// 获取accessToken是否有效值
			String accessTokenErrcode = WeiXinUtils.getWeiXinInfo(callbackipURL, "errcode");
			
			// 判断accessToken是否有效
			if(  StringUtils.isNotEmpty(phoneWarn_access_token) 
					&& WeixinConstant.WEIXIN_TOKEN_ERRCODE.equals(accessTokenErrcode) )	{
				
				System.out.println("tokenType:"+tokenType+" ,callbackipURL="+callbackipURL+" ,errcodeStr="+accessTokenErrcode);
				System.out.println("获取前accessToken:"+phoneWarn_access_token+" ,jsapi_ticket:"+jsapi_ticket);
				
				// 获取新的AccessToken
			    new contractTask().excute();
				
				System.out.println("获取后accessToken:"+phoneWarn_access_token+" ,jsapi_ticket:"+jsapi_ticket);
			}
			
			// accessToken (全局唯一票据)
			if( WeixinConstant.WEIXIN_TOKEN_ACCESSTOKEN.equals(tokenType) ){
				tokenStr = phoneWarn_access_token;
			}
			
			// JS接口的临时票据
			if( WeixinConstant.WEIXIN_TOKEN_JSTICKET.equals(tokenType) ){
				tokenStr = jsapi_ticket;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 返回accessToken
		return tokenStr;
	}
	
	

	/**
	 * 
	* <p>Title: 用类每间隔2个小时来重新获取一下access_tokne的静态内部类</p>
	* <p>Description: 每间隔两个小时重新获取一下和微信企业类之间的 access_token</p>
	* <p>Copyright: Copyright (c) 2017</p>
	* <p>Company: 长城数字[www.e-u.cn]</p>
	* @author liuyunpeng
	 * @version $Revision: 1.14 $
	 */
	static class contractTask extends TimerTask {
		
		public void run() {
			
			// 获取accss_token 
			excute();
			
		}

		private void excute() {
			try {
                
				Logger log = Logger.getLogger("微信端获取Access_token企业号唯一票据！");
				
				// 手机智能监控平台AccessToken URL链接
			    String phoneWarnUrl = PropertyUtils.getProperty("public_weixin_accessTokenURL") + "&appid=" + PropertyUtils.getProperty("public_weixin_appID") + "&secret="+PropertyUtils.getProperty("public_weixin_appsecret") ;
			    String tempPhoneWarn_access_token = WeiXinUtils.getWeiXinInfo(phoneWarnUrl, "access_token");
			    phoneWarn_access_token = tempPhoneWarn_access_token;
			    log.info("手机智能监控平台AccessToken :" + phoneWarn_access_token);
			    
				String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ phoneWarn_access_token +"&type=jsapi";
				String tempJsapi_ticket = WeiXinUtils.getWeiXinInfo(jsapi_ticket_url, "ticket");
				jsapi_ticket = tempJsapi_ticket;
				log.info("获取jsapi_ticket:" + jsapi_ticket);
				
			} catch (Exception e) {    
				e.printStackTrace();   
				try {
					Thread.sleep(60000);  //线程睡眠1分钟后，再次执行
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				excute();
			}
		}

	}

}
