/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.weixin.phoneWarningWeixin.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 *  微信公众号手机告警系统。传感器站点管理人员DTO
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */

@Getter
@Setter
public class SensorSiteMangeEmpDTO implements Serializable {

	/**
	 * 生成唯一序列化ID
	 */
	private static final long serialVersionUID = -4802200473969266016L;
	
    /** 传感器负责人ID */
    private String siteleader = "";
	
    /** 传感器编号 */
    private String sensorNo = "";

    /** 关注公众号的openID */
    private String wechatNo = "";
    
    /** 人员名称 */
    private String realName = "";
	

    
}
