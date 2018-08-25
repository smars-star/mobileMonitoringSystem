package gds.application.weixin.weixin.controller;

import gds.application.weixin.constant.WeixinConstant;
import gds.application.weixin.listener.WeiXinAccessToken;
import gds.application.weixin.phoneWarningWeixin.dto.WeixinPublicEmployeeDTO;
import gds.application.weixin.phoneWarningWeixin.service.PhoneWarningWeixinService;
import gds.application.weixin.util.WeiXinUtils;
import gds.application.weixin.weixin.util.AesException;
import gds.application.weixin.weixin.util.WXBizMsgCrypt;
import gds.framework.base.controller.BaseController;
import gds.framework.exception.AppException;
import gds.framework.utils.PropertyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/** 
 *  微信公共模块：微信控制器（主要是用来处理对微信信息发送或接收的信息）
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
@Controller
public class WeixinController extends BaseController{

	/**
	 * 注入告警Service接口
	 */
	@Autowired
	private PhoneWarningWeixinService phoneWarningServiceImpl;

	 /**
	   *  开启微信办公系统-回调模式  
	 * @param <WXBizMsgCrypt>
	   * @param request
	   * @param response
	   * @param model
	   * @return
	 * @throws IOException 
	 * @throws AesException 
	   */
	@RequestMapping(value="weixin/openCallBackMode.do")
	  public   void  openCallBackMode(HttpServletRequest request, HttpServletResponse response,Model model) throws IOException, AesException{
		   
		   //用于生成签名
		   String sToken = PropertyUtils.getProperty("token");
		   //企业 CorpID
		   String sCorpID = PropertyUtils.getProperty("corpID"); 
		   //是AES密钥的Base64编码
		   String sEncodingAESKey = PropertyUtils.getProperty("encodingAESKey");
	       
		   //对微信发送的消息进行解密
		   WXBizMsgCrypt wxcpt = new  WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
		   	
		   //微信加密签名，msg_signature结合了企业填写的token、请求中的timestamp、nonce参数、加密的消息体
		   String msg_signature = request.getParameter("msg_signature");
		   String timestamp = request.getParameter("timestamp");//微信时间戳
		   String nonce = request.getParameter("nonce");//随机数
		   //	加密的随机字符串，以msg_encrypt格式提供。需要解密并返回echostr明文，解密后有random、msg_len、msg、$CorpID四个字段，其中msg即为echostr明文        只有第一次调用时才用
		   String echostr=request.getParameter("echostr");
		  
		   //解析出来后的微信明文 
	       String 	sEchoStr = wxcpt.VerifyURL(msg_signature, timestamp,nonce, echostr);
		   
	       //返回明文
	       response.getWriter().println(sEchoStr);
	  }
	
	
	
	/**
	   *  开启微信公众号-回调模式  
	   * @param request
	   * @param response
	   * @param model
	   * @throws AppException 抛出异常  
	   */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="weixin/openWechatCallBackMode.do")
	  public   void   openWechatCallBackMode(HttpServletRequest request, HttpServletResponse response,Model model) throws AppException{
	
		try {
			// 加密的随机字符串，以msg_encrypt格式提供。需要解密并返回echostr明文，解密后有random、msg_len、msg、$CorpID四个字段，其中msg即为echostr明文
			// 只有第一次调用时才用
			String echostr = request.getParameter("echostr");
			
			// 如果是开启回调模式
			if( StringUtils.isNotEmpty(echostr) ){
				// 返回明文消息内容
				sendSignature(request, response, echostr);
			}else{
				// 获取自动回复的内容
				Map map  = parseXml(request);
				System.out.println(map);
				
				// 微信公众号人员Map集合
				Map<String,WeixinPublicEmployeeDTO> employeeMap = new HashMap<>();
				// 获取微信公众号告警组ID
				String  pubic_weixin_phoneWarnGroupid = PropertyUtils.getProperty("pubic_weixin_phoneWarnGroupid");
				// 获取微信公众号关注人员集合
		        List<WeixinPublicEmployeeDTO> employeeList  = this.phoneWarningServiceImpl.findWeixinPublicEmployeeList(pubic_weixin_phoneWarnGroupid);
                if( !employeeList.isEmpty() ){
                	 for(WeixinPublicEmployeeDTO weixinPublicEmployeeDTO : employeeList ){
                		 employeeMap.put( weixinPublicEmployeeDTO.getOpenid(), weixinPublicEmployeeDTO);
                	 }
                }
				
				if( map.containsKey("EventKey") && "V1001_GOOD".endsWith(map.get("EventKey").toString()) ){
					// 发送消息URL
					String  messageURL  = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+WeiXinAccessToken.getAccessToken(WeixinConstant.WEIXIN_TOKEN_ACCESSTOKEN );
                   
					// 微信公众号发送消息人openid
					String openid = map.get("FromUserName").toString();
					
					// 封装自动发送消息内容
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("touser", openid);
					jsonObject.put("msgtype", "text");
					
					// 微信公众号欢迎语
					String  contentStr =  "大兄弟，你来了， 欢迎，欢迎，热烈欢迎！！";
					if( employeeMap.containsKey(openid) ){
						WeixinPublicEmployeeDTO weixinPublicEmployeeDTO = employeeMap.get(openid);
						contentStr = "来自"+weixinPublicEmployeeDTO.getCountry()+"的" + weixinPublicEmployeeDTO.getNickname()+"  欢迎，欢迎，热烈欢迎！！ ^_^";
					}
					
					JSONObject  jsonObjectText = new JSONObject();
					jsonObjectText.put("content",contentStr );
					jsonObject.put("text", jsonObjectText);
					
					// 发送消息
					WeiXinUtils.sendWeixinTemplateMessageByPost(messageURL, jsonObject);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	}
	
	
	/**
	 *  开启微信公众号-回调模式   返回明文消息内容
	 * @param request
	 * @param response
	 * @param echostr  String对象为加密的随机字符串
	 * @throws IOException 抛出异常
	 */
	public  void  sendSignature(HttpServletRequest request, HttpServletResponse response,String echostr) throws IOException{
		// 用于生成签名
		String sToken = "gwdsgzhtoken";
		// 是AES密钥的Base64编码
		// String sEncodingAESKey = "YHacAn2mW52EwJ0ijfYCAIAUC7Ov4pdAE6fJDWDFBrM";

		// 微信加密签名，msg_signature结合了企业填写的token、请求中的timestamp、nonce参数、加密的消息体
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");// 微信时间戳
		String nonce = request.getParameter("nonce");// 随机数

		// 加密
		String sEchoStr = WeiXinUtils.checkSignature(sToken, signature,timestamp, nonce);

		PrintWriter out = response.getWriter();
		// 校验签名
		if (sEchoStr != null && sEchoStr != ""
				&& sEchoStr.equals(signature.toUpperCase())) {
			System.out.println("签名校验通过。");
			out.print(echostr);
		} else {
			System.out.println("签名校验失败。");
		}
		
	}
	
	
	/**
	 *  获取从微信公众号返回的信息
	 * @param request
	 * @return
	 * @throws Exception  抛出异常
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map map = new HashMap();
 
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
 
        // 遍历所有子节点
        for (Element e : elementList){
        	map.put(e.getName(), e.getText());
        }
 
        // 释放资源
        inputStream.close();
        inputStream = null;
 
        return map;
    }
	
	/**
	 * 微信公众号调用频率限制(最大清除数量不能大于3)
	 * @param request
	 * @throws AppException 抛出异常
	 */
	@RequestMapping(value="weixin/clearWechatAPI.do")
	public void clearWechatAPI(HttpServletRequest request,HttpServletResponse response) throws AppException{
		
		try {
			// 初始化日志打印工具Logger
			Logger log = Logger.getLogger("微信公众号调用频率限制！");
			
			// 清除微信公众号调用频率限制
			String  clearWechatAPICountURL = PropertyUtils.getProperty("public_weixin_clearWechatAPICountURL")+WeiXinAccessToken.getAccessToken(WeixinConstant.WEIXIN_TOKEN_ACCESSTOKEN);
			
			// 封装数据
			JSONObject jsonObject1 = new JSONObject();
			jsonObject1.put("appid", PropertyUtils.getProperty("public_weixin_appID"));
			
			// 返回执行清除清除微信公众号调用频率次数
			String errcode = WeiXinUtils.getWeiXinInfoByPost(clearWechatAPICountURL, jsonObject1, "errcode");
		    // 清除调用微信公众号API频率限制内容
			String errmesage = "清除微信公众号调用频率限制超过3次！"+errcode;
			// 48006：超出次数
			if( "48006".endsWith(errcode) ){
				log.info(errmesage);
			}
			
			// 返回页面数据
			response.getOutputStream().print(errmesage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
}
