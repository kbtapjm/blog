package kr.co.blog.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import kr.co.blog.common.CommonUtil;
import kr.co.blog.common.ExcelUtil;
import kr.co.blog.common.FileUtil;
import kr.co.blog.common.MailSend;
import kr.co.blog.common.PageUtil;
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
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Board 웹 요청처리
 * @author jmpark
 *
 */
@RequestMapping("/board")
@Controller(value = "boardController")
public class BoardController {
    private static Logger log = Logger.getLogger(BoardController.class);
    
    @Autowired BoardService boardService;
    @Autowired BoardReplyService boardReplyService;
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
                            @Valid @ModelAttribute Board board, 
                            @RequestParam("attachFile") MultipartFile file,
                            BindingResult bindingResult,
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
        board.setUrlType("1");
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
            log.debug("BoardController getAllBoard method start~!!!");    
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
            log.debug("BoardController getAllBoard method start~!!!");    
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
    public Map<String, Object> getBoardTypeheadSubject(Model model) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardTypeheadSubject method start~!!!");    
        }
        
        List<String> list = boardService.getBoardTypeheadSubject();
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("list", list);
        
        return resultMap;
    }
    
    /**
     * 게시글 상세정보
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardRead", method = RequestMethod.POST)
    public String boardRead(Model model, @RequestParam Map<String, Object> params) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController getAllBoard method start~!!!");    
        }
        
        String boardId = params.get("boardId").toString();
        
        // 조회수 증가
        boardService.updateBoardCount(boardId);
        
        // 게시글 상세정보
        Board board = boardService.getBoardByBoardId(boardId);
        
        model.addAttribute("board", board);
        model.addAttribute("params", params);
        
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
            log.debug("download error : " + e.toString());
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
        
        // 게시글 상세정보
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
                            @Valid @ModelAttribute Board board, 
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
            
            redirectAttr.addAttribute("result", "N");
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
    public String boardDelete(Model model, @RequestParam Map<String, Object> params, @Valid @ModelAttribute Board board) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardDelete method start~!!!");    
        }
        
        // 첨부파일 삭제
        if(board.getFileName().length() > 0) {
            FileUtil.fileDelete(board.getFileName());    
        }
        
        // 게시글 삭제
        int result = boardService.deleteBoard(board);
        
        // 삭제 실패시
        if(result < 0) {
            // 게시글 상세정보
            String boardId = params.get("boardId").toString();
            board = boardService.getBoardByBoardId(boardId);
            
            model.addAttribute("board", board);
            model.addAttribute("params", params);
            
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
            log.debug("BoardController boardEmailSend method start~!!!");    
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
    public String boardMultiDelete(Model model,  @RequestParam Map<String, Object> params, @RequestParam("checkBoardId") String[] checkBoardId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardMultiDelete method start~!!!");    
        }

        // 삭제처리
        for(String boardId : checkBoardId) {
            // 삭제할 파일 조회
            Board board = boardService.getBoardByBoardId(boardId);
            
            // 첨부파일 삭제
            if(board.getFileName().length() > 0) {
                FileUtil.fileDelete(board.getFileName());    
            }
            
            // 댓글 삭제
            
            // 게시글 삭제
            boardService.deleteBoard(board);
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
     * 엑셀저장
     * @param model
     * @param params
     * @param response
     * @return
     * @throws Exception
     */
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
           
            sheet.addMergedRegion(new Region(0, (short)0, 0, (short)4));
            
            HSSFCellStyle titlestyle = excelUtil.getTitleStyle(workbook);
            HSSFCellStyle headerstyle = excelUtil.getHeaderStyle(workbook);
            HSSFCellStyle valueStyle = excelUtil.getValueStyle(workbook);
     
            // title
            HSSFRow title = sheet.createRow(0);
            cell = title.createCell((short)0);
            cell.setCellValue(CommonUtil.getDate() );
            cell.setCellStyle(titlestyle);
            
            // header
            HSSFRow header = sheet.createRow(2);
            
            // hearder create
            cell = header.createCell((short)0);
            cell.setCellValue(messageSourceAccessor.getMessage("blog.label.no"));
            cell.setCellStyle(headerstyle);
            
            cell = header.createCell((short)1);
            cell.setCellValue(messageSourceAccessor.getMessage("blog.label.subject"));
            cell.setCellStyle(headerstyle);
            
            cell = header.createCell((short)2);
            cell.setCellValue(messageSourceAccessor.getMessage("blog.label.create.user"));
            cell.setCellStyle(headerstyle);
            
            cell = header.createCell((short)3);
            cell.setCellValue(messageSourceAccessor.getMessage("blog.label.create.date"));
            cell.setCellStyle(headerstyle);
            
            cell = header.createCell((short)4);
            cell.setCellValue(messageSourceAccessor.getMessage("blog.label.views"));
            cell.setCellStyle(headerstyle);

            
            int rowcnt = 3;
            int length = resultList.size() + 1;
            for (Board board : resultList) {
                HSSFRow row = sheet.createRow(rowcnt++);    // row create
                
                cell = row.createCell((short)0);
                cell.setCellStyle(valueStyle);
                cell.setCellValue(--length);
                
                cell = row.createCell((short)1);
                cell.setCellStyle(valueStyle);
                cell.setCellValue(board.getSubject());
                
                cell = row.createCell((short)2);
                cell.setCellStyle(valueStyle);
                cell.setCellValue(board.getUser().getUserName());
                
                cell = row.createCell((short)3);
                cell.setCellStyle(valueStyle);
                cell.setCellValue(board.getCreateDt());
                
                cell = row.createCell((short)4);
                cell.setCellStyle(valueStyle);
                cell.setCellValue(board.getCount());
            }
            
            String filename = System.currentTimeMillis() + ".xls";
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
    
    /**
     * 댓글 등록
     * @param request
     * @param boardReply
     * @param boardId
     * @param replyContent
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardReplyCreate/{boardId}", method = RequestMethod.POST)
    @ResponseBody
    public BoardReply boardReplyCreate(HttpServletRequest request, 
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
        
        return boardReply;
    }
    
    /**
     * 댓글 목록
     * @param boardId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardReplyList/{boardId}", method = RequestMethod.GET)
    @ResponseBody
    public List<BoardReply> boardReplyList(@PathVariable("boardId") String boardId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardReplyCreate method start~!!!");    
        }
        
        List<BoardReply> resultList = boardReplyService.getAllBoardReplyList(boardId);
    
        return resultList;
    }
    
    /**
     * 댓글 삭제
     * @param replyId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boardReplyDelete/{replyId}", method = RequestMethod.DELETE)
    @ResponseBody
    public int boardReplyDelete(@PathVariable("replyId") String replyId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardReplyDelete method start~!!!");    
        }
        
        int result = boardReplyService.deleteBoardReply(replyId);
    
        return result;
    }

}
