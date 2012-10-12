package kr.co.blog.common;

public class PageUtil {
    public static final int ROW_PER_PAGE = 10; // 한 페이지에 보여줄 ROW 개수
    public static final int PAGE_LINK = 10; // 한 번에 링크할 페이지 개수
    
    /**
     * 목록 조회 화면의 페이지 링크 문자열 생성
     * @param iTotalRows    전체 ROW수 (제한검색조건 적용된것)
     * @param iCurPage  현재 페이지 NO
     * @param sJsFuncName   해당 페이지를 보여주게 될 JavaScript 함수 - 인자로 page 번호 문자열 받음
     * @param iRowPerPage   한 페이지에 보여줄 ROW 개수
     * @param iPageLink     한 번에 링크할 페이지 개수
     * @return 페이지 링크 만들어 주는 String
     */
    public static String getPageLink(int iTotalRows, int iCurPage, String sJsFuncName, int iRowPerPage, int iPageLink, String path) {
        StringBuilder sbPageLink = new StringBuilder();

        int iPages = (iTotalRows / iRowPerPage);

        if( (iTotalRows % iRowPerPage)  > 0 ) iPages ++;

        int iStartPage = ((iCurPage - 1) / iPageLink) * iPageLink + 1;  // ((10-1) / 10) * 10 + 1 = 1
        int iEndPage = iStartPage + iPageLink - 1;  // 1 + 10 - 1       // (1 + 10 - 1) = 10
        if( iEndPage > iPages ) iEndPage = iPages;

        if( iPages == 0 ) iCurPage = 0;
        
        if(iTotalRows > 0) {
            sbPageLink.append("<div class='pagination pagination-centered'>");
            sbPageLink.append("    <ul>");
        
            // 이전 페이지
            if(iCurPage > iPageLink) {    
                int prePage = 0;
                if(iPages == iEndPage) {
                    prePage = ((iCurPage / iPageLink) - 1) * iPageLink + 10;
                } else {
                    prePage = iEndPage - 10 ;
                }
                
                sbPageLink.append("<li><a href='#' onclick=\"" + sJsFuncName + "('" + prePage + "')\" data-bitly-type='bitly_hover_card'>«</a></li>");
            } else {
                sbPageLink.append("<li class='disabled'><a data-bitly-type='bitly_hover_card'>«</a></li>");
            }
    
            for(int i = iStartPage; i <= iEndPage ; i++){
                if(i == iCurPage) {
                    sbPageLink.append("<li class='active'><a href='#' onclick=\"" + sJsFuncName + "('" + i + "')\" data-bitly-type='bitly_hover_card'>"+ i +"</a></li>");
                } else {
                    sbPageLink.append("<li><a href='#' onclick=\"" + sJsFuncName + "('" + i + "')\" data-bitly-type='bitly_hover_card'>"+ i +"</a></li>");
                }
            }
    
            // 다음페이지
            if(iEndPage < iPages) {   
                int nextPage = ((iCurPage + iPageLink - 1) / iPageLink ) * iPageLink + 1; //(11+10-1)/10
                sbPageLink.append("<li><a href='#' onclick=\"" + sJsFuncName + "('" + nextPage + "')\" data-bitly-type='bitly_hover_card'>»</a></li>");
            } else {
                sbPageLink.append("<li class='disabled'><a data-bitly-type='bitly_hover_card'>»</a></li>");
            }
            
            sbPageLink.append("    </ul>");
            sbPageLink.append("</div>");
        }
        
        return sbPageLink.toString();
    }   
}
