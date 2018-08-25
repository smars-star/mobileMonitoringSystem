/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.weixin.phoneWarningWeixin.service.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import gds.application.weixin.phoneWarningWeixin.dao.impl.PhoneWarningWeixinDaoImpl;
import gds.application.weixin.phoneWarningWeixin.dto.PhoneEarlyWarningDTO;
import gds.application.weixin.phoneWarningWeixin.dto.PhoneEarlyWarningRefDTO;
import gds.application.weixin.phoneWarningWeixin.dto.SensorSiteMangeEmpDTO;
import gds.application.weixin.phoneWarningWeixin.dto.WeixinPublicEmployeeDTO;
import gds.application.weixin.phoneWarningWeixin.service.PhoneWarningWeixinService;
import gds.framework.exception.AppException;
import sun.misc.BASE64Encoder;

/**
 *  微信公众号手机告警系统。手机告警Service接口实现类
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
@Service
public class PhoneWarningWeixinServiceImpl implements PhoneWarningWeixinService {
	
	/**
	 *  告警DAO
	 */
	@Autowired
	@Qualifier("phoneWarningWeixinDaoImpl")
	private PhoneWarningWeixinDaoImpl  phoneWarningWeixinDaoImpl;

	/**
	 *  查询告警DTO
	 * @param warningID String对象为告警ID
	 * @return PhoneWarningDTO 返回告警DTO
	 */
	@Override
	public PhoneEarlyWarningDTO getPhoneWarningDTO(String warningID)  throws AppException {
		// 获取告警信息DTO
		PhoneEarlyWarningDTO phoneEarlyWarningDTO = this.phoneWarningWeixinDaoImpl.getPhoneWarningDTO(warningID);
		
		// 获取手机预警明细DTO
		List<PhoneEarlyWarningRefDTO> phoneEarlyWarningRefList  = this.phoneWarningWeixinDaoImpl.findPhoneEarlyWarningRefList(warningID);
		
		// 封装图片集合
		List<String> warningImageList = new ArrayList<String>();
		
		// 封装图片详情【key：图片途径（imagepath），value：预警详细信息DTO】
		HashMap<String, List<PhoneEarlyWarningRefDTO>> warningImageDetailHashMap = new HashMap<String, List<PhoneEarlyWarningRefDTO>>();
		List<PhoneEarlyWarningRefDTO> tempWarningDetailList = new ArrayList<PhoneEarlyWarningRefDTO>();

		// 循环预警信息，将同一个图片的告警点封装到一起 
		for (PhoneEarlyWarningRefDTO phoneEarlyWarningRefDTO : phoneEarlyWarningRefList) {
			// 设置图片坐标轴
			setImageAxis(phoneEarlyWarningRefDTO);
			
			// 图片路径做为key
			String key = phoneEarlyWarningRefDTO.getImagePath(); 
			
			// 对告警点图片进行处理
			if (warningImageDetailHashMap.containsKey(key)) {
				tempWarningDetailList = warningImageDetailHashMap.get(key);
			} else {
				warningImageList.add(key);
				tempWarningDetailList = new ArrayList<PhoneEarlyWarningRefDTO>();
			}
			
			// 设置图片需要画图的平面点
			tempWarningDetailList.add(phoneEarlyWarningRefDTO);
			warningImageDetailHashMap.put(key, tempWarningDetailList);
		}
		
	/*	// 处理图片
		for( Entry<String, List<PhoneEarlyWarningRefDTO>>  entry  :  warningImageDetailHashMap.entrySet() ) {
			// 获取64bit格式的字符串图片
			String base64Image =  setWaningImageList("D:\\gitlocal\\mobileMonitoringSystem\\src\\main\\webapp\\"+entry.getKey() ,entry.getValue()) ;
			warningImageList.add(base64Image);
		}*/
		
		// 设置预警明细信息
		phoneEarlyWarningDTO.setWarningImageList(warningImageList);
		phoneEarlyWarningDTO.setWarningImageDetailHashMap(warningImageDetailHashMap);
		
		// 返回预警DTO
		return phoneEarlyWarningDTO;
	}

	/**
	 *  对图片进行渲染
	 * @param imagePath String对象为图片路径
	 * @param phoneEarlyWarningRefList  List<PhoneEarlyWarningRefDTO>对象为图片矩形平面点List集合
	 * @throws AppException  抛出异常
	 */
	@SuppressWarnings("restriction")
	private String  setWaningImageList(String imagePath, List<PhoneEarlyWarningRefDTO> phoneEarlyWarningRefList) throws AppException {
		// 64bit格式的图片字符串		
		String base64Image = "";
		try {
			
			// 获取图片
			InputStream in = new FileInputStream(imagePath);
			
			// 读取图片
			BufferedImage bufferImage = ImageIO.read( in);
			
			// 在图片上画图
			Graphics g = bufferImage.getGraphics();
			
			//画笔颜色
			g.setColor(Color.RED);
			
			// 画矩形
			if( !phoneEarlyWarningRefList.isEmpty() ) {
			    for( PhoneEarlyWarningRefDTO phoneEarlyWarningRefDTO : phoneEarlyWarningRefList    ) {
			    	//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
			    	g.drawRect(phoneEarlyWarningRefDTO.getImageX(), phoneEarlyWarningRefDTO.getImageY(), 
			    			phoneEarlyWarningRefDTO.getImageWidth(), phoneEarlyWarningRefDTO.getImageHeight());
			    }
			}
			
			// 结束
			g.dispose();
			
			//把图片转换成 byte数组
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferImage, "jpeg", baos);
			byte[] bytes = baos.toByteArray();
			
			//再把数组转换成BASE64字符串， 在加上图片的格式
			BASE64Encoder  base64Encoder = new BASE64Encoder();
			String  base64Str = base64Encoder.encode(bytes);
			
			// 生成64bit格式的图片字符串
			base64Image = "data:image/"+"jpeg"+";base64,"+base64Str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 返回
		return base64Image;
	}

	/**
	 *  封装图片坐标轴
	 * @param phoneEarlyWarningRefDTO  PhoneEarlyWarningRefDTO对象为手机预警明细DTO
	 */
	private void setImageAxis(PhoneEarlyWarningRefDTO phoneEarlyWarningRefDTO) {
		// 判断图片是否有坐标轴值
		if( StringUtils.isNotEmpty( phoneEarlyWarningRefDTO.getObjectPostion() ) ) {
			// 图片的坐标
			String  tempPicAxis = ""; 
			// 获取图片平面点
			String  objectPostion = phoneEarlyWarningRefDTO.getObjectPostion();
			
			// 获取XY轴坐标
			String XYAxis = objectPostion.split( "\\),\\(" )[0];
			// 获取X轴坐标
			tempPicAxis = XYAxis.substring(1, XYAxis.length() );
			// 对X 轴坐标值进行处理
			phoneEarlyWarningRefDTO.setImageX( Integer.parseInt(tempPicAxis.split(",")[0]) );
			// 对Y 轴坐标值进行处理
			phoneEarlyWarningRefDTO.setImageY( Integer.parseInt(tempPicAxis.split(",")[1]) );
			
			//获取对角点(width、height)
			String diagonalPoint = objectPostion.split( "\\),\\(" )[1];
			// 获取对角点坐标
			tempPicAxis = diagonalPoint.substring(0, diagonalPoint.length()-1);
			// 对width坐标值进行处理
			phoneEarlyWarningRefDTO.setImageWidth(phoneEarlyWarningRefDTO.getImageX() - Integer.parseInt(tempPicAxis.split(",")[0]) );
			// 对height坐标值进行处理
			phoneEarlyWarningRefDTO.setImageHeight(phoneEarlyWarningRefDTO.getImageY() - Integer.parseInt(tempPicAxis.split(",")[1]) );
		}
		
	}


	/**
	 * 添加微信公众号人员粉丝集合
	 * @param employeeList List<WeixinPublicEmployeeDTO>对象为微信公众号人员粉丝信息集合
	 */
	@Override
	public void addWeixinPublicEmployeeList(List<WeixinPublicEmployeeDTO> employeeList) throws AppException {
		// 添加微信公众号人员DTO
		for( WeixinPublicEmployeeDTO weixinPublicEmployeeDTO : employeeList ){
			this.phoneWarningWeixinDaoImpl.addWeixinPublicEmployeeDTO(weixinPublicEmployeeDTO);
		}
		
	}

	/**
	 *  查询微信公众号关注人员集合
	 * @param pubic_weixin_phoneWarnGroupid String对象为微信公众号告警组ID
	 * @return 返回List<WeixinPublicEmployeeDTO> 微信公众号关注人员集合
	 */
	@Override
	public List<WeixinPublicEmployeeDTO> findWeixinPublicEmployeeList(String pubic_weixin_phoneWarnGroupid) throws AppException {
		return this.phoneWarningWeixinDaoImpl.findWeixinPublicEmployeeList(pubic_weixin_phoneWarnGroupid);
	}

	/**
	 * 查询当前传感器的预警人员集合信息
	 * @param sensorNo Stirng对象为传感器编号
	 * @return List<SensorSiteMangeEmpDTO>  返回当前传感器站点负责人List集合
	 */
	@Override
	public List<SensorSiteMangeEmpDTO> findEarlyWarningOpenidList(String sensorNo) throws AppException {
		// 获取当前传感器的预警人员集合信息
		List<SensorSiteMangeEmpDTO>  sensorSiteMangeEmpList = new ArrayList<>();
		
		// 获取传感器的站点负责人的公众号openID
		List<SensorSiteMangeEmpDTO> siteManagerList = this.phoneWarningWeixinDaoImpl.findSiteManagerList(sensorNo);
		sensorSiteMangeEmpList.addAll(siteManagerList);
		
	    // 获取传感器摄像头部门负责人的公众号openID
		List<SensorSiteMangeEmpDTO>  siteDepMangerList = this.phoneWarningWeixinDaoImpl.findSiteDepMangerList(sensorNo);
		sensorSiteMangeEmpList.addAll(siteDepMangerList);
		
		// 返回传感器的预警人员集合信息
		return sensorSiteMangeEmpList;
	}


	/**
	 * 查询当前传感器的站点负责人公众号openID
	 * @param sensorNo String对象为传感器编号
	 * @return  List<SensorSiteMangeEmpDTO>返回传感器的站点负责人List集合
	 */
	@Override
	public List<SensorSiteMangeEmpDTO> findSiteManagerList(String sensorNo) throws AppException {
		return this.phoneWarningWeixinDaoImpl.findSiteManagerList(sensorNo);
	}
	
}
