package kr.co.blog.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
    
    /**
     * 시간을 변환하여 문자열로 반환
     * @param time
     * @return
     */
    public static String getStrDateTime(Long time) {
        if(time == null || time == 0) return ""; 
        
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss"); 
        Date date = new Date(time);
        
        String retTime = sdf.format(date);
        
        return retTime;
    }

}
