/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.ding.phoneWarningDing.service;

import gds.application.ding.phoneWarningDing.dto.DingEmployeeDTO;
import gds.framework.exception.AppException;

import java.util.List;


/**
 * 钉钉手机告警系统：钉钉手机告警Service 接口
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public interface PhoneWarningDingService  {


	/**
	 * 添加钉钉人员集合
	 * @param employeeList List<DingEmployeeDTO>对象为钉钉人员信息集合
	 */
	void addDingEmployeeList(List<DingEmployeeDTO> employeeList) throws AppException;



}
