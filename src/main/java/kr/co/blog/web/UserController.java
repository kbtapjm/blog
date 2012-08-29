package kr.co.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/user")
@Controller(value = "userController")
public class UserController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) throws Exception {
        return "login";
    }

}
