/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.weixin.phoneWarningWeixin.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gds.application.weixin.constant.WeixinConstant;
import gds.application.weixin.listener.WeiXinAccessToken;
import gds.application.weixin.phoneWarningWeixin.constant.PhoneWarningConstant;
import gds.application.weixin.phoneWarningWeixin.dto.PhoneEarlyWarningDTO;
import gds.application.weixin.phoneWarningWeixin.dto.SensorSiteMangeEmpDTO;
import gds.application.weixin.phoneWarningWeixin.service.PhoneWarningWeixinService;
import gds.application.weixin.util.WeiXinUtils;
import gds.framework.base.controller.BaseController;
import gds.framework.exception.AppException;
import gds.framework.utils.PropertyUtils;
import net.sf.json.JSONObject;


/**
 *  微信公众号手机告警系统。手机告警Controller
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
@Controller
@RequestMapping("/phoneWarningWeixin")
public class PhoneWarningWeixinController extends BaseController {

	/**
	 * 注入告警Service接口
	 */
	@Autowired
	private PhoneWarningWeixinService phoneWarningWeixinServiceImpl;
	
	/**
	 * 查看手机监控预警信息
	 * @param request
	 * @param response
	 * @param model
	 * @param warningID String对象为警告ID
	 * @return String 跳转页面
	 * @throws AppException 抛出异常
	 */
	 @RequestMapping(value="/viewPhoneEarlyWarning.do")
	 public String   viewPhoneEarlyWarning(HttpServletRequest request, HttpServletResponse response,Model model,String warningID)throws AppException {
		  
	     // 获取告警信息
	     PhoneEarlyWarningDTO  phoneEarlyWarningDTO = this.phoneWarningWeixinServiceImpl.getPhoneWarningDTO(warningID);
	     
	     // 获取当前传感器负责人(巡线工)集合  	
	     List<SensorSiteMangeEmpDTO> sensorSiteMangeEmpList = this.phoneWarningWeixinServiceImpl.findSiteManagerList(phoneEarlyWarningDTO.getSensorNo());
	     
	     // 设置页面数据
	     model.addAttribute("phoneEarlyWarningDTO", phoneEarlyWarningDTO);
	     model.addAttribute("sensorSiteMangeEmpList", sensorSiteMangeEmpList);
	     model.addAttribute("phoneWarningDomainFieleURL", PropertyUtils.getProperty("phoneWarningDomainFieleURL"));
		  
		 // 返回页面
		 return "phoneWarningWeixin/viewPhoneEarlyWarning";
	 }
	  
	  
	  /**  
	     * 获取页面需要的配置信息的参数  
	     * @param request  
	     * @param url  String对象为当前网页URL
	     * @return  Map<String, Object> 返回Map集合结果集
	     * @throws Exception  抛出异常
	     */  
	    @ResponseBody  
	    @RequestMapping(value = "/getJsTicket.do")  
	    public Map<String, Object> getWeJsTicket(HttpServletRequest request, String url) throws Exception {  
	    	// 参数集合
	        Map<String, Object> map = new HashMap<String, Object>();  
        	// 获取时间戳 
        	String timeStamp = WeiXinUtils.create_timestamp();
        	// 获取jsapi_ticket
        	String jsapi_ticket = WeiXinAccessToken.getAccessToken(WeixinConstant.WEIXIN_TOKEN_JSTICKET);
        	// 获取随机数
        	String noncestr =  WeiXinUtils.create_nonce_str();
        	
	    	// 获取corpID
        	String appId =   PropertyUtils.getProperty("public_weixin_appID");
        	
        	// 获取字符串拼接
        	String string1 = "jsapi_ticket="+jsapi_ticket +"&noncestr="+noncestr+"&timestamp="+timeStamp+"&url="+url;
        	//获取安全签名
        	String  signature = WeiXinUtils.createSignature(string1);
    		
    		// 设置页面数据	
            map.put("appId", appId);  
            map.put("timestamp", timeStamp);  
            map.put("nonceStr", noncestr);  
            map.put("signature", signature);  
	        
	        // 返回页面数据集合
	        return map;  
	    } 
	    
	    /**
	     *  发送微信公众号消息
	     * @param request
	     * @param response
		 * @param paramsMap HashMap<String, String> 对象页面传入参数集合：
		 * <p>
		 * <li>key=warningType 警告类型： alarm(告警)、early(预警)</li>
		 * <li>key=userOpenID 发送派警用户openid</li>
		 * <li>key=warningID 对象警告ID</li>
		 * </p>
	     * @throws Exception  抛出异常
	     */
	    @ResponseBody  
	    @RequestMapping(value = "/sendUsersMessage.do")  
	    public void  sendUsersMessage(HttpServletResponse response, @RequestParam HashMap<String, String> paramsMap,@RequestBody String param) throws Exception {
	    	// 消息提示内容 默认失败
		    String messageContent = PhoneWarningConstant.STATE_CODE_faile;
		
			// 获取微信公众号模板URL链接
			String url = PropertyUtils.getProperty("public_weixin_messageTemplateURL")+WeiXinAccessToken.getAccessToken(WeixinConstant.WEIXIN_TOKEN_ACCESSTOKEN);
			
			// 获取微信公众号消息JSON 内容
			JSONObject  messageJSONObject  = getWeixinPublicMessageData(paramsMap,param);
			
			// 发送微信公众号消息
			HttpsURLConnection httpsURLConnection = null;
			
			// 预警
			if( PhoneWarningConstant.PHONE_WARNING_EARLY.equals(paramsMap.get("warningType")) ){
				
				// 获取手机告警warningID
				String warningID = messageJSONObject.get("messgeURL").toString().split("=")[1];
				
				// 获取手机告警DTO信息
				PhoneEarlyWarningDTO  phoneEarlyWarningDTO = this.phoneWarningWeixinServiceImpl.getPhoneWarningDTO(warningID);
				
				// 获取当前传感器的负责人公众号openID集合
			    List<SensorSiteMangeEmpDTO> employeeList =  this.phoneWarningWeixinServiceImpl.findEarlyWarningOpenidList(phoneEarlyWarningDTO.getSensorNo());
				
			    // 发送预警消息
				for( int i = 0; i < employeeList.size(); i++){
					// 设置公众号发送人openid
					JSONObject tempJSON = JSONObject.fromObject(messageJSONObject);
					tempJSON.put("touser", employeeList.get(i).getWechatNo());
					 
					// 发送微信公众号消息
			    	httpsURLConnection = WeiXinUtils.sendWeixinTemplateMessageByPost(url,tempJSON);
			    	
			    	// 主要用于日志记录发送消息情况
			    	System.out.println("开始预警发送消息,第"+ i +"个JSON内容："+tempJSON);
				}
				
			}else{ // 告警
				// 主要用于日志记录发送消息情况
		    	System.out.println("开始告警发送消息JSON内容："+messageJSONObject);
		    	
				// 发送微信公众号消息
			    httpsURLConnection = WeiXinUtils.sendWeixinTemplateMessageByPost(url,messageJSONObject);
			}
			
			// 返回消息执行后JSON
			messageJSONObject = new JSONObject();
			
			// 获取发送微信公众号消息状态返回值
			int httpResponseCode = httpsURLConnection.getResponseCode();
			if(httpResponseCode == 200){
				messageContent = PhoneWarningConstant.STATE_CODE_success ;
			}
			
			// 设置执行发送消息码值
			messageJSONObject.put(PhoneWarningConstant.STATE_CODE, httpResponseCode);
			// 设置执行发送消息与否内容
			messageJSONObject.put(PhoneWarningConstant.MESSAGE, messageContent);
			
			// 主要用于日志记录发送消息情况
			System.out.println("结束发送消息内容："+messageJSONObject);
			
			//设置JSON数据跨域 编码
			WeiXinUtils.setResponseContent(response);
			response.getWriter().write(messageJSONObject.toString());
 		   
	    }
	    
	   /**
	     *  封装微信公众号消息内容JSON
	     * @param paramsMap  HashMap<String, String> 参数集合
		 * <p>
		 * <li>key=warningType 警告类型： alarm(告警)、early(预警)</li>
		 * <li>key=userOpenID 发送派警用户openid</li>
		 * <li>key=warningID 对象警告ID</li>
		 * </p>
	     * @param param String独享为JSON字符串
	 * @param phoneWarningDTO 
	     * @return 返回微信公众号消息JSON
	     * @throws AppException  抛出异常
	     */
	    private JSONObject getWeixinPublicMessageData(HashMap<String, String> paramsMap, String param) throws AppException {

	    	// 警告类型
	    	String warningType = "";
	    	// 获取模板消息ID
	    	String  template_id = "";
	    	// 消息URL链接
	    	String messgeURL = "";
	    	// 消息接收人openid
	    	String userOpenID = "";
	    	// 消息标题
	    	String title = "";
	    	// 告警1
	    	String keyword1 = "";
	    	// 告警2
	    	String keyword2 = "";
	        // 备注信息
	    	String  remark = "";
	    	
	    	// 获取告警类型
	    	warningType = paramsMap.get("warningType");
	    	
	    	// 预警
	    	if( PhoneWarningConstant.PHONE_WARNING_EARLY.equals(warningType) ){
	    		
	    		// 获取模板消息ID
		        template_id = PropertyUtils.getProperty("public_weixin_messageTemplateID_yjts");
		        
                JSONObject paramObject = JSONObject.fromObject(param);
		        // 消息链接URL
		        messgeURL = paramObject.get("messgeURL").toString();
		        
		        // 告警title
		        title = paramObject.get("title").toString();
		        
		        // 告警内容
		        keyword1 = paramObject.get("warningContent").toString();
		        
		        // 告警时间
		        keyword2 = paramObject.get("warningTime").toString();
		        
		        // 告警备注信息 
		        remark  = paramObject.get("remark").toString();
		        
	    	}

	    	// 告警
	    	if( PhoneWarningConstant.PHONE_WARNING_ALARM.equals(warningType) ){
	    		
	    	 	// 获取模板消息ID
		        template_id = PropertyUtils.getProperty("public_weixin_messageTemplateID_gjts");
		    	
		    	// 获取告警信息DTO集合
			    PhoneEarlyWarningDTO  phoneEarlyWarningDTO = this.phoneWarningWeixinServiceImpl.getPhoneWarningDTO(paramsMap.get("warningID").toString());
		    	    	
		    	// 获取手机告警链接
			    messgeURL = PropertyUtils.getProperty("phoneWarningDomainURL") +"/phoneWarningWeixin/viewPhoneAlarmWarning.do?warningID="+phoneEarlyWarningDTO.getWarningID();
			    
		        // 告警title
		        title = phoneEarlyWarningDTO.getWarningTypeName() +"告警：";
		        
		        // 告警时间
		        keyword1 = phoneEarlyWarningDTO.getWarningTime();
		        
		        // 告警内容
		        keyword2  = phoneEarlyWarningDTO.getWarningAddress()
			    		+"【"+ phoneEarlyWarningDTO.getLongitude()+"，"+phoneEarlyWarningDTO.getLatitude() +"】发生"
			    		+"【"+ phoneEarlyWarningDTO.getWarningTypeName() +"】告警，请立即前往查看。";
		        
		        // 备注信息
		        remark =  "点击查看告警信息！";
		        
		        // 获取微信公众号消息接收人openID
		        userOpenID = paramsMap.get("userOpenID").toString();
	    	}
	    	
	    	// 封装公众号模板消息
	    	JSONObject jsonObject = new JSONObject();
	    	// 消息接收人openID
	    	jsonObject.put("touser", userOpenID);
	    	// 消息模板ID
	    	jsonObject.put("template_id", template_id);
	    	// 消息模板链接
	    	jsonObject.put("url", messgeURL);
	    	// 头部颜色
	    	jsonObject.put("topcolor", "#FF0000");
	    	
	    	// 消息模板内容
	        JSONObject  jsonData= new JSONObject();
	    	
	    	// 消息模块first.DATA
	    	JSONObject  jsonObjectFristValue = new JSONObject();
	    	jsonObjectFristValue.put("value", title);
	    	jsonObjectFristValue.put("color", "#FF0000");
	    	jsonData.put("first", jsonObjectFristValue);
	    	
	    	// 消息模块keyword1.DATA
	        JSONObject  jsonObjectkeyword1 = new JSONObject();
	    	jsonObjectkeyword1.put("value", keyword1);
	    	// 红色
	    	jsonObjectkeyword1.put("color", "#FF0000");
	    	jsonData.put("keyword1", jsonObjectkeyword1);
	    	
	    	// 消息模块keyword2.DATA
	    	JSONObject  jsonObjectkeyword2 = new JSONObject();
	    	jsonObjectkeyword2.put("value", keyword2);
	    	// 红色
	    	jsonObjectkeyword2.put("color", "#FF0000");
	    	jsonData.put("keyword2", jsonObjectkeyword2);
	    	
	    	// 消息备注remark.DATA
	    	JSONObject  remarkJsonObject = new JSONObject();
	    	remarkJsonObject.put("value", remark );
	    	// 蓝色
	    	remarkJsonObject.put("color", "#173177");
	    	jsonData.put("remark", remarkJsonObject);
	    	
	    	jsonObject.put("data", jsonData);
	    	
	    	// 返回微信公众号消息JSON
	    	return jsonObject;
		}


		/**
	     *  修改警告状态
	     * @param request
	     * @param response
	     * @param paramsMap
	     * @throws Exception
	     */
		@ResponseBody  
	    @RequestMapping(value = "/modifyPhoneWarning.do")  
		public void modifyPhoneWarning(HttpServletRequest request,
				HttpServletResponse response,@RequestParam HashMap<String, String> paramsMap) throws Exception {
	    	
	    	// 获取警告ID
	    	String  warningID = paramsMap.get("warningID");
	    	// 获取修改警告状态
	    	String  warningStatusCode = paramsMap.get("waringStatusCode");
	    	          
	    	JSONObject jsonObject = new JSONObject();
	    	jsonObject.put("warningid", warningID);
	    	jsonObject.put("waringstatuscode", warningStatusCode);
	    	jsonObject.put("isconfirm", true);
	    	
	    	// 封装URL链接
	    	String url = PropertyUtils.getProperty("pipelinewebservice_modyWaringState")+warningID;
	    	// 修改警告状态
	    	HttpURLConnection  httpURLConnection = WeiXinUtils.sendPhoneWarnByPost(url, jsonObject);
	    	
	    	// 获取修改警告状态返回值
			int httpResponseCode = httpURLConnection.getResponseCode();
			if( httpURLConnection != null ){
				httpURLConnection.disconnect();
			}
			
	    	// 提示信息
			String messageContent = "设置成功！";
			if(httpResponseCode != 200){
				messageContent = "设置失败！" ;
			}
	
			// 设置JSON数据跨域 编码
			WeiXinUtils.setResponseContent(response);
			response.getWriter().write(messageContent);
		}
		
		/**
		 *  查看手机告警信息
		 * @param request
		 * @param response
		 * @param model
		 * @param warningID String对象为警告ID
		 * @throws AppException 抛出异常
		 */
		@RequestMapping("/viewPhoneAlarmWarning.do")
		public String viewPhoneAlarmWarning(HttpServletRequest request,
				HttpServletResponse response, Model model,String warningID)throws AppException{
	
			 // 获取告警信息
		     PhoneEarlyWarningDTO  phoneEarlyWarningDTO = this.phoneWarningWeixinServiceImpl.getPhoneWarningDTO(warningID);
		     
		     // 获取导航链接
		     String  apisMapURL =  "http://apis.map.qq.com/uri/v1/marker?marker=coord:"+ phoneEarlyWarningDTO.getLatitude() +","+phoneEarlyWarningDTO.getLongitude();
		     
		     // 设置页面数据
		     model.addAttribute("phoneEarlyWarningDTO", phoneEarlyWarningDTO);
		     model.addAttribute("apisMapURL", apisMapURL);
		     model.addAttribute("phoneWarningDomainFieleURL", PropertyUtils.getProperty("phoneWarningDomainFieleURL"));
			  
			 // 返回页面
			 return "phoneWarningWeixin/viewPhoneAlarmWarning";
		}
		
		/**
		 * 
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@SuppressWarnings("resource")
		@RequestMapping("/getImageInof.do")
		public void   getImageInof(HttpServletRequest request,HttpServletResponse response) throws Exception {
			
			byte[]  buf = new byte[1024];
			
			String filname = "E:\\demoImg\\111.160.74.154_01_20180818141708473_TIMING.jpg";
			InputStream input = new FileInputStream(filname);
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			int ch = 0;
			while ((ch = input.read()) != -1) {
				byteArray.write(ch);
			}
			
			buf = byteArray.toByteArray();

			java.io.OutputStream out = response.getOutputStream();
	     	out.write(buf);
			out.flush();
			out.close();
		}
}
