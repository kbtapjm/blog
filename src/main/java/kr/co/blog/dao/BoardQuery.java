package kr.co.blog.dao;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * board Query 
 * @author jmpark
 *
 */
public class BoardQuery {
    private static Logger log = Logger.getLogger(BoardQuery.class);

    /**
     * 게시글 목록쿼리
     * @param params
     * @return
     */
    public static String getBoardListQuery(Map<String, Object> params) {
        if(log.isDebugEnabled()) {
            log.debug("BoardQuery getBoardListQuery GET~~~!!!");
        }
        
        // 검색 파라미터
        String searchType = "";
        String searchWord = "";
        if(params.get("searchType") != null) {
            searchType = params.get("searchType").toString();
        }
        if(params.get("searchWord") != null) {
            searchWord = params.get("searchWord").toString();
        }
        
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * ");
        sb.append("FROM board ");
        
        log.debug("searchWord : " + searchWord);
        
        // 검색어가 있는 경우에만 검색 쿼리
        if(searchWord.length() > 0) {
            if(searchType.equals("subject_content")) {
                sb.append("WHERE subject LIKE '%" + searchWord + "%'");
                sb.append("OR content LIKE '%" + searchWord + "%'");
            } else if(searchType.equals("subject")) {
                sb.append("AND subject LIKE '%" + searchWord + "%'");
            } else if(searchType.equals("createUser")) {
                sb.append("AND createuser LIKE '%" + searchWord + "%'");
            } else if(searchType.equals("content")) {
                sb.append("AND content LIKE '%" + searchWord + "%'");
            }
        }
        
        return sb.toString();
    }


}
