/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.weixin.phoneWarningWeixin.constant;

/**
 * 微信公众号手机告警系统。手机告警常量类
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public final class PhoneWarningConstant {

	 /**
     * 私有的构造方法
     */
    private PhoneWarningConstant(){
        
    }
    
    /** 手机警告类型：alarm(告警) */
    public static final String PHONE_WARNING_ALARM  = "alarm";
    
    /** 手机警告类型：early(预警) */
    public static final String PHONE_WARNING_EARLY  = "early";
    
    
    /** 微信webservice返回字段 stateCode */
    public final static String STATE_CODE = "stateCode";
    /** 微信webservice返回字段 stateCode: success(成功)*/
    public final static String STATE_CODE_success = "success";
    /** 微信webservice返回字段 stateCode ：faile(失败)*/
    public final static String STATE_CODE_faile = "faile";

    /** 微信webservice返回字段 message */
    public final static String MESSAGE = "message";
    
}
