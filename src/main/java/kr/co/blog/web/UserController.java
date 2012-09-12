package kr.co.blog.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import kr.co.blog.domain.User;
import kr.co.blog.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * User 웹요청 처리
 * @author kbtapjm
 *
 */
@RequestMapping("/user")
@Controller(value = "userController")
@SessionAttributes("user")
public class UserController {
    private static Logger log = Logger.getLogger(UserController.class);
    
    @Autowired 
    private UserService userService;
    
    /**
     *  최초 처음 model 최초 바인딩 하기위함
     *  참조 : http://springmvc.egloos.com/535572
     * @return
     */
    @ModelAttribute("user")
    public User user() {
        return new User();
    }
    
    /**
     * 로그인 화면
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) throws Exception {
        // isDebugEnabled : 성능과 리소스의 효율성을 높이기 위해 사용, 즉 Log 레벨이 DEBUG일때만 표시
        // model : interface, ModelMap : 구현체 => 사용성의 차이
        if(log.isDebugEnabled()) {
            log.debug("userController login method start~!!!");    
        }
        
        model.addAttribute("user", user());
        
        return "login";
    }
    
    /**
     * 회원가입 화면
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUp(Model model, @RequestParam(value="result", required=false) String result) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("userController signUp method start~!!!");    
        }
        
        model.addAttribute("result", result);
        
        return "/user/signUp";
    }
    
    /**
     * 유저 등록
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String createUser(HttpServletRequest request, 
                            Model model, 
                            @Valid @ModelAttribute User user, 
                            BindingResult bindingResult, 
                            SessionStatus sessionStatus,
                            RedirectAttributes redirectAttr) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("userController createUser method start~!!!");    
        }
        
        if(bindingResult.hasErrors()) {
            return "/user/signUp";
        }
        
        String userId = UUID.randomUUID().toString(); 
        
        user.setUserId(userId);
        int result = userService.createUser(user);
        
        // 성공일 경우 login 처리
        if(result > 0) {
            user = userService.getUserByUserId(userId);
            
            // 세션에 유저정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("sessionuser", user);
            model.addAttribute("user", user);   // @SessionAttributes에서 사용하기 위해서 저장
            
            // home으로 이동
            return "redirect:home.do";
        }
        
        // 실패시 회원가입 화면
        //model.addAttribute("result", "N");
        redirectAttr.addAttribute("result", "N");   // parameter uri세팅
        
        return "redirect:/user/signUp.do";
    }
    
    /**
     * 아이디 중복체크
     * @param model
     * @param memberId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/membercheck", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public Map<String, Object> memberCheck(Model model, @RequestParam String memberId) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("userController memberCheck method start~!!!");    
        }
        
        User user = userService.getUserBymemberId(memberId);
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", (user != null) ? true : false);
        resultMap.put("user", user);
        
        return resultMap;
    }
    
    /**
     * 로그인 처리
     * @param model
     * @param memberId
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/loginProc", method = RequestMethod.POST)
    public String loginProc(HttpServletRequest request, Model model, @RequestParam String memberId, @RequestParam String password) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("userController loginProc method start~!!!");    
        }
        
        User user = userService.getUserLoginInfo(memberId, password); 
        
        if(user != null) {
            // 세션에 유저정보 세팅
            HttpSession session = request.getSession();
            session.setAttribute("sessionuser", user);
            model.addAttribute("user", user);
            
            // home으로 이동
            return "redirect:home.do";
        } else {
            model.addAttribute("result", (user != null) ? true : false);
            model.addAttribute("user", user);
        }
        
        return "login";      
    }
    
    /**
     * 메인
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("userController home method start~!!!");    
        }
        
        return "home";
    }
    
    /**
     * 회원정보
     * @param request
     * @param model
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, @ModelAttribute User user) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("userController userInfo method start~!!!");    
        }
    
        return "/user/userInfo";
    }

}

