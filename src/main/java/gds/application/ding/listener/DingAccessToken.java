/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.ding.listener;

import gds.framework.utils.PropertyUtils;

import java.util.TimerTask;
import java.util.logging.Logger;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiGettokenResponse;


/**
 * 钉钉公共模块：access_token工具类
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public class DingAccessToken extends TimerTask {
	
	/** 钉钉平台AccessToken */
	public static  String ding_access_token = null;
	
	/** 钉钉平台jsapi_ticket */
	public static  String ding_jsapi_ticket = null;

	@Override
	public void run() {
		// 执行
		excute();
	}
	
	private void excute() {
		try {
            
			Logger log = Logger.getLogger("钉钉端获取Access_token唯一票据！");
			
			// 获取accessTokenURL 
			String accessTokenURL = PropertyUtils.getProperty("dingAccessTokenInsideURL");
			
			// 获取CorpId
			String corpId = PropertyUtils.getProperty("dingCorpId");
			
			// 获取corpSecret
			String corpSecret = PropertyUtils.getProperty("dingCorpSecret");
			
			DingTalkClient accessTokenClient = new DefaultDingTalkClient(accessTokenURL);
			OapiGettokenRequest accessTokenRequest = new OapiGettokenRequest();
			accessTokenRequest.setCorpid(corpId);
			accessTokenRequest.setCorpsecret(corpSecret);
			accessTokenRequest.setHttpMethod("GET");
			OapiGettokenResponse response = accessTokenClient.execute(accessTokenRequest);
			
			// 获取accessToken
			ding_access_token = response.getAccessToken();
			
		    log.info("手机智能监控平台钉钉AccessToken :" + response.getBody());
		    
		    // 获取钉钉jsapi_ticket
		    String jsapi_ticketURL = PropertyUtils.getProperty("dingJsapiTicketURL")+ding_access_token;
		    
			DingTalkClient jsTicketClient = new DefaultDingTalkClient(jsapi_ticketURL);
			OapiGetJsapiTicketRequest  jsTicketClientRequest = new OapiGetJsapiTicketRequest();
			jsTicketClientRequest.setHttpMethod("GET");
			OapiGetJsapiTicketResponse jsTicketResponse =  jsTicketClient.execute(jsTicketClientRequest, ding_access_token);

			// 获取jsapi_ticket
			ding_jsapi_ticket = jsTicketResponse.getTicket();
		    log.info("手机智能监控平台钉钉jsapi_ticket :" + jsTicketResponse.getBody());
			
		} catch (Exception e) {    
			e.printStackTrace();   
			try {
				//线程睡眠1分钟后，再次执行
				Thread.sleep(60000);  
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			excute();
		}
	}

}
