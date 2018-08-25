/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.ding.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

	
/**
 * 钉钉公共模块：access_token监听器 没间隔 
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public class DingAccessTokenListener  implements ServletContextListener {
	
	/**  定时器  */
	private Timer timer = null;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		// 钉钉AccessToken有效时间
		int accessTokenTime = 1000*60*60;

		// 创建一个新计时器，可以指定其相关的线程作为守护程序运行。
		timer = new Timer(true);
		
		// 设置任务计划，启动和间隔时间
		timer.schedule( new DingAccessToken(), 0, accessTokenTime);
	}
	
	
}
