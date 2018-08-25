/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.weixin.phoneWarningWeixin.service;

import java.util.List;

import gds.application.weixin.phoneWarningWeixin.dto.PhoneEarlyWarningDTO;
import gds.application.weixin.phoneWarningWeixin.dto.SensorSiteMangeEmpDTO;
import gds.application.weixin.phoneWarningWeixin.dto.WeixinPublicEmployeeDTO;
import gds.framework.exception.AppException;

/**
 *  微信公众号手机告警系统。手机告警Service接口
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public interface PhoneWarningWeixinService  {

	/**
	 *  查询告警DTO
	 * @param warningID String对象为告警ID
	 * @return PhoneWarningDTO 返回告警DTO
     * @throws AppException  抛出异常
	 */
	PhoneEarlyWarningDTO getPhoneWarningDTO(String warningID) throws AppException;

	/**
	 * 添加微信公众号人员粉丝集合
	 * @param employeeList List<WeixinPublicEmployeeDTO>对象为微信公众号人员粉丝信息集合
	 */
	void addWeixinPublicEmployeeList(List<WeixinPublicEmployeeDTO> employeeList) throws AppException;

	/**
	 *  查询微信公众号关注人员集合
	 * @param pubic_weixin_phoneWarnGroupid String对象为微信公众号告警组ID
	 * @return 返回List<WeixinPublicEmployeeDTO> 微信公众号关注人员集合
	 */
	List<WeixinPublicEmployeeDTO> findWeixinPublicEmployeeList(
			String pubic_weixin_phoneWarnGroupid) throws AppException;

	/**
	 * 查询当前传感器的预警人员集合信息
	 * @param sensorNo Stirng对象为传感器编号
	 * @return List<SensorSiteMangeEmpDTO>  返回当前传感器站点负责人List集合
	 */
	List<SensorSiteMangeEmpDTO> findEarlyWarningOpenidList(String sensorNo) throws AppException;

	/**
	 * 查询当前传感器的站点负责人公众号openID
	 * @param sensorNo String对象为传感器编号
	 * @return  List<SensorSiteMangeEmpDTO>返回传感器的站点负责人List集合
	 */
	List<SensorSiteMangeEmpDTO> findSiteManagerList(String sensorNo) throws AppException;

}
