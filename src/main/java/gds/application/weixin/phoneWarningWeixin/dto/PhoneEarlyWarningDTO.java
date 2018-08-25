/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.weixin.phoneWarningWeixin.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 *  微信公众号手机告警系统。手机预警DTO
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */

@Getter
@Setter
public class PhoneEarlyWarningDTO implements Serializable {
	
	/**
	 * 生成唯一序列化ID
	 */
	private static final long serialVersionUID = 234521411961723575L;

	/** 告警ID */
    private String warningID  = "";
    
    /** 告警类型，对应GJLXM码 */      
    private String warningType = "";
    
    /** 告警类型名称 */
    private String warningTypeName = "";
    
    /** 告警状态，对应GJZTM码 */
    private String warningStatus = "";
    
    /** 告警状态名称 */
    private String warningStatusName = "";
    
    /** 预警时间 */
    private String warningTime = "";
    
    /** 处理时间 */
    private String dealwithTime = "";
    
    /** 处理人 */
    private String dealwithuser = "";

    /** 传感器编号 */
    private String sensorNo = "";
    
    /** 预警地址 */
    private String warningAddress = "";
    
    /** 告警经度 */
    private String longitude = "";
    
    /** 告警纬度 */
    private String latitude = "";
    
    /** 备注 */
    private String remark = "";
    
    /** 标桩号 */
    private String stakeNo = "";
    
    /** 手机预警图片集合 */
	private List<String> warningImageList  = null;
	
	/** 手机预警图片平面点集合 */
	private HashMap<String, List<PhoneEarlyWarningRefDTO>> warningImageDetailHashMap = null;

    
}
