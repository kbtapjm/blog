package kr.co.blog.common.viewer;

import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.LocalizedResourceHelper;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * Spring AbstractView를 상속받아서 ExcelView를 생성
 * <pre>
 * pacakage : kr.co.blog.common.viewer 
 * file     : ExcelView.java , 2013. 10. 30.
 * author   : jmpark
 * email    : kbtapjm@gmail.com
 * 수정내용
 * ----------------------------------------------
 * 수정일      수정자  수정내용
 * ----------------------------------------------
 * 
 *</pre>
 */
public class ExcelView extends AbstractView {

    /** The content type for an Excel response */
    private static final String CONTENT_TYPE = "application/vnd.ms-excel";

    /** The extension to look for existing templates */
    private static final String EXTENSION = ".xls";
    protected String url;
    
    /**
     * Default Constructor.
     * Sets the content type of the view to "application/vnd.ms-excel".
     */
    public ExcelView() {
        setContentType(CONTENT_TYPE);
    }

    /**
     * Set the URL of the Excel workbook source, without localization part nor extension.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    /**
     * Renders the Excel view, given the specified model.
     */
    @Override
    protected void renderMergedOutputModel(
            Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HSSFWorkbook workbook;
        if (this.url != null) {
            workbook = getTemplateSource(this.url, request);
        }
        else {
            workbook = new HSSFWorkbook();
            logger.debug("Created Excel Workbook from scratch");
        }

        // Set the content type.
        response.setContentType(getContentType());

        // Flush byte array to servlet output stream.
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
    }

    /**
     * Creates the workbook from an existing XLS document.
     * @param url the URL of the Excel template without localization part nor extension
     * @param request current HTTP request
     * @return the HSSFWorkbook
     * @throws Exception in case of failure
     */
    protected HSSFWorkbook getTemplateSource(String url, HttpServletRequest request) throws Exception {
        LocalizedResourceHelper helper = new LocalizedResourceHelper(getApplicationContext());
        Locale userLocale = RequestContextUtils.getLocale(request);
        Resource inputFile = helper.findLocalizedResource(url, EXTENSION, userLocale);

        // Create the Excel document from the source.
        if (logger.isDebugEnabled()) {
            logger.debug("Loading Excel workbook from " + inputFile);
        }
        POIFSFileSystem fs = new POIFSFileSystem(inputFile.getInputStream());
        return new HSSFWorkbook(fs);
    }

    /**
     * Convenient method to obtain the cell in the given sheet, row and column.
     * <p>Creates the row and the cell if they still doesn't already exist.
     * Thus, the column can be passed as an int, the method making the needed downcasts.
     * @param sheet a sheet object. The first sheet is usually obtained by workbook.getSheetAt(0)
     * @param row thr row number
     * @param col the column number
     * @return the HSSFCell
     */
    @SuppressWarnings("deprecation")
    protected HSSFCell getCell(HSSFSheet sheet, int row, int col) {
        HSSFRow sheetRow = sheet.getRow(row);
        if (sheetRow == null) {
            sheetRow = sheet.createRow(row);
        }
        HSSFCell cell = sheetRow.getCell((short) col);
        if (cell == null) {
            cell = sheetRow.createCell((short) col);
        }
        return cell;
    }

    /**
     * Convenient method to set a String as text content in a cell.
     * @param cell the cell in which the text must be put
     * @param text the text to put in the cell
     */
    protected void setText(HSSFCell cell, String text) {
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(text);
    }

    protected ExcelViewerConvert excelViewerConvert;
    
    /**
     * ExcelViewer 인터페이스를 구현한 구현체가 컨버터 클래스를
     * 소유하고있는지 리턴한다.
     * @return
     */
    protected final boolean hasConvert() {
        return excelViewerConvert != null;
    }
}
