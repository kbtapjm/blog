package kr.co.blog.common.viewer;

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
public class XlsUtil extends ExcelView implements ExcelViewer {

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
        
        fieldArray = new String[ fieldQueue.size() ];
        int count = 0;
        
        while ( fieldQueue.peek() != null) {
            fieldArray[count] = fieldQueue.poll();
            count++;
        }
        
        font.setFontName("Arial");
        font.setColor(HSSFFont.COLOR_NORMAL);
        
        // header
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        
        count = 0;
        
        while ( headQueue.peek() != null) {
            String lang = headQueue.poll();
            cell = row.createCell(count);
            cell.setCellStyle( style );
            cell.setCellValue( lang );
            
            sheet.setColumnWidth(count, (256 * 20) );
            
            count++;
        }
        
        try {
            if (object instanceof List) {
                List<?> listObject = (List<?>) object;
                
                if (listObject != null && listObject.size() > 0 ) {
                    int statRowNum = 1;
                    
                    for (Object dataObject : listObject ) {
                        row = sheet.createRow( statRowNum );
                        statRowNum++;
                        int startCellNum = 0;
                        
                        if (dataObject instanceof Map) {
                            Map<?, ?> map = (Map<?, ?>) dataObject;
                            
                            for (int i = 0; i < fieldArray.length; i++) {
                                Object obj = map.get(fieldArray[i].toLowerCase());
                                
                                if (obj != null) {
                                    String s = obj.toString() == null ? "" : obj.toString();
                                    
                                    cell = row.createCell( startCellNum++ );
                                    
                                    /*
                                     * has Convert Class
                                     */
                                    if (hasConvert()) {
                                        
                                        try {
                                            s = (String) excelViewerConvert.convert(fieldArray[i], s);
                                        } catch(Exception e) {
                                            s = "";
                                        }
                                    }
                                    
                                    cell.setCellValue( s );
                                }
                            }
                            
                        } else {
                            Class<? extends Object> clazz = dataObject.getClass();
                            
                            for (int i = 0; i < fieldArray.length; i++) {
                            
                                if (hasField(clazz, fieldArray[i], i)) {
                                    
                                    String fieldName = fieldArray[i];
                                    String getMethodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                                    
                                    Method getMethod = clazz.getMethod(getMethodName);
                                    Object obj = getMethod.invoke( dataObject );
                                    
                                    cell = row.createCell( startCellNum++ );
                                    boolean converted = false;
                                    
                                    try {
                                        /*
                                         * has Convert Class
                                         */
                                        if (hasConvert()) {
                                            Object beforeConvertObj = obj;
                                            obj = excelViewerConvert.convert(fieldName, obj);
                                            
                                            if (beforeConvertObj.equals(obj)) {
                                                converted = false;
                                            } else  {
                                                obj = obj == null ? "" : obj;
                                                cell.setCellValue( (String) obj );
                                                converted = true;
                                            }
                                        }
                                        
                                        if (!converted) {
                                            obj = obj == null ? "" : obj;
                                            
                                            if (getMethod.getReturnType().equals( int.class ) || getMethod.getReturnType().equals(Integer.class)) {
                                                    cell.setCellValue(Integer.parseInt(obj.toString()));
                                            } else if (getMethod.getReturnType().equals(Double.class) || getMethod.getReturnType().equals(double.class) 
                                                ||  getMethod.getReturnType().equals(Float.class ) || getMethod.getReturnType().equals(float.class)) {
                                                    cell.setCellValue(Double.parseDouble(obj.toString()));
                                            } else if (getMethod.getReturnType().equals(Boolean.class ) || getMethod.getReturnType().equals( boolean.class)) {
                                                    cell.setCellValue(Boolean.parseBoolean(obj.toString()));
                                            } else if (getMethod.getReturnType().equals(Date.class)) {
                                                    cell.setCellValue(Date.parse( obj.toString()));
                                            } else {
                                                    cell.setCellValue(obj.toString());
                                            }
                                        }
                                        
                                    } catch(Exception e) {
                                        cell.setCellValue("");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  셀에 값 추가
     */
    public void push(String field, String head) {
        // field에 추가
        fieldQueue.add( field );
        
        // header에 추가
        headQueue.add( head );
    }

    /**
     * 필드값이 존재하는지 체크
     */
    public boolean hasField(Class<?> clazz, String field, int loop) {
        if ( loop > 0 ) {
            return true;
        }
        
        try {
            clazz.getDeclaredField(field);
        } catch(Exception e) {
            return false;
        }
        
        return true;
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

    /**
     * conter set
     */
    public void setExcelViewerConvert(ExcelViewerConvert excelViewerConvert) {
        this.excelViewerConvert = excelViewerConvert;
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
