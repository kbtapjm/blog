package kr.co.blog.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import kr.co.blog.common.CommonUtil;
import kr.co.blog.common.FileUtil;
import kr.co.blog.common.MailSend;
import kr.co.blog.common.PageUtil;
import kr.co.blog.domain.Board;
import kr.co.blog.domain.User;
import kr.co.blog.service.BoardService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        FileUtil.fileUpload(file);
        
        board.setBoardId(UUID.randomUUID().toString());
        board.setNoticeYn("N");
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
            FileUtil.fileDelete(oldFileName);
            
            // 파일 업로드
            FileUtil.fileUpload(file);
            
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
        FileUtil.fileDelete(board.getFileName());
        
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

}
