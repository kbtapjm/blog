package kr.co.blog.dao;

import java.util.List;
import java.util.Map;

/**
 * board Query 
 * @author jmpark
 *
 */
public class BoardQuery {

    @SuppressWarnings("unchecked")
    public static String getBoardListQuery(Map<String, Object> params) {
        
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM board");
        
        return sql.toString();
    }


}
