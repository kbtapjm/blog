package kr.co.blog.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelDownload extends AbstractExcelView {
    
    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
         // controller에서 ModelAndView 객체에 addObject하여 보내온 데이터를 꺼낸다.
         List<Map<String, String>> excelList = (List<Map<String, String>>)model.get("resultList ");
         
         
         System.out.println(" excel =========================================================================================== ");
         
         // 첫 시트를 만든다.
         HSSFSheet sheet = creatFirstSheet(workbook);
         creatColum(sheet);
         
         int rowNum = 1;
     
         for(Map<String, String> excelInfo : excelList ){
             createRow(sheet, excelInfo , rowNum++);
         }
    }

    // 첫번째 시트를 만든다
    private HSSFSheet creatFirstSheet(HSSFWorkbook workbook){
    
    HSSFSheet sheet = workbook.createSheet();
     // setSheetName(시트순서, 시트이름)
     workbook.setSheetName(0,"APP리스트 현황");
     
     return sheet;
    }
    
    // 시트에 대한 컬럼을 만든다
    private void creatColum(HSSFSheet sheet) {
    
     HSSFRow header = sheet.createRow((short)0);
     
     HSSFCell cell = header.createCell((short)0);
     cell.setCellValue("아이디");
    
     cell = header.createCell((short)1);
     cell.setCellValue("이름");
     
     cell = header.createCell((short)2);
     cell.setCellValue("비밀번호");
     
    }  

    // 각 로우에 대한 값 입력
    private void createRow(HSSFSheet sheet, Map<String, String> excelInfo, int rowNum){
    
     HSSFRow row = sheet.createRow(rowNum);
    
     HSSFCell cell = row.createCell((short)0);
     cell.setCellValue(excelInfo .get("testID"));
     
     cell = row.createCell((short)1);
     cell.setCellValue(excelInfo .get("testName"));
     
     cell = row.createCell((short)2);
     cell.setCellValue(excelInfo .get("testPW"));
        
    }

    @Override
    protected boolean generatesDownloadContent() {
        // TODO Auto-generated method stub
        return super.generatesDownloadContent();
    }

    @Override
    public void setUrl(String url) {
        // TODO Auto-generated method stub
        super.setUrl(url);
    }
    
    

}
