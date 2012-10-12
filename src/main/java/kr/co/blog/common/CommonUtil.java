package kr.co.blog.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtil {
    
    /**
     * 시간을 변환하여 문자열로 반환
     * @param time
     * @return
     */
    public static String getStrDateTime(Long time) {
        if(time == null || time == 0) return ""; 
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()); 
        Date date = new Date(time);
        
        String retTime = sdf.format(date);
        
        return retTime;
    }
    
    /**
     * 널 체크
     * @param parmValue
     * @param str
     * @return
     */
    public static String Nvl(String parmValue, String str) {
        String ret = null;
        if(parmValue == null || parmValue.equals("")) {
            ret = str;
        } else {
            ret = parmValue;
        }
        
        return ret;
    }

}
