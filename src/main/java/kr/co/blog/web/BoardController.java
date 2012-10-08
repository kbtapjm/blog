package kr.co.blog.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kr.co.blog.domain.Board;
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
import org.springframework.web.servlet.LocaleResolver;
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
    @Autowired LocaleResolver localeResolver;
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
                            BindingResult bindingResult, 
                            RedirectAttributes redirectAttr) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController boardCreateProc method start~!!!");    
        }
        
        redirectAttr.addAttribute("result", "N");
        
        return "redirect:/board/getAllBoard.do";
    }
    
    @RequestMapping(value = "/getAllBoard", method = RequestMethod.GET)
    public String getAllBoard(Model model) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("BoardController getAllBoard method start~!!!");    
        }
        
        return "/board/boardList";
    }

}
