/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.ding.listener;

import gds.application.ding.phoneWarningDing.dto.DingEmployeeDTO;
import gds.application.ding.phoneWarningDing.service.PhoneWarningDingService;
import gds.framework.utils.PropertyUtils;
import gds.framework.utils.SpringGDSUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserListRequest;
import com.dingtalk.api.response.OapiUserListResponse;



/**
 * 钉钉公共模块：钉钉通讯录同步人员监听器
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public class DingEmployeeListener implements ServletContextListener{

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
	 * 钉钉人员List集合，每间隔60分钟执行一次
	 * @author $Author: liuyunpeng $
	 * @version $Revision: 1.0 $
	 */
	class contractTask extends TimerTask {
		
		public void run() {
			// 获取employeeList
			excute();

		}

		@SuppressWarnings({ "static-access", "unchecked" })
		private void excute() {
			try {
				Logger log = Logger.getLogger("微信公众号关注人员集合！");
				
				// 初始化AppSettingFactory
				String dingUserDetailURL = PropertyUtils.getProperty("dingUserDetailURL");
				
				// 如果手机微信端access_token为空，休眠10秒
				if( StringUtils.isEmpty( DingAccessToken.ding_access_token ) ){
					Thread.sleep(10000);
				}
				
				// 获取部门用户（详情）
				DingTalkClient client = new DefaultDingTalkClient(dingUserDetailURL);
				OapiUserListRequest request = new OapiUserListRequest();
				request.setHttpMethod("GET");
				request.setDepartmentId(1L);
				
				/*
				// 是否分页，且每页显示的数量
				request.setOffset(0L);
				request.setSize(10L);
				*/
				OapiUserListResponse response = client.execute(request, DingAccessToken.ding_access_token);
				
				System.out.println("获取人员:"+response.getBody());
				
				// 对JSONArray数据进行封装
				JSONArray jsonEmpArray = new JSONArray().fromObject(response.getUserlist());
				// 关注公众号人员信息集合
				List<DingEmployeeDTO> employeeList = (List<DingEmployeeDTO>) jsonEmpArray.toCollection(jsonEmpArray,
						DingEmployeeDTO.class);
				
				// 初始化PhoneWarningService
				PhoneWarningDingService phoneWarningDingServiceImpl = SpringGDSUtils.getBean("phoneWarningDingServiceImpl");	
                // 添加钉钉人员数据集合
				phoneWarningDingServiceImpl.addDingEmployeeList(employeeList);

				log.info("获钉钉人员List集合成功！");
				
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
