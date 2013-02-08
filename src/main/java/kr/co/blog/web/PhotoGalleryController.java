package kr.co.blog.web;

import java.util.List;
import java.util.Map;

import kr.co.blog.domain.Board;
import kr.co.blog.service.BoardService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * photoGalleryController
 * @author kbtapjm
 *
 */
@RequestMapping("/photoGallery")
@Controller(value = "photoGalleryController")
public class PhotoGalleryController {
    private static Logger log = Logger.getLogger(PhotoGalleryController.class);
    
    @Autowired MessageSourceAccessor messageSourceAccessor;
    @Autowired private BoardService boardService;
    
    /**
     * 사진 목록
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/photoGalleryList", method = RequestMethod.GET)
    public String photoGalleryList(Model model, @RequestParam Map<String, Object> params) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("PhotoGalleryController photoGalleryList method start~!!!");    
        }
        
        List<Board> resultList = boardService.getAllBoardList(params);
        model.addAttribute("resultList", resultList);
        
        return "/photoGallery/photoGalleryList";
    }
}
