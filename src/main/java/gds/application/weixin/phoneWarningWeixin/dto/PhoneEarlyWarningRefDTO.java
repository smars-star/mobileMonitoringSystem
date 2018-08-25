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
 *  微信公众号手机告警系统。手机预警明细DTO
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
@Getter
@Setter
public class PhoneEarlyWarningRefDTO implements Serializable {

	/**
	 * 生成唯一序列号
	 */
	private static final long serialVersionUID = 907384449222175573L;
	
	/** 预警信息详细ID */
	private  String warningrefID = "";
	
	/** -- 图片地址*/
	private  String imagePath = "";
	
	/** 引发预警的对象类型 */
	private String objectType = "";
	
	/** 对象在图像上位置：point[]平面中的点 */
	private String objectPostion = null;
	
	/** 预警消息ID  */
	private String warningID = "";
	
	/** 画取图片的X轴开始位置 */
	private Integer imageX = 0;
	
	/** 画取图片的Y轴开始位置 */
	private Integer imageY = 0;
	
	/** 画取图片的width */
	private Integer imageWidth = 0;
	
	/** 画取图片的height */
	private Integer imageHeight = 0;
	

}
