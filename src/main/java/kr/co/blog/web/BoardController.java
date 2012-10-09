package kr.co.blog.web;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import kr.co.blog.common.FileUtil;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
    public String boardCreate(Model model) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardCreate method start~!!!");    
        }
        
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
                            BindingResult bindingResult) throws Exception {
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
        
        return "redirect:/board/boardList.do";
    }
    
    @RequestMapping(value = "/boardList", method = RequestMethod.GET)
    public String boardList(Model model) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController getAllBoard method start~!!!");    
        }
        
        return "/board/boardList";
    }

}
