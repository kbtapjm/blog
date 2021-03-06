package kr.co.blog.common.viewer;

import org.springframework.web.servlet.ModelAndView;

/**
 * ExcelViewer Interface
 * <pre>
 * pacakage : kr.co.blog.common.viewer 
 * file     : ExcelViewer.java , 2013. 10. 30.
 * author   : jmpark
 * email    : kbtapjm@gmail.com
 * 수정내용
 * ----------------------------------------------
 * 수정일      수정자  수정내용
 * ----------------------------------------------
 * 
 *</pre>
 */
public interface ExcelViewer {

    /* 최대 row */
    public final int MAX_ROWS = 66535; 

    /* 초기화 */
    public void init(Object object);
    
    public void push(String field, String head);
    
    public boolean hasField(Class<?> clazz, String field, int loop);
    
    /* 시트 이름 */
    public void setSheetName(String sheetName);
    
    /* 파일 이름 */
    public void setFileName(String fileName);
    
    /* 다운로드 */
    public ModelAndView download(Object object);
    
    /* 컨버터 */
    public void setExcelViewerConvert(ExcelViewerConvert excelViewerConvert);
}
