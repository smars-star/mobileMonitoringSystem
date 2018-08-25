/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.ding.phoneWarningDing.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;


/**
 * 钉钉手机告警系统：钉钉人员DTO
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public class DingEmployeeDTO implements Serializable{

	/**
	 * 生成唯一序列号
	 */
	private static final long serialVersionUID = 3711409487266014980L;

	/** 员工唯一标识ID  */
	private String  userid = "";
	
	/** 在当前isv全局范围内唯一标识一个用户的身份，用户无法修改  */
	private String unionid = "";
	
	/** 手机号（ISV不可见）*/
	private String mobile ="";
	
	/** 分机号（ISV不可见） */
	private String tel = "";
	
	/** 办公地点（ISV不可见）*/
	private String workPlace = "";
	
	/** 备注（ISV不可见） */
	private String remark = "";
	
	/** 是否是企业的管理员：true表示是 、false表示不是 */
	private String isAdmin = "";
	
	/** 	是否为企业的老板（不能通过接口进行设置，可以通过钉钉管理后台进行设置），true表示是 false表示不是 */
	private String isBoss = "";
	
	/** 	是否隐藏号码， true表示是 、false表示不是 */
	private String isHide = "";
	
	/**
	 * 是否是部门的主管， true表示是， false表示不是
	 */
	private String isLeader  = "" ;

	/** 成员名称 */
	private String name  = "";
	
	/** 表示该用户是否激活了钉钉 */
	private String active = "";
	
	/** 成员所属部门id列表 */
    //private List<Integer>  department = null;
	private String department = null;
    /** 成员所属部门id列表List */
    //private String departmentList = "";
    
    /** 职位信息 */
    private String position = "";
    
    /** 员工的邮箱 */
    private String email = "";
    
    /** 员工的企业邮箱，如果员工的企业邮箱没有开通，返回信息中不包含 */
    private String orgEmail = "";
    
    /**  头像url  */
    private String  avatar = "";
    
    /** 员工工号 */
    private String  jobnumber = "";
    
    /**  入职时间 */
    private String hiredDate = "";
    
	/** 创建时间 插入本条记录时的时间，由数据库自动设置 */
	private String createTime;
	/** 创建人ID */
	private String createPersonID = "";
	/** 最后修改时间 */
	private String lastModifyTime;
	/** 修改人ID */
	private String lastModifyPersonID = "";
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsBoss() {
		return isBoss;
	}
	public void setIsBoss(String isBoss) {
		this.isBoss = isBoss;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrgEmail() {
		return orgEmail;
	}
	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getJobnumber() {
		return jobnumber;
	}
	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}
	public String getHiredDate() {
		return hiredDate;
	}
	public void setHiredDate(String hiredDate) {
		this.hiredDate = hiredDate;
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
	/*public List<Integer> getDepartment() {
		return department;
	}
	public void setDepartment(List<Integer> department) {
		this.department = department;
	}*/
	public String getIsLeader() {
		return isLeader;
	}
	public void setIsLeader(String isLeader) {
		this.isLeader = isLeader;
	}
	public String getIsHide() {
		return isHide;
	}
	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}
/*	public String getDepartmentList() {
		// 部门集合List字符串 
		String tempDepartmentList = "";
		if( !this.department.isEmpty() ){
			tempDepartmentList = this.department.toString();
		}
		
		// 返回tempDepartmentList
		return tempDepartmentList;
	}
	public void setDepartmentList(String departmentList) {
		this.departmentList = departmentList;
	}*/
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
	

	
}
