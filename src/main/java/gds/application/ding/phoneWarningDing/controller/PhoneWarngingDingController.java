/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.ding.phoneWarningDing.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationGetsendresultRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationGetsendresultResponse;
import com.taobao.api.internal.util.StringUtils;

import gds.application.ding.listener.DingAccessToken;
import gds.application.ding.util.DingTicketUtils;
import gds.application.weixin.phoneWarningWeixin.constant.PhoneWarningConstant;
import gds.application.weixin.phoneWarningWeixin.dto.PhoneEarlyWarningDTO;
import gds.application.weixin.phoneWarningWeixin.service.PhoneWarningWeixinService;
import gds.application.weixin.util.WeiXinUtils;
import gds.framework.base.controller.BaseController;
import gds.framework.exception.AppException;
import gds.framework.utils.PropertyUtils;
import net.sf.json.JSONObject;


/**
 * 钉钉手机告警系统：钉钉手机告警Controller
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
@Controller
@RequestMapping("/phoneWarngingDing")
public class PhoneWarngingDingController extends BaseController {
	
	/**
	 * 注入告警Service接口
	 */
	@Autowired
	private PhoneWarningWeixinService phoneWarningWeixinServiceImpl;	
	
	/**
	 * 查看钉钉预警信息
	 * @param request
	 * @param response
     * @param model Model对象用于设置页面数据
	 * @param warningID String对象为警告ID
	 * @return String 跳转页面
	 * @throws AppException 抛出异常
	 */
	@RequestMapping("/viewPhoneWarning.do")
	public String viewPhoneWarning(HttpServletRequest request, HttpServletResponse response,Model model,String warningID)throws AppException{
	    // 获取告警信息
	    PhoneEarlyWarningDTO  phoneEarlyWarningDTO = this.phoneWarningWeixinServiceImpl.getPhoneWarningDTO(warningID);
	        
	    // 设置页面数据
	    model.addAttribute("phoneEarlyWarningDTO", phoneEarlyWarningDTO);
	    
	    // 返回页面
	    return "phoneWarngingDing/viewPhoneWarning";
	}
	
	/**
	 *  获取钉钉JsTicket
     * @param model Model对象用于设置页面数据
     * @param url String对象为当前网页URL
	 * @throws Exception  抛出异常
	 */
	@ResponseBody
	@RequestMapping("/getDingJsTicket.do")
	public Map<String, String>  getDingJsTicket(Model model,String url) throws Exception{
		// 参数Map集合
		Map<String,String> map = new HashMap<>();
		
		// 获取时间戳 
    	String timeStamp = DingTicketUtils.create_timestamp();
    	
    	// 获取jsapi_ticket
    	String jsapi_ticket = DingAccessToken.ding_jsapi_ticket;
    	
    	// 获取随机数
    	String nonceStr = DingTicketUtils.create_nonce_str();
    	
    	// 获取corpID
    	String corpId = PropertyUtils.getProperty("dingCorpId");
       
		// 获取钉钉手机告警应用ID
    	String dingPhoneWarngingAgentID  = PropertyUtils.getProperty("dingPhoneWarngingAgentID");
    	
    	//获取安全签名
    	String  signature = DingTicketUtils.getDingSignature(jsapi_ticket, nonceStr, timeStamp, url);
    	
    	// 设置页面数据
    	map.put("agentId", dingPhoneWarngingAgentID);
    	map.put("corpId", corpId);
    	map.put("timeStamp", timeStamp);
    	map.put("nonceStr", nonceStr);
    	map.put("signature", signature);
    	
    	// 返回Map参数集合
    	return map;
	}

    /**
     *  发送钉钉ActionCard消息
     * @param response
	 * @param paramsMap HashMap<String, String> 对象页面传入参数集合：
	 * <p>
	 * <li>key=warningType 警告类型： alarm(告警)、early(预警)</li>
	 * <li>key=emplId 发送派警用户emplId</li>
	 * <li>key=warningID 对象警告ID</li>
	 * </p>
     * @param param String对象为JSON字符串
     * @throws AppException 抛出异常
     */
	@ResponseBody
	@RequestMapping("/sendDingActionCardMessage.do")
	public void sendDingActionCardMessage(HttpServletResponse response,@RequestParam HashMap<String, String> paramsMap,@RequestBody String param) throws AppException{
		// 消息提示内容 默认失败
    	String messageContent = PhoneWarningConstant.STATE_CODE_faile;
	
		try {
			
			// 获取钉钉发送消息链接
			String dingSendMessageURL =PropertyUtils.getProperty("dingSendMessageURL");
			// 获取钉钉发送消息是否成功链接
			String dingSendMessageResultURL = PropertyUtils.getProperty("dingSendMessageResultURL");
			// 获取钉钉手机告警应用ID
			long  dingPhoneWarngingAgentID  = Integer.parseInt(PropertyUtils.getProperty("dingPhoneWarngingAgentID"));
			
			DingTalkClient client = new DefaultDingTalkClient(dingSendMessageURL);
			OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
			request.setAgentId(dingPhoneWarngingAgentID);
			request.setUseridList(paramsMap.get("emplId"));
			request.setToAllUser(false);
			
			OapiMessageCorpconversationAsyncsendV2Request.Msg messageActionCard = getDingActionCardMessageData(paramsMap,param);
			request.setMsg(messageActionCard);

			OapiMessageCorpconversationAsyncsendV2Response messsageResponse = client.execute(request,DingAccessToken.ding_access_token);
			System.out.println("sendMessgae:"+messsageResponse.getBody());
			
			// 获取发送消息结果
			DingTalkClient resultClient = new DefaultDingTalkClient(dingSendMessageResultURL);
			OapiMessageCorpconversationGetsendresultRequest resultRequest = new OapiMessageCorpconversationGetsendresultRequest();
			resultRequest.setAgentId(188281408L);
			resultRequest.setTaskId(messsageResponse.getTaskId());
			OapiMessageCorpconversationGetsendresultResponse  resultResponse = resultClient.execute(resultRequest, DingAccessToken.ding_access_token);
			System.out.println("resultResponse"+resultResponse.getBody());

			// 获取发钉钉消息状态返回值
			if( StringUtils.isEmpty(resultResponse.getErrmsg() )  ){
				messageContent = PhoneWarningConstant.STATE_CODE_success ;
			}
			
			// webservice接口返回字符串
			JSONObject messageJSON =  new JSONObject();
			
			// 设置执行发送消息码值
			messageJSON.put(PhoneWarningConstant.STATE_CODE, resultResponse.getErrorCode());
			// 设置执行发送消息与否内容
			messageJSON.put(PhoneWarningConstant.MESSAGE, messageContent);
			
			//设置JSON数据跨域 编码
			WeiXinUtils.setResponseContent(response);
			response.getWriter().write(messageJSON.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  封装钉钉消息内容JSON
	 * @param paramsMap
	 * <p>
	 * <li>key=warningType 警告类型： alarm(告警)、early(预警)</li>
	 * <li>key=emplId 发送派警用户emplId</li>
	 * <li>key=warningID 对象警告ID</li>
	 * </p>
	 * @param param String独享为JSON字符串
	 * @return JSONObject 返回钉钉消息JSON
	 * @throws AppException 
	 */
	private OapiMessageCorpconversationAsyncsendV2Request.Msg getDingActionCardMessageData(HashMap<String, String> paramsMap,
			String param) throws AppException {
		// 封装钉钉ActionCard消息
		OapiMessageCorpconversationAsyncsendV2Request.Msg msg  = null;
		
		try {
			// 获取告警类型
			String warningType = paramsMap.get("warningType");
			
			// 消息标题
			String titile = "";
			// 消息内容，支持markdown，语法参考标准markdown语法
			String markdown = "";
			// 使用整体跳转ActionCard样式时的标题，必须与single_url同时设置
			String single_title = "";
			// 消息点击链接地址   当发送消息为E应用时支持E应用跳转链接eapp://page?query,其中page表示E应用的页面地址, query表示page的onLoad的query参数。
			// 例如param1=aa&param2=bb, 当参数值有特殊字符时需要使用urlEncode进行编码处理
			String single_url = "";
			
			// 告警
			if( PhoneWarningConstant.PHONE_WARNING_ALARM.equals(warningType) ){
				// 获取告警信息DTO集合
			    PhoneEarlyWarningDTO  phoneEarlyWarningDTO = this.phoneWarningWeixinServiceImpl.getPhoneWarningDTO(paramsMap.get("warningID").toString());
			   
			    // 告警title
			    titile = phoneEarlyWarningDTO.getWarningTypeName() +"告警";
			  
			    // 消息内容
			    markdown = phoneEarlyWarningDTO.getWarningTypeName() +"告警：" + phoneEarlyWarningDTO.getWarningAddress()
			    		+"【"+ phoneEarlyWarningDTO.getLongitude()+"，"+phoneEarlyWarningDTO.getLatitude() +"】发生"
			    		+"【"+ phoneEarlyWarningDTO.getWarningTypeName() +"】告警，请立即前往查看。";
			    
			    // 消息点击链接地址
			    single_url = "https://uri.amap.com/marker?position="+phoneEarlyWarningDTO.getLongitude()+","+phoneEarlyWarningDTO.getLatitude();
			   
			    // 整体跳转ActionCard样式时的标题
			    single_title =  "点击链接导航至告警点";
			}
			
			// 预警
	    	if( PhoneWarningConstant.PHONE_WARNING_EARLY.equals(warningType) ){
	    		// 把数据转换成JSON
                JSONObject paramObject = JSONObject.fromObject(param);
		        
		        // 告警title
                titile = paramObject.get("title").toString();
		        
		        // 告警内容
                markdown = paramObject.get("warningContent").toString();
		        
		        // 告警备注信息 
                single_title  = paramObject.get("remark").toString();
                
                // 消息链接URL
                single_url = paramObject.get("messgeURL").toString();
	    	}
			
			// 设置Message 消息
			msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		    msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
			msg.getActionCard().setTitle(titile);
			msg.getActionCard().setMarkdown(markdown);
			msg.getActionCard().setSingleTitle(single_title);
			msg.getActionCard().setSingleUrl(single_url);
			msg.setMsgtype("action_card");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 返回钉钉消息内容
		return msg;
	}
}
