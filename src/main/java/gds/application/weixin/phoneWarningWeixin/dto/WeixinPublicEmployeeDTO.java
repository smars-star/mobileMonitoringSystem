/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.weixin.phoneWarningWeixin.dto;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;



/**
 *  微信公众号手机告警系统。微信公众号人员DTO
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public class WeixinPublicEmployeeDTO implements Serializable{

	/**
	 * 生成唯一序列号
	 */
	private static final long serialVersionUID = 4743353684167743200L;

	/** 是否关注：0(未关注时，拉取不到其余信息)、1(关注) */
	private  String subscribe = "";
	
	/** 用户的标识，对当前公众号唯一 */
	private  String openid = "";
	
	/** 用户的昵称 */
	private  String nickname = "";
	
	/** 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 */
	private  String sex = "";
	
	/** 用户所在城市 */
	private  String city = "";
	
	/** 用户所在国家 */
	private String country = "";
	
	/** 用户所在省份 */
	private String province = "";
	
	/** 用户的语言，简体中文为zh_CN */
	private  String language = "";
	
	/** 用户头像，最后一个数值代表正方形头像大小 */
	private String headimgurl = "";
	
	/** 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间 */
	private String subscribe_time = "";
	
	/** 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。 */
	private String unionid = "";
	
	/** 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注 */
	private String remark = "";
	
	/** 用户所在的分组ID（暂时兼容用户分组旧接口） */
	private  String groupid = "";
	
	/** 用户被打上的标签ID列表  */
	private List<Integer> tagid_list;
	/** 用户被打上的标签ID中间用 “ , ” 分隔*/
	@SuppressWarnings("unused")
	private String tagidList = "";
	
	/** 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他 */
	private String subscribe_scene = "";
	
	/** 二维码扫码场景（开发者自定义）  */
	private String qr_scene = "";
	
	/** 二维码扫码场景描述（开发者自定义） */
	private String qr_scene_str = "";
	
	/** 创建时间 插入本条记录时的时间，由数据库自动设置 */
	private String createTime;
	/** 创建人ID */
	private String createPersonID = "";
	/** 最后修改时间 */
	private String lastModifyTime;
	/** 修改人ID */
	private String lastModifyPersonID = "";

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() throws IOException {
		return URLDecoder.decode(nickname,"utf-8");
	}

	public void setNickname(String nickname) throws Exception {
		// 临时昵称
		String tempNickname = nickname;
		if ( StringUtils.isNotEmpty(tempNickname)){
			tempNickname = URLEncoder.encode(tempNickname,"utf-8");
		}
		this.nickname =tempNickname ;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(String subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getSubscribe_scene() {
		return subscribe_scene;
	}

	public void setSubscribe_scene(String subscribe_scene) {
		this.subscribe_scene = subscribe_scene;
	}

	public String getQr_scene() {
		return qr_scene;
	}

	public void setQr_scene(String qr_scene) {
		this.qr_scene = qr_scene;
	}

	public String getQr_scene_str() {
		return qr_scene_str;
	}

	public void setQr_scene_str(String qr_scene_str) {
		this.qr_scene_str = qr_scene_str;
	}

	public List<Integer> getTagid_list() {
		return tagid_list;
	}

	public void setTagid_list(List<Integer> tagid_list) {
		this.tagid_list = tagid_list;
	}
	
	public String getTagidList() {
		// 定义临时用户标签ID列表
		String tagidListStr = "[]";
		
		// 对微信公众号标签ID列表进行封装
		if ( !tagid_list.isEmpty() ){
			JSONArray pathsArray = JSONArray.fromObject(tagid_list);
			tagidListStr = pathsArray.toString();
		}
		
		// 返回用户标签ID列表
		return tagidListStr;
	}

	public void setTagidList(String tagidList) {
		this.tagidList = tagidList;
	}

	/** 获取创建时间 插入本条记录时的时间，由数据库自动设置 */
	public String getCreateTime() {
		return createTime;
	}

	/** 获取创建时间 插入本条记录时的时间，由数据库自动设置的长日期格式串 如 (2003-12-05 13:04:06) */
	public String getCreateTime_LDate() {
		try {
			return (createTime == null || "".equals(this.createTime)) ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.createTime));
		} catch (Exception e) {
			return this.getCreateTime_SDate();
		}
	}

	/** 获取创建时间 插入本条记录时的时间，由数据库自动设置的短日期格式串 如 (2003-12-05) */
	public String getCreateTime_SDate() {
		try {
			return (createTime == null || "".equals(this.createTime)) ? null
					: new SimpleDateFormat("yyyy-MM-dd")
							.format(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(this.createTime));
		} catch (Exception e) {
			return null;
		}
	}

	/** 设置创建时间 插入本条记录时的时间，由数据库自动设置 */
	public void setCreateTime(String theCreateTime) {
		if (theCreateTime != null) {
			try {
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(theCreateTime);
				this.createTime = theCreateTime;
			} catch (Exception e) {
				try {
					new SimpleDateFormat("yyyy-MM-dd").parse(theCreateTime);
					this.createTime = theCreateTime;
				} catch (Exception ex) {
					this.createTime = null;
				}
			}
		}
	}

	/** 获取创建人ID */
	public String getCreatePersonID() {
		return createPersonID;
	}

	/** 设置创建人ID */
	public void setCreatePersonID(String theCreatePersonID) {
		if (theCreatePersonID != null) {
			this.createPersonID = theCreatePersonID;
		}
	}

	/** 获取最后修改时间 */
	public String getLastModifyTime() {
		return lastModifyTime;
	}

	/** 获取最后修改时间的长日期格式串 如 (2003-12-05 13:04:06) */
	public String getLastModifyTime_LDate() {
		try {
			return (lastModifyTime == null || "".equals(this.lastModifyTime)) ? null
					: new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.lastModifyTime));
		} catch (Exception e) {
			return this.getLastModifyTime_SDate();
		}
	}

	/** 获取最后修改时间的短日期格式串 如 (2003-12-05) */
	public String getLastModifyTime_SDate() {
		try {
			return (lastModifyTime == null || "".equals(this.lastModifyTime)) ? null
					: new SimpleDateFormat("yyyy-MM-dd")
							.format(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(this.lastModifyTime));
		} catch (Exception e) {
			return null;
		}
	}

	/** 设置最后修改时间 */
	public void setLastModifyTime(String theLastModifyTime) {
		if (theLastModifyTime != null) {
			try {
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(theLastModifyTime);
				this.lastModifyTime = theLastModifyTime;
			} catch (Exception e) {
				try {
					new SimpleDateFormat("yyyy-MM-dd").parse(theLastModifyTime);
					this.lastModifyTime = theLastModifyTime;
				} catch (Exception ex) {
					this.lastModifyTime = null;
				}
			}
		}
	}

	/** 获取修改人ID */
	public String getLastModifyPersonID() {
		return lastModifyPersonID;
	}

	/** 设置修改人ID */
	public void setLastModifyPersonID(String theLastModifyPersonID) {
		if (theLastModifyPersonID != null) {
			this.lastModifyPersonID = theLastModifyPersonID;
		}
	}


	
}
