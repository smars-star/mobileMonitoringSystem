/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.weixin.phoneWarningWeixin.listener;

import gds.application.weixin.constant.WeixinConstant;
import gds.application.weixin.listener.WeiXinAccessToken;
import gds.application.weixin.phoneWarningWeixin.dto.WeixinPublicEmployeeDTO;
import gds.application.weixin.phoneWarningWeixin.service.PhoneWarningWeixinService;
import gds.application.weixin.util.WeiXinUtils;
import gds.framework.utils.PropertyUtils;
import gds.framework.utils.SpringGDSUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;


/**
 * 微信公众号手机告警系统。微信公众号人员List集合
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public class WeiXinPublicEmployeeListener implements ServletContextListener{

	private Timer timer = null;
	
	 @Override
	public void contextDestroyed(ServletContextEvent arg0) {
		timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		// employeeList有效时间
		int accessTokenTime = 1000*60*60;

		// 创建一个新计时器，可以指定其相关的线程作为守护程序运行。
		timer = new Timer(true);
		// 设置任务计划，启动和间隔时间
		timer.schedule(new contractTask(), 0, accessTokenTime);

	}
	

	/**
	 * 微信公众号人员List集合，每间隔60分钟执行一次
	 * @author $Author: liuyunpeng $
	 * @version $Revision: 1.0 $
	 */
	class contractTask extends TimerTask {
		
		public void run() {
			// 获取employeeList
			excute();

		}

		@SuppressWarnings("unchecked")
		private void excute() {
			try {
				Logger log = Logger.getLogger("微信公众号关注人员集合！");
				
				// 如果手机微信端access_token为空，休眠10秒
				if( StringUtils.isEmpty( WeiXinAccessToken.getAccessToken(WeixinConstant.WEIXIN_TOKEN_ACCESSTOKEN)) ){
					Thread.sleep(10000);
				}
				
				// 获取tag用户openid列表URL
				//String userURL = appSettingFactory.getAppSetting("public_weixin_openidTagURL")+ WeiXinAccessToken.phoneWarn_access_token;
				// 获取用户openid列表
				String userURL = PropertyUtils.getProperty("public_weixin_openidURL")+ WeiXinAccessToken.getAccessToken(WeixinConstant.WEIXIN_TOKEN_ACCESSTOKEN);
				System.out.println("userURL="+userURL);
				
				// 封装tagJSON
				JSONObject  tagListObject = new JSONObject();
				tagListObject.put("tagid", PropertyUtils.getProperty("pubic_weixin_phoneWarnGroupid"));
				tagListObject.put("next_openid","");
				
				// 获取微信公众号粉丝openid列表
				String userList = WeiXinUtils.getWeiXinInfoByPost(userURL, tagListObject, "data");
				JSONObject jsonObject = JSONObject.fromObject(userList);
				JSONArray openIDs = JSONArray.fromObject(jsonObject.getString("openid"));
				
				// 初始化PhoneWarningService
				PhoneWarningWeixinService phoneWarningServiceImpl = SpringGDSUtils.getBean("phoneWarningWeixinServiceImpl");	
				
				if ( openIDs.size() > 100 ){
					for( int i = 0; i < openIDs.size(); ){
						// 获取最大openID数量 
						int maxOpenIds = i+100;
						if( maxOpenIds >   openIDs.size() ) {
							maxOpenIds = openIDs.size() ;
						}
						
						// 对openID 集合进行截取
						List<String> oppenIDList = openIDs.subList(i, maxOpenIds);
							
						// 获取微信公众号人员数据
						List<WeixinPublicEmployeeDTO> employeeList = this.getWeixinPublicEmployeeList(oppenIDList);
						// 添加微信公众号人员粉丝集合
						phoneWarningServiceImpl.addWeixinPublicEmployeeList(employeeList);
						
						i += 100;
					}
				}else{
					// 获取微信公众号人员数据
					List<WeixinPublicEmployeeDTO> employeeList = this.getWeixinPublicEmployeeList(openIDs);
					// 添加微信公众号人员粉丝集合
					phoneWarningServiceImpl.addWeixinPublicEmployeeList(employeeList);
				}

				log.info("获取微信公众号关注人员List集合成功！");
				
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

		/**
		 *  获取微信公众号人员数据
		 * @param user_list_object openid json集合
		 * @return
		 */
		@SuppressWarnings({ "static-access", "unchecked" })
		private List<WeixinPublicEmployeeDTO> getWeixinPublicEmployeeList(
				List<String> oppenIDList) throws Exception {
			
			// 对用批量获取用户基本信息数据进行封装
			JSONArray openIDArray = new JSONArray();
			for (int i = 0; i < oppenIDList.size(); i++) {
				JSONObject jsonUser = new JSONObject();
				jsonUser.put("openid", oppenIDList.get(i));
				jsonUser.put("lang", "zh_CN");
				openIDArray.add(jsonUser);
			}
			JSONObject user_list_object = new JSONObject();
			user_list_object.put("user_list", openIDArray);
			
			
			// 批量获取关注公众号人员详细信息URL
			String userInfoURL = PropertyUtils.getProperty("public_weixin_openidInfoURL")
					   + WeiXinAccessToken.getAccessToken(WeixinConstant.WEIXIN_TOKEN_ACCESSTOKEN);
			
			// 批量获取用户基本信息列表
			String userInfoList = WeiXinUtils.getWeiXinInfoByPost(userInfoURL, user_list_object, "user_info_list");
			
			// 对JSONArray数据进行封装
			JSONArray jsonEmpArray = new JSONArray().fromObject(userInfoList);
			// 关注公众号人员信息集合
			List<WeixinPublicEmployeeDTO> employeeList = (List<WeixinPublicEmployeeDTO>) jsonEmpArray.toCollection(jsonEmpArray,
							WeixinPublicEmployeeDTO.class);
			
			// 返回微信公众号人员数据集合
			return employeeList;
		}

	}

}
