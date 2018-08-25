/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.weixin.phoneWarningWeixin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gds.application.weixin.phoneWarningWeixin.dto.PhoneEarlyWarningDTO;
import gds.application.weixin.phoneWarningWeixin.dto.PhoneEarlyWarningRefDTO;
import gds.application.weixin.phoneWarningWeixin.dto.SensorSiteMangeEmpDTO;
import gds.application.weixin.phoneWarningWeixin.dto.WeixinPublicEmployeeDTO;
import gds.framework.base.dao.BaseDao;
import gds.framework.exception.AppException;


/**
 *  微信公众号手机告警系统。手机告警Dao接口实现类
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */

@Repository
public class PhoneWarningWeixinDaoImpl extends BaseDao {

	/**
	 *  查询告警DTO
	 * @param warningID String对象为告警ID
	 * @return PhoneWarningDTO 返回告警DTO
	 */
	public PhoneEarlyWarningDTO getPhoneWarningDTO(String warningID) throws AppException {
		return this.sqlSessionTemplate.selectOne("phoneWarningWexin.getPhoneWarningDTO", warningID);
	}

	/**
	 * 添加微信公众号人员粉丝DTO
	 * @param weixinPublicEmployeeDTO WeixinPublicEmployeeDTO对象为微信公众号人员粉丝DTO
	 */
	public void addWeixinPublicEmployeeDTO(WeixinPublicEmployeeDTO weixinPublicEmployeeDTO)throws AppException {
		this.sqlSessionTemplate.insert("phoneWarningWexin.addWeixinPublicEmployeeDTO", weixinPublicEmployeeDTO);
	}

	/**
	 *  查询微信公众号关注人员集合
	 * @param pubic_weixin_phoneWarnGroupid String对象为微信公众号告警组ID
	 * @return 返回List<WeixinPublicEmployeeDTO> 微信公众号关注人员集合
	 */
	public List<WeixinPublicEmployeeDTO> findWeixinPublicEmployeeList(String pubic_weixin_phoneWarnGroupid) throws AppException {
		return this.sqlSessionTemplate.selectList("phoneWarningWexin.findWeixinPublicEmployeeList", pubic_weixin_phoneWarnGroupid);
	}

	/**
	 * 查询当前传感器的站点负责人公众号openID
	 * @param sensorNo String对象为传感器编号
	 * @return  List<SensorSiteMangeEmpDTO>返回传感器的站点负责人List集合
	 */
	public List<SensorSiteMangeEmpDTO> findSiteManagerList(String sensorNo) {
		return this.sqlSessionTemplate.selectList("phoneWarningWexin.findSiteManagerList", sensorNo);
	}

	/**
	 *  查询传感器摄像头部门负责人的公众号openID
	 * @param sensorNo String对象为传感器编号
	 * @return List<SensorSiteMangeEmpDTO>返回传感器摄像头部门负责人List集合
	 */
	public List<SensorSiteMangeEmpDTO> findSiteDepMangerList(String sensorNo) {
		return this.sqlSessionTemplate.selectList("phoneWarningWexin.findSiteDepMangerList", sensorNo);
	}

	/**
	 * 查询手机预警明细集合信息
	 * @param warningID String对象为手机告警ID
	 * @return  List<PhoneEarlyWarningRefDTO>  返回手机预警明细List集合
	 */
	public List<PhoneEarlyWarningRefDTO> findPhoneEarlyWarningRefList(String warningID) {
		return this.sqlSessionTemplate.selectList("phoneWarningWexin.findPhoneEarlyWarningRefList", warningID);
	}

}
