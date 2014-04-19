package kr.co.blog.common.extract;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Excel관련 처리
 * <pre>
 * pacakage : kr.co.blog.common.viewer 
 * file     : XlsUtil.java , 2013. 10. 30.
 * author   : jmpark
 * email    : kbtapjm@gmail.com
 * 수정내용
 * ----------------------------------------------
 * 수정일      수정자  수정내용
 * ----------------------------------------------
 * 
 *</pre>
 */
public class XlsUtil extends OasisPotalExcelView implements OasisPotalViewer {

    private Queue<String> fieldQueue = new LinkedList<String>();
    private Queue<String> headQueue = new LinkedList<String>();
    private String[] fieldArray = null;
    private HSSFWorkbook workbook = new HSSFWorkbook();
    private String sheetName = "oasis";
    private String fileName = "oasis";
    
    /**
     * Excel Create
     */
    public void init(Object object) {
        HSSFSheet sheet = workbook.createSheet();
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        workbook.setSheetName(0, sheetName);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        
        
    }
    /**
     * sheet 이름 설정
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * 파일이름 설정
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 다운로드
     */
    public ModelAndView download(Object object) {
        try {
            init(object);
                    
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return new ModelAndView(this);
    }

    @Override
    protected void renderMergedOutputModel(
            Map<String, Object> model, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "inline; filename=" + java.net.URLEncoder.encode(fileName + "_"+ sdf.format(c.getTime()) + ".xls","UTF-8").replace('+',' ').replaceAll(" ","%20"));

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
    }

}
