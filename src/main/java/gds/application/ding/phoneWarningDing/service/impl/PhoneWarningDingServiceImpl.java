/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.ding.phoneWarningDing.service.impl;

import gds.application.ding.phoneWarningDing.dao.PhoneWarningDingDao;
import gds.application.ding.phoneWarningDing.dto.DingEmployeeDTO;
import gds.application.ding.phoneWarningDing.service.PhoneWarningDingService;
import gds.framework.exception.AppException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * 钉钉手机告警系统：钉钉手机告警Service接口实现类
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
@Service
public class PhoneWarningDingServiceImpl implements PhoneWarningDingService {
	
	/**
	 *  钉钉告警DAO
	 */
	@Autowired
	@Qualifier("phoneWarningDingDaoImpl")
	private PhoneWarningDingDao  phoneWarningDingDaoImpl;

	/**
	 * 添加钉钉人员集合
	 * @param employeeList List<DingEmployeeDTO>对象为钉钉人员信息集合
	 */
	@Override
	public void addDingEmployeeList(List<DingEmployeeDTO> employeeList) throws AppException {
		// 添加钉钉人员DTO信息
		for( DingEmployeeDTO dingEmployeeDTO : employeeList){
			this.phoneWarningDingDaoImpl.addDingEmployeeDTO(dingEmployeeDTO);
		}
		
	}
	
}
