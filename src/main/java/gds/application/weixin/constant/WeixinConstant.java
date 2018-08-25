/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.weixin.constant;

/**
 * 微信公共模块。微信常量类
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public final class WeixinConstant {

	 /**
     * 私有的构造方法
     */
    private WeixinConstant(){
        
    }
    
    /** 微信token类型：accessToken (全局唯一票据) */
    public static final String WEIXIN_TOKEN_ACCESSTOKEN  = "accessToken";
    
    /** 微信token类型：jsapi_ticket是H5应用调用企业微信JS接口的临时票据*/
    public static final String WEIXIN_TOKEN_JSTICKET = "jsapi_ticket";
    
    /** 微信token：AccessToken失效码值40001 */
    public static final String WEIXIN_TOKEN_ERRCODE = "40001";
    
    
}
