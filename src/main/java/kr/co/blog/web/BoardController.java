package kr.co.blog.web;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.blog.common.Barbecue;
import kr.co.blog.common.CommonUtil;
import kr.co.blog.common.ExcelUtil;
import kr.co.blog.common.FileUtil;
import kr.co.blog.common.MailSend;
import kr.co.blog.common.PageUtil;
import kr.co.blog.common.extract.CsvUtil;
import kr.co.blog.common.extract.XlsUtil;
import kr.co.blog.domain.Board;
import kr.co.blog.domain.BoardReply;
import kr.co.blog.domain.User;
import kr.co.blog.service.BoardReplyService;
import kr.co.blog.service.BoardService;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.WebColors;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Board 웹 요청처리
 * @author jmpark
 *
 */
@RequestMapping("/board")
@Controller(value = "boardController")
public class BoardController {
    private static Logger log = Logger.getLogger(BoardController.class);
    
    @Autowired private BoardService boardService;
    @Autowired private BoardReplyService boardReplyService;
    @Autowired MessageSourceAccessor messageSourceAccessor;
    
    /**
     * 게시글 등록화면
     * @param model
     * @param result
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardCreate", method = RequestMethod.GET)
    public String boardCreate(Model model, @RequestParam(value="result", required=false) String result) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardCreate method start~!!!");    
        }
        model.addAttribute("result", result);
        
        return "/board/boardCreate";
    }
    
    /**
     * 게시글 등록
     * @param request
     * @param model
     * @param board
     * @param bindingResult
     * @param redirectAttr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardCreateProc", method = RequestMethod.POST)
    public String boardCreateProc(HttpServletRequest request, 
                                    Model model, 
                                    @ModelAttribute Board board, 
                                    @RequestParam("attachFile") MultipartFile file,
                                    RedirectAttributes redirectAttr) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardCreateProc method start~!!!");    
        }
        
        // 세션의 유저 정보 가져오기
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("sessionuser");
        
        // 파일업로드 처리
        if(file.getOriginalFilename().length() > 0) {
            FileUtil.fileUpload(file);    
        }
        
        board.setBoardId(UUID.randomUUID().toString());
        board.setIp(request.getRemoteAddr());
        board.setFileName(file.getOriginalFilename());
        board.setFileSize((int)file.getSize());
        board.setUserId(user.getUserId());
        board.setCreateUser(user.getUserName());
        
        int result = boardService.createBoard(board);
        if(result < 0) {
            redirectAttr.addAttribute("result", "N");
            return "/board/boardCreate";
        }
        
        FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
        fm.put("board", board);
        
        return "redirect:/board/boardList.do";
    }
    
    /**
     * 게시글 목록
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardList", method = RequestMethod.GET)
    public String boardList(Model model, @RequestParam Map<String, Object> params) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardList method start~!!!");    
        }
        
        String pageNo = CommonUtil.Nvl((String)params.get("pageNo"), "1" );
        String pageSize = CommonUtil.Nvl((String)params.get("pageSize"), "10" );
        int pSize = Integer.parseInt(pageSize);
        int pNo = Integer.parseInt(pageNo);
        
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        
        List<Board> resultList = boardService.getAllBoardList(params);
        int count = boardService.getAllBoardListCnt(params);
        
        int beginOfPage = (pNo -1) * PageUtil.ROW_PER_PAGE;  
        int rowNo = count - beginOfPage;
        
        model.addAttribute("resultList", resultList);
        model.addAttribute("params", params);
        model.addAttribute("pageControl", PageUtil.getPageLink(count, pNo, "goPage", pSize, PageUtil.PAGE_LINK, ""));
        model.addAttribute("rowNo", rowNo);
        model.addAttribute("today", CommonUtil.getDate()); 
        
        return "/board/boardList";
    }
    
    /**
     * 게시글 검색목록
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardSearchList", method = RequestMethod.POST)
    public String boardSearchList(Model model, @RequestParam Map<String, Object> params) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardSearchList method start~!!!");    
        }
        
        String pageNo = CommonUtil.Nvl((String)params.get("pageNo"), "1" );
        String pageSize = CommonUtil.Nvl((String)params.get("pageSize"), "10" );
        int pSize = Integer.parseInt(pageSize);
        int pNo = Integer.parseInt(pageNo);
        
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        
        List<Board> resultList = boardService.getAllBoardList(params);
        int count = boardService.getAllBoardListCnt(params);
        
        int beginOfPage = (pNo -1) * PageUtil.ROW_PER_PAGE;  
        int rowNo = count - beginOfPage;
        
        model.addAttribute("resultList", resultList);
        model.addAttribute("params", params);
        model.addAttribute("pageControl", PageUtil.getPageLink(count, pNo, "goPage", pSize, PageUtil.PAGE_LINK, ""));
        model.addAttribute("rowNo", rowNo);
        model.addAttribute("today", CommonUtil.getDate());
        
        return "/board/boardList";
    }
    
    /**
     * Typedhead 가져오기(제목)
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getBoardTypeheadSubject", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<String> getBoardTypeheadSubject(Model model) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController getBoardTypeheadSubject method start~!!!");    
        }
        
        List<String> list = boardService.getBoardTypeheadSubject();
        
        return list;
    }
    
    /**
     * 게시글 상세정보
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardRead", method = RequestMethod.POST)
    public String boardRead(HttpServletRequest request, Model model, @RequestParam Map<String, Object> params) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardRead method start~!!!");    
        }
        
        log.debug(" ================================================  ");
        log.debug(" getRealPath : " + request.getServletContext().getRealPath("/"));
        log.debug(" ================================================  ");
        
        String boardId = params.get("boardId").toString();
        
        // 조회수 증가
        boardService.updateBoardCount(boardId);
        
        // 게시글 상세정보
        Board board = boardService.getBoardByBoardId(boardId);
        
        // 바코드 생성 테스트
        String cpNo = "0324-2358-5124-6712";
        String barcodeImg = Barbecue.getBarcodeImg(request, cpNo);
        Barbecue.barcodeGeneration(barcodeImg, Barbecue.CODE_128C, cpNo, Barbecue.GIFTICON_WIDTH_SIZE, Barbecue.GIFTICON_HEIGHT_SIZE, Color.WHITE);
        
        model.addAttribute("board", board);
        model.addAttribute("params", params);
        model.addAttribute("attachFileSize", FileUtil.getFileSize(board.getFileSize()));
        model.addAttribute("barcodeImg", barcodeImg);
        
        return "/board/boardRead";
    }
    
    /**
     * 파일 다운로드
     * @param response
     * @param fileName
     * @throws Exception
     */
    @RequestMapping(value = "/fileDownload", method = RequestMethod.GET)
    public void fileDownload(HttpServletResponse response, @RequestParam("fileName") String fileName) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController fileDownload method start~!!!");    
        }
        
        String downFileName = FileUtil.PATH + fileName;
        
        try {
            FileUtil.fileDownload(response, new File(downFileName));    
        } catch (Exception e) {
            log.debug("fileDownload error : " + e.toString());
        }
    }
    
    /**
     * 게시글 수정화면
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardUpdate", method = RequestMethod.POST)
    public String boardUpdate(Model model, @RequestParam Map<String, Object> params) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardUpdate method start~!!!");    
        }
        
        String boardId = params.get("boardId").toString();
        
        // 수정 할 게시글 정보 얻기
        Board board = boardService.getBoardByBoardId(boardId);
        
        model.addAttribute("board", board);
        model.addAttribute("params", params);
        
        return "/board/boardUpdate";
    }
    
    /**
     * 게시글 수정
     * @param request
     * @param model
     * @param board
     * @param file
     * @param bindingResult
     * @param redirectAttr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardUpdateProc", method = RequestMethod.POST)
    public String boardUpdateProc(HttpServletRequest request, 
                            Model model, 
                            @ModelAttribute Board board, 
                            @RequestParam("attachFile") MultipartFile file,
                            RedirectAttributes redirectAttr,
                            @RequestParam Map<String, Object> params) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardUpdateProc method start~!!!");    
        }
        
        // 파일업로드 처리
        String oldFileName = params.get("oldFileName").toString();
        String oldFileSize = params.get("oldFileSize").toString();
        String newFileName = file.getOriginalFilename();
        
        // 새로운 파일이 존재 할 경우
        if(newFileName.length() > 0) {
            // 기존 파일 삭제
            if(oldFileName.length() > 0) {
                FileUtil.fileDelete(oldFileName);    
            }
            
            // 파일 업로드
            if(file.getOriginalFilename().length() > 0) {
                FileUtil.fileUpload(file);    
            }
            
            board.setFileName(file.getOriginalFilename());
            board.setFileSize((int)file.getSize());
        } else {
            // 기존 파일 정보
            board.setFileName(oldFileName);
            board.setFileSize(Integer.parseInt(oldFileSize));
        }
        
        // 게시글 수정
        int result = boardService.updateBoard(board);
        if(result < 0) {
            model.addAttribute("board", board);
            model.addAttribute("params", params);
            
            redirectAttr.addAttribute("updateResult", "N");
            return "/board/boardUpdate";
        }
        
        // 게시글 상세정보
        board = boardService.getBoardByBoardId(board.getBoardId());
        
        model.addAttribute("board", board);
        model.addAttribute("params", params);
        
        return "/board/boardRead";
    }
    
    /**
     * 게시글 삭제
     * @param model
     * @param params
     * @param board
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardDelete", method = RequestMethod.POST)
    public String boardDelete(Model model, @RequestParam Map<String, Object> params, @ModelAttribute Board board, RedirectAttributes redirectAttr) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardDelete method start~!!!");    
        }
        
        String[] boardIds = {board.getBoardId()};
        int result = boardService.multiDeleteBoard(boardIds);
        
        // 삭제 실패시
        if(result < 0) {
            // 게시글 상세정보
            String boardId = params.get("boardId").toString();
            board = boardService.getBoardByBoardId(boardId);
            
            model.addAttribute("board", board);
            model.addAttribute("params", params);
            redirectAttr.addAttribute("deleteResult", "N");
            
            return "/board/boardRead";
        }
        
        // 삭제 성공 후 목록으로 이동
        String pageNo = CommonUtil.Nvl((String)params.get("pageNo"), "1" );
        String pageSize = CommonUtil.Nvl((String)params.get("pageSize"), "10" );
        int pSize = Integer.parseInt(pageSize);
        int pNo = Integer.parseInt(pageNo);
    
        List<Board> resultList = boardService.getAllBoardList(params);
        int count = boardService.getAllBoardListCnt(params);
        
        // 삭제 후 현재 페이지의 리스트 개수가 0인 경우 전페이지 리스트 호출, 현재페이지 계산
        if(resultList.size() == 0) {
            pNo = (pNo != 1) ? pNo -1 : pNo;    
            params.put("pageNo", pNo);
            resultList = boardService.getAllBoardList(params);
        }
        
        int beginOfPage = (pNo -1) * PageUtil.ROW_PER_PAGE;  
        int rowNo = count - beginOfPage;
        
        model.addAttribute("resultList", resultList);
        model.addAttribute("params", params);
        model.addAttribute("pageControl", PageUtil.getPageLink(count, pNo, "goPage", pSize, PageUtil.PAGE_LINK, ""));
        model.addAttribute("rowNo", rowNo);
        
        return "/board/boardList";
    }
    
    /**
     * 게시글 인쇄
     * @param model
     * @param boardId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardPrint", method = RequestMethod.GET)
    public String boardPrint(Model model, @RequestParam(value="boardId", required=true) String boardId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardPrint method start~!!!");    
        }
        
        // 인쇄할 게시글 정보
        Board board = boardService.getBoardByBoardId(boardId);
        
        model.addAttribute("board", board);
        
        return "/board/boardPrint";
    }
    
    /**
     * E-mail 전송화면
     * @param model
     * @param boardId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardEmailSend", method = RequestMethod.GET)
    public String boardEmailSend(Model model, @RequestParam(value="boardId", required=true) String boardId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardEmailSend method start~!!!");    
        }
        
        model.addAttribute("boardId", boardId);
        
        return "/board/boardEmailSend";
    }
    
    /**
     * 게시글 메일전송처리 
     * @param request
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardEmailSendProc", method = RequestMethod.POST)
    @ResponseBody
    public  Map<String, Object> boardEmailSendProc(HttpServletRequest request, Model model, @RequestParam Map<String, Object> params) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardEmailSendProc method start~!!!");    
        }
        
        // 세션의 유저 정보 가져오기
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("sessionuser");
        
        String boardId = params.get("boardId").toString();
        
        // 전송할 게시글 조회
        Board board = boardService.getBoardByBoardId(boardId);
        
        // 메일전송 처리
        params.put("from", user.getEmail());    // 세션정보의 E-mail 세팅
        params.put("subject", board.getSubject());
        params.put("content", board.getContent());
        params.put("board", board);
        
        Boolean result = MailSend.mailsend(params);
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", result == true ? "success" : "fail");
        
        return resultMap;
    }
    
    /**
     * 선택파일 삭제
     * @param model
     * @param params
     * @param checkBoardId
     * @param board
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardMultiDelete", method = RequestMethod.POST)
    public String boardMultiDelete(Model model, 
                                    @RequestParam Map<String, Object> params, 
                                    @RequestParam("checkBoardId") String[] checkBoardId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardMultiDelete method start~!!!");    
        }

        // 삭제 처리
        int result = boardService.multiDeleteBoard(checkBoardId);
        // 삭제 실패시
        if(result < 0) {
            model.addAttribute("deleteResult", "N");
        }
                
        // 삭제 후 리스트
        String pageNo = CommonUtil.Nvl((String)params.get("pageNo"), "1" );
        String pageSize = CommonUtil.Nvl((String)params.get("pageSize"), "10" );
        int pSize = Integer.parseInt(pageSize);
        int pNo = Integer.parseInt(pageNo);
        
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        
        List<Board> resultList = boardService.getAllBoardList(params);
        int count = boardService.getAllBoardListCnt(params);
        
        // 삭제 후 현재 페이지의 리스트 개수가 0인 경우 전페이지 리스트 호출, 현재페이지 계산
        if(resultList.size() == 0) {
            pNo = (pNo != 1) ? pNo -1 : pNo;    
            params.put("pageNo", pNo);
            resultList = boardService.getAllBoardList(params);
        }
        
        int beginOfPage = (pNo -1) * PageUtil.ROW_PER_PAGE;  
        int rowNo = count - beginOfPage;
        
        model.addAttribute("resultList", resultList);
        model.addAttribute("params", params);
        model.addAttribute("pageControl", PageUtil.getPageLink(count, pNo, "goPage", pSize, PageUtil.PAGE_LINK, ""));
        model.addAttribute("rowNo", rowNo);
        
        return "/board/boardList";
    }
    
    /**
     * 엑셀저장 테스트
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardExcelSaveTest", method = RequestMethod.POST)
    public ModelAndView boardExcelSaveTest(Model model,  @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardExcelSaveTest method start~!!!");    
        }
        
        List<Board> resultList = boardService.getAllBoardList(params);
        
        XlsUtil xlsUtil = new XlsUtil();
        
        xlsUtil.setFileName("전국교통량");
        xlsUtil.setSheetName("traffic");
        
        return xlsUtil.download(resultList); 
    }
    
    /**
     * CSV저장 test
     * @param model
     * @param params
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardCsvSaveTest", method = RequestMethod.POST)
    public ModelAndView boardCsvSaveTest(Model model,  @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardCsvSaveTest method start~!!!");    
        }
        
        List<Board> resultList = boardService.getAllBoardList(params);
        
        CsvUtil xlsUtil = new CsvUtil();
        
        xlsUtil.setFileName("전국교통량");
        xlsUtil.setSheetName("traffic");
        
        return xlsUtil.download(resultList); 
    }
    
    /**
     * 엑셀저장
     * @param model
     * @param params
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/boardExcelSave", method = RequestMethod.POST)
    public String boardExcelSave(Model model,  @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardExcelSave method start~!!!");    
        }

        ExcelUtil excelUtil = new ExcelUtil();
        List<Board> resultList = boardService.getAllBoardList(params);
        
        // 시트 생성
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("board");
        HSSFCell cell = null;
        
        FileOutputStream fs = null;
      
        try {
            for (int i = 0; i < 4; i++) { 
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i, (sheet.getColumnWidth(i)) + 1024 ); 
            }
           
            //sheet.addMergedRegion(new Region(0, (short)0, 0, (short)4));
            
            sheet.addMergedRegion(new CellRangeAddress(1, (short)7, 0, (short)0));
            
            //HSSFCellStyle titlestyle = excelUtil.getTitleStyle(workbook);
            HSSFCellStyle headerstyle = excelUtil.getHeaderStyle(workbook);
            HSSFCellStyle valueStyle = excelUtil.getValueStyle(workbook);
     
            /*
            // title
            HSSFRow title = sheet.createRow(0);
            cell = title.createCell((short)0);
            cell.setCellValue(CommonUtil.getDate() );
            cell.setCellStyle(titlestyle);
            */
            
            // header
            HSSFRow header = sheet.createRow(0);
            
            // hearder create
            cell = header.createCell(0);
            cell.setCellValue(messageSourceAccessor.getMessage("blog.label.no"));
            cell.setCellStyle(headerstyle);
            
            cell = header.createCell(1);
            cell.setCellValue(messageSourceAccessor.getMessage("blog.label.subject"));
            cell.setCellStyle(headerstyle);
            
            cell = header.createCell(2);
            cell.setCellValue(messageSourceAccessor.getMessage("blog.label.create.user"));
            cell.setCellStyle(headerstyle);
            
            cell = header.createCell(3);
            cell.setCellValue(messageSourceAccessor.getMessage("blog.label.create.date"));
            cell.setCellStyle(headerstyle);
            
            cell = header.createCell(4);
            cell.setCellValue(messageSourceAccessor.getMessage("blog.label.views"));
            cell.setCellStyle(headerstyle);
            
            int rowcnt = 1;
            int length = resultList.size() + 1;
            for (Board board : resultList) {
                HSSFRow row = sheet.createRow(rowcnt++);    // row create
                
                cell = row.createCell(0);
                cell.setCellStyle(valueStyle);
                cell.setCellValue(--length);
                
                cell = row.createCell(1);
                cell.setCellStyle(valueStyle);
                cell.setCellValue(board.getSubject());
                
                cell = row.createCell(2);
                cell.setCellStyle(valueStyle);
                cell.setCellValue(board.getUser().getUserName());
                
                cell = row.createCell(3);
                cell.setCellStyle(valueStyle);
                cell.setCellValue(board.getCreateDt());
                
                cell = row.createCell(4);
                cell.setCellStyle(valueStyle);
                cell.setCellValue(board.getCount());
            }
            
            String filename = "List_" + System.currentTimeMillis() + ".xls";
            String excelfile = FileUtil.PATH + filename; 
            
            fs = new FileOutputStream(excelfile);
            workbook.write(fs);
            
            FileUtil.fileDownload(response, new File(excelfile));
        } catch (Exception e) {
            if (fs != null) {
                try { fs.close(); } catch (IOException e1) { e1.printStackTrace(); }
            }
        }
        
        return "/board/boardList";
    }
    
    /*
     * PDF 저장
     */
    @RequestMapping(value = "/boardPdfSave", method = RequestMethod.POST)
    public String boardPdfSave(Model model,  @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardPdfSave method start~!!!");    
        }
        
        // 목록
        List<Board> resultList = boardService.getAllBoardList(params);
        
        String pdfFile = "List_" + System.currentTimeMillis() + ".pdf";  //pdf file
        
        // basic font
        BaseFont bf = BaseFont.createFont("C:\\windows\\fonts\\batang.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf, 10);
        
        // step 1
        Document document = new Document(PageSize.A4, 10, 10, 10, 10);
        
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        
        // step 3
        document.open();
        
        // title
        /*
        String text = "<Image Board>";
        Chunk space = new Chunk(' ');
        Phrase phrase1 = new Phrase(text);
        Paragraph paragraph = new Paragraph();
        paragraph.add(phrase1);
        paragraph.add(space);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        */
        
        // table set
        PdfPTable table = new PdfPTable(5);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
        
        // header color
        Color myColor = WebColors.getRGBColor("#afeeee");
        
        // header create
        PdfPCell cell;
        cell = new PdfPCell(new Paragraph(messageSourceAccessor.getMessage("blog.label.no"), font));
        cell.setFixedHeight(18f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(myColor);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(messageSourceAccessor.getMessage("blog.label.subject"), font));
        cell.setFixedHeight(18f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(myColor);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(messageSourceAccessor.getMessage("blog.label.create.user"), font));
        cell.setFixedHeight(18f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(myColor);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(messageSourceAccessor.getMessage("blog.label.create.date"), font));
        cell.setFixedHeight(18f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(myColor);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(messageSourceAccessor.getMessage("blog.label.views"), font));
        cell.setFixedHeight(18f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(myColor);
        table.addCell(cell);
        
        // table width
        float[] widths = { 0.5f, 4.3f, 1.5f, 2f, 1f };
        table.setWidths(widths);
        
        int length = resultList.size() + 1;
        for (Board board : resultList) {
            if(board == null) continue;
            
            String boardNo = String.valueOf(--length);
            cell = new PdfPCell(new Paragraph(boardNo, font));
            cell.setFixedHeight(72f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
            
            /* 썸네일 이미지 추가시
            Image img = Image.getInstance(FileUtil.PATH + board.getFileName());
            img.scaleAbsoluteHeight(71f);
            img.scaleAbsoluteWidth(51f);
            
            cell = new PdfPCell(img, false);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
            */
            
            cell = new PdfPCell(new Paragraph(board.getSubject(), font));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(board.getUser().getUserName(), font));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(board.getCreateDt().substring(0, 10), font));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            
            String count  = String.valueOf(board.getCount());
            cell = new PdfPCell(new Paragraph(count, font));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        
        // step 4
        document.add(table);
        
        // step 5
        document.close();

        // download
        FileUtil.fileDownload(response, new File(pdfFile));
        
        // file delete
        File file = new File(pdfFile);
        file.delete();
        
        return "/board/boardList";
    }
    
    
    /**
     * 엑셀문서 로드 폼
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardExcelLoad", method = RequestMethod.GET)
    public String boardExcelLoad(Model model) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardEmailSend method start~!!!");    
        }
        
        return "/board/boardExcelLoad";
    }
    
    /**
     * 엑셀문서 읽기
     * @param model
     * @param excelFile
     * @return
     */
    @RequestMapping(value="/boardExcelLoadProc", method=RequestMethod.POST)
    public String boardExcelLoadProc(Model model, @RequestParam("excelFile") MultipartFile excelFile) {
        if (log.isDebugEnabled()) {
            log.debug("BoardController boardExcelLoadProc method start~!!!");    
        }
        
        File uploadedFile = null;
        List<Board> list = new ArrayList<Board>();
        
        try {
            // 파일 존재 유무 체크
            String fileName = excelFile.getOriginalFilename();
            if(fileName.length() == 0) {
                throw new Exception(messageSourceAccessor.getMessage("blog.label.input.excefile"));
            }
            
            // 파일 유효성 체크
            String extensionFile = "";
            if (fileName.lastIndexOf(".") > -1 ) {
                extensionFile = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            } else {
                throw new Exception(messageSourceAccessor.getMessage("blog.label.input.vaild.excelfile"));
            }
            
            // 확장자 체크
            if (extensionFile.lastIndexOf("xls") == -1 && extensionFile.lastIndexOf("xlsx") == -1) {
                throw new Exception(messageSourceAccessor.getMessage("blog.label.input.excefile.only"));
            }
            
            // 동일한 엑셀파일이 있는지 체크, 동일한 파일이 있는 경우 인덱스를 붙여서 생성
            uploadedFile = new File(FileUtil.PATH, fileName);
            if (uploadedFile.exists()) {
                for (int k = 0;  true; k++) {
                    uploadedFile = new File(FileUtil.PATH, "(" + k + ")" + fileName);
                            
                    if (!uploadedFile.exists()) { 
                        fileName = "("+k+")"+fileName;
                        break;
                    }
                }
            }
            
            // 파일 업로드
            uploadedFile = new File(FileUtil.PATH, fileName);
            excelFile.transferTo(uploadedFile);
            
            // 엑셀파일 버젼 체크(2007버젼 이상 읽기)
            if(fileName.lastIndexOf(".xlsx") > 0) {
                list = ExcelUtil.boardExcelData2007Read(uploadedFile);
            } else {
                list = ExcelUtil.boardExcelDataRead(uploadedFile);
            }
            
            model.addAttribute("resultList", list);
            
        } catch (Exception e){
            log.debug("ExcelLoad Exception~!!!! : " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            if(uploadedFile != null) uploadedFile.delete(); 
            e.printStackTrace();
        } finally {
            log.debug("temp excelFile Deleted~!!!!");
            if(uploadedFile != null) uploadedFile.delete();
        }
        
        return "/board/boardExcelLoad";
    }
    
    /**
     * 댓글 등록
     * @param request
     * @param boardReply
     * @param boardId
     * @param replyContent
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardReplyCreate/{boardId}", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    public Map<String, Object> boardReplyCreate(HttpServletRequest request, 
                                @ModelAttribute BoardReply boardReply,
                                @PathVariable("boardId") String boardId,
                                @RequestParam("replyContent") String replyContent) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardReplyCreate method start~!!!");    
        }
        
        // 세션의 유저 정보 가져오기
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("sessionuser");
 
        String replyId = UUID.randomUUID().toString();
        
        // 댓글등록
        boardReply.setReplyId(replyId);
        boardReply.setCreateUser(user.getUserName());
        boardReply.setIp(request.getRemoteAddr());
        boardReply.setReplyContent(replyContent);
        boardReply.setBoardId(boardId);
        boardReply.setUserId(user.getUserId());
        
        int result = boardReplyService.createBoardReply(boardReply);
        
        if(result > 0) {
            boardReply = boardReplyService.getBoardReplyByReplyId(replyId);
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("createResult", (result > 0) ? "Y" : "N");
        resultMap.put("boardReply", boardReply);
        
        return resultMap;
    }
    
    /**
     * 댓글 목록
     * @param boardId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardReplyList/{boardId}", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<BoardReply> boardReplyList(@PathVariable("boardId") String boardId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardReplyCreate method start~!!!");    
        }
        
        List<BoardReply> resultList = boardReplyService.getAllBoardReplyList(boardId);
    
        return resultList;
    }
    
    /**
     * 댓글수정
     * @param request
     * @param boardReply
     * @param replyId
     * @param replyContent
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardReplyUpdate/{replyId}", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    public Map<String, Object> boardReplyUpdate(HttpServletRequest request, 
                                @ModelAttribute BoardReply boardReply,
                                @PathVariable("replyId") String replyId,
                                @RequestParam("replyContent") String replyContent) throws Exception {
        
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardReplyCreate method start~!!!");    
        }
        
        // 댓글 수정
        int result = boardReplyService.updateBoardReply(replyContent, replyId);
        if(result > 0) {
            boardReply = boardReplyService.getBoardReplyByReplyId(replyId);
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("updateResult", (result > 0) ? "Y" : "N");
        resultMap.put("boardReply", boardReply);
        
        return resultMap;
    }
    
    /**
     * 댓글 삭제
     * @param replyId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardReplyDelete/{replyId}", method = RequestMethod.DELETE, produces="application/json")
    @ResponseBody
    public int boardReplyDelete(@PathVariable("replyId") String replyId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardReplyDelete method start~!!!");    
        }
        
        int result = boardReplyService.deleteBoardReply(replyId);
    
        return result;
    }
    
    /**
     * Grid 테스트
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardGridList", method = RequestMethod.GET)
    public String boardGridList(Model model) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardGridList method start~!!!");    
        }
        
        return "/board/boardGridList";
    }
    
    /**
     * 
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getBoardGridList", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    public Map<String, Object> getBoardGridList(Model model, @RequestParam Map<String, Object> params) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController getBoardGridList method start~!!!");    
        }
     
        // parameter
        System.out.println("======================================");
        System.out.println("parammeter : " + params.toString());
        System.out.println("======================================");
        
        // {sord=asc, page=1, nd=1352247946981, sidx=id, _search=false, rows=100}
        String sord = params.get("sord").toString();    // 내림차순 or 오름차순
        String page = params.get("page").toString();    // 몇번째의 페이지를 요청했는지.
        String nd = params.get("nd").toString();        // 현재시간 
        String sidx = params.get("sidx").toString();    // 소팅하는 기준이 되는 인덱스
        String search = params.get("_search").toString();   // 검색유무
        String rows = params.get("rows").toString();        //  페이지 당 몇개의 행이 보여질건지. 
        
        /*
        {
            "total":10, // 총페이지
            "page":10,  // 현재 페이지
            "records":10, // 레코드 갯수
            "rows": [
                {"id":1, "cell":["1", "히히히"]},
                {"id":2, "cell":["2", "호호호"]},
            ]
        }
        
        {id:"15",invdate:"2007-09-01",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"},
        {id:"16",invdate:"2007-09-01",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"}
        */
        
        // 가상데이터 세잍ㅇ
        List rowsList = new ArrayList();
        Map<String, Object> row = null; 
        if(page.equals("1")) {
            for(int i =1; i <= 100; i++) {
                row = new HashMap<String, Object>();
                
                row.put("id", i);
                row.put("invdate", "2012-11-" + i);
                row.put("name", "test" + i);
                row.put("note", "note" + i);
                row.put("amount", "400." + 1);
                row.put("tax", "30.00");
                row.put("total", "430.00");
                
                rowsList.add(row);    
            }
        } else if(page.equals("2")) {
            for(int i = 101; i <= 200; i++) {
                row = new HashMap<String, Object>();
                
                row.put("id", i);
                row.put("invdate", "2012-11-" + i);
                row.put("name", "test" + i);
                row.put("note", "note" + i);
                row.put("amount", "400." + 1);
                row.put("tax", "30.00");
                row.put("total", "430.00");
                
                rowsList.add(row);    
            }
        } else if(page.equals("3")) {
            for(int i = 201; i <= 300; i++) {
                row = new HashMap<String, Object>();
                
                row.put("id", i);
                row.put("invdate", "2012-11-" + i);
                row.put("name", "test" + i);
                row.put("note", "note" + i);
                row.put("amount", "400." + 1);
                row.put("tax", "30.00");
                row.put("total", "430.00");
                
                rowsList.add(row);    
            }
        } else if(page.equals("4")) {
            for(int i = 301; i <= 400; i++) {
                row = new HashMap<String, Object>();
                
                row.put("id", i);
                row.put("invdate", "2012-11-" + i);
                row.put("name", "test" + i);
                row.put("note", "note" + i);
                row.put("amount", "400." + 1);
                row.put("tax", "30.00");
                row.put("total", "430.00");
                
                rowsList.add(row);    
            }
        }
        
        // response
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", 400);
        result.put("page", page);
        result.put("records", 400);
        result.put("rows", rowsList);
        
        return result;
    }
    
    
}
