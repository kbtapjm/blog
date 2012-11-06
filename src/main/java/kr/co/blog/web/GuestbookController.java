package kr.co.blog.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * GuestbookController
 * @author kbtapjm
 *
 */
@RequestMapping("/guestbook")
@Controller(value = "guestbookController")
public class GuestbookController {
    private static Logger log = Logger.getLogger(GuestbookController.class);
    
    @Autowired MessageSourceAccessor messageSourceAccessor;
    
    /**
     * 방명록
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/guestbookList", method = RequestMethod.GET)
    public String guestbookList(Model model) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("GuestBookController guestbookList method start~!!!");    
        }
        
        return "/guestbook/guestbookList";
    }

}
