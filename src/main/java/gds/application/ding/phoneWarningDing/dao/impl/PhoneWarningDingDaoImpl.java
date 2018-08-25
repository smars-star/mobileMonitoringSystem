package gds.application.ding.phoneWarningDing.dao.impl;

import gds.application.ding.phoneWarningDing.dao.PhoneWarningDingDao;
import gds.application.ding.phoneWarningDing.dto.DingEmployeeDTO;
import gds.framework.base.dao.BaseDao;

import org.springframework.stereotype.Repository;

/**
 * 钉钉手机告警系统：钉钉手机告警Dao接口实现类
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
@Repository("phoneWarningDingDaoImpl")
public class PhoneWarningDingDaoImpl extends BaseDao implements PhoneWarningDingDao  {

	/**
	 * 添加钉钉人员信息DTO
	 * @param dingEmployeeDTO DingEmployeeDTO对象为钉钉人员DTO
	 */
	@Override
	public void addDingEmployeeDTO(DingEmployeeDTO dingEmployeeDTO) {
		this.sqlSessionTemplate.insert("phoneWarningDing.addDingEmployeeDTO", dingEmployeeDTO);
	}

}
