package kr.co.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/user")
@Controller(value = "userController")
public class UserController {

    /**
     * 로그인 화면
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) throws Exception {
        return "login";
    }
    
    /**
     * 회원가입
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUp(Model model) throws Exception {
        return "/user/signUp";
    }
    

}
