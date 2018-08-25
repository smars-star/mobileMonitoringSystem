/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.ding.phoneWarningDing.dao;

import gds.application.ding.phoneWarningDing.dto.DingEmployeeDTO;


/**
 * 钉钉手机告警系统：钉钉手机告警Dao接口
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public interface PhoneWarningDingDao {

	/**
	 * 添加钉钉人员信息DTO
	 * @param dingEmployeeDTO DingEmployeeDTO对象为钉钉人员DTO
	 */
	void addDingEmployeeDTO(DingEmployeeDTO dingEmployeeDTO);

}
