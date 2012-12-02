package kr.co.blog.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    
    /**
     * 사진 목록
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/photoGalleryList", method = RequestMethod.GET)
    public String photoGalleryList(Model model) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("PhotoGalleryController photoGalleryList method start~!!!");    
        }
        
        return "/photoGallery/photoGalleryList";
    }
}
