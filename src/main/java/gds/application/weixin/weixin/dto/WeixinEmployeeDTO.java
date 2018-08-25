/**
 * Copyright (c)  2017， 西安长城数字软件有限公司[www.e-u.cn]。
 *
 */
package gds.application.weixin.weixin.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;



/** 
 *  微信公共模块：微信部门员工DTO
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public class WeixinEmployeeDTO implements Serializable {

	/**
	 *  生成唯一序列号
	 */
	private static final long serialVersionUID = 985782072975019760L;

	/** 企业微信用户Id  */
	private  String  userid ="";
	
	/** 企业微信用户名称 */
	private  String  name = "";
	
	/** 	企业微信用户部门集合 */
	private  List<Integer>  department = null;
	
	/** 企业微信用户部门 */
	private  String   depName = "";
	
	/** 	企业微信用户职位 */
	private  String  position = "";
	
	/** 	企业微信用户手机号码 */
	private  String  mobile = "";
	
	/** 企业微信用户性别 */
	private  String  gender = "";
	
	/** 	企业微信用户Email */
	private  String  email = "";
	
	/** 	企业微信用户微信Id */
	private  String  weixinid = "";
	
	/** 企业微信用户头像 */
	private  String  avatar = "";
	
	/** 企业微信用户是否关注 */
	private  int  status = 1;
	
	/** 同级别排序 */
	private  List<Integer> order= null;
	
	/** 微信字段扩展 */
	private  Map<String,Map<String,?>>  extattr = null;
	
	/** 错误提示信息 */
    private  String  errmsg = "";
    
    /**  错误编码 */
	private  String  errcode = "";
	
	/** 是否是领导 */
	private  int  isleader = 0;
	
	 /** 英文名称 */
	private  String english_name = "";
	
	/**  电话 */
	private  String  telephone = "";
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getDepartment() {
		return department;
	}
	public void setDepartment(List<Integer> department) {
		this.department = department;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeixinid() {
		return weixinid;
	}
	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<Integer> getOrder() {
		return order;
	}
	public void setOrder(List<Integer> order) {
		this.order = order;
	}
	public Map<String, Map<String, ?>> getExtattr() {
		return extattr;
	}
	public void setExtattr(Map<String, Map<String, ?>> extattr) {
		this.extattr = extattr;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public int getIsleader() {
		return isleader;
	}
	public void setIsleader(int isleader) {
		this.isleader = isleader;
	}
	public String getEnglish_name() {
		return english_name;
	}
	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}
