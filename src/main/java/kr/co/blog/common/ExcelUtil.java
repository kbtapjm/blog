package kr.co.blog.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import kr.co.blog.domain.Board;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 엑셀설정
 * @author kbtapjm
 *
 */
public class ExcelUtil {
    /**
     * 제목 스타일
     * @param workbook
     * @return
     */
    public HSSFCellStyle getTitleStyle(HSSFWorkbook workbook) {
        HSSFCellStyle titlestyle = workbook.createCellStyle();
        titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        // font 설정
        HSSFFont font = workbook.createFont();  //폰트 객체 생성
        font.setFontHeightInPoints((short)14);  //폰트 크기
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //폰트 굵게
        titlestyle.setFont(font);
        
        return titlestyle;
    }
   
    /**
     * 헤더 스타일
     * @param workbook
     * @return
     */
    public HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook) {
        HSSFCellStyle headerstyle = workbook.createCellStyle();
        headerstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        headerstyle.setFillForegroundColor(HSSFColor.AQUA.index); 
        headerstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);     
        
        // 라인
        headerstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);     
        headerstyle.setBottomBorderColor(HSSFColor.BLACK.index);    
        headerstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerstyle.setLeftBorderColor(HSSFColor.BLACK.index);
        headerstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerstyle.setRightBorderColor(HSSFColor.BLACK.index);
        headerstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerstyle.setTopBorderColor(HSSFColor.BLACK.index);
        
        // font 설정
        HSSFFont font = workbook.createFont();  //폰트 객체 생성
        font.setFontHeightInPoints((short)10);  //폰트 크기
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //폰트 굵게
        headerstyle.setFont(font);
        
        return headerstyle;
    }
    
    /**
     * 값 스타일
     * @param workbook
     * @return
     */
    public HSSFCellStyle getValueStyle(HSSFWorkbook workbook) {
        HSSFCellStyle valueStyle = workbook.createCellStyle();
        valueStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);      
        valueStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);    
        valueStyle.setBottomBorderColor(HSSFColor.BLACK.index);   
        valueStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        valueStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        valueStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        valueStyle.setRightBorderColor(HSSFColor.BLACK.index);
        valueStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        valueStyle.setTopBorderColor(HSSFColor.BLACK.index);
        
        return valueStyle;
    }
    
    /**
     * cell 값 얻기(2007버젼 이하)
     * @param cell
     * @return
     */
    public static String getCellValue(HSSFCell cell) {
        
        String value = "";
        switch(cell.getCellType()) {
            case HSSFCell.CELL_TYPE_FORMULA : 
                value = cell.getCellFormula();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC : 
                value = String.valueOf((int)cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_STRING : 
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BLANK :
                value = null;
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN : 
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR : 
                value = String.valueOf(cell.getErrorCellValue());
                break;
        }
        
        return value;
    }
    
    /**
     * cell 값 얻기(2007버젼)
     * @param cell
     * @return
     */
    public static String getCellValue(XSSFCell cell) {
        
        String value = "";
        switch(cell.getCellType()) {
            case HSSFCell.CELL_TYPE_FORMULA : 
                value = cell.getCellFormula();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC : 
                value = String.valueOf((int)cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_STRING : 
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BLANK :
                value = null;
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN : 
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR : 
                value = String.valueOf(cell.getErrorCellValue());
                break;
        }
        
        return value;
    }
    
    /**
     * 2007버젼 이하 엑셀 파일 읽기
     * @param uploadedFile
     * @return
     */
    public static List<Board> boardExcelDataRead(File uploadedFile) {
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        
        List<Board> list = new ArrayList<Board>();
        Board board = null;
        
        try {
            
            // 엑셀파일 로드
            workbook = new HSSFWorkbook(new FileInputStream(uploadedFile));
            
            // 시트 읽기
            sheet = workbook.getSheetAt(0);
            
            // 실제 데이터가 시작되는 Row지정
            int startRow = 1;
            // 실제 데이터가 끝 Row지정
            int endRow   = sheet.getLastRowNum();
            
            for (int i = startRow; i <= endRow ; i++) {
                board = new Board();
                row  = sheet.getRow(i);
                
                if(row == null) continue; 
                 
                cell = row.getCell(0);
                board.setBoardId(ExcelUtil.getCellValue(cell));
                
                cell = row.getCell(1);
                board.setSubject(ExcelUtil.getCellValue(cell));
                
                cell = row.getCell(2);
                board.setCreateUser(ExcelUtil.getCellValue(cell));
                
                cell = row.getCell(3);
                board.setCreateDt(ExcelUtil.getCellValue(cell));
                
                cell = row.getCell(4);
                board.setCount(Integer.parseInt(ExcelUtil.getCellValue(cell)));
                
                list.add(board);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    /**
     * 2007버젼 엑셀파일 읽기
     * @param uploadedFile
     * @return
     */
    public static List<Board> boardExcelData2007Read(File uploadedFile) {
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        
        List<Board> list = new ArrayList<Board>();
        Board board = null;
        
        try {
            
            // 엑셀파일 로드
            workbook = new XSSFWorkbook(new FileInputStream(uploadedFile));
            // 엑셀 시트 확인
            sheet = workbook.getSheetAt(0);
            
            // 실제 데이터가 시작되는 Row지정
            int startRow = 1;
            // 실제 데이터가 끝 Row지정
            int endRow = sheet.getLastRowNum();
            
            for (int i = startRow; i <= endRow ; i++) {
                board = new Board();
                row  = sheet.getRow(i);
                
                if(row == null) continue; 
                     
                cell = row.getCell(0);
                board.setBoardId(ExcelUtil.getCellValue(cell));
                
                cell = row.getCell(1);
                board.setSubject(ExcelUtil.getCellValue(cell));
                
                cell = row.getCell(2);
                board.setCreateUser(ExcelUtil.getCellValue(cell));
                
                cell = row.getCell(3);
                board.setCreateDt(ExcelUtil.getCellValue(cell));
                
                cell = row.getCell(4);
                board.setCount(Integer.parseInt(ExcelUtil.getCellValue(cell)));
                
                list.add(board);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
}
