/**
 *liuyunpneg
 * 
 * 
 * 
 */
package gds.application.weixin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 微信公共模块：emoji特殊处理工具类
 * @author $Author: liuyunpeng $
 * @version $Revision: 1.0 $
 */
public class EmojiStringUtils {
	
	
	/**
	 * 判断是否存在特殊字符串
	 * @param content 字符串内容
	 * @return  返回是否存在特殊字符串
	 */
    public static boolean hasEmoji(String content){
        Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
        Matcher matcher = pattern.matcher(content);
        if(matcher .find()){
            return true;    
        }
            return false;
    }
    
    /**
     * 替换字符串中的emoji字符
     * @param str 字符串内容
     * @return 返回字符串
     */
    public static String replaceEmoji(String str){
    	if(!hasEmoji(str)){
    		return str;
    	}else{
    		str=str.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", " ");
    		return str;
    	}
    }


}
