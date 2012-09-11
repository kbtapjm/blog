package kr.co.blog.web.security;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.blog.common.CommonUtil;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = Logger.getLogger(SecurityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("SecurityInterceptor preHandle~!!!");    
        }
        
        // session검사
        HttpSession session = request.getSession();
        Object sessionuser = session.getAttribute("sessionuser");
        if (sessionuser == null) {
            if (log.isDebugEnabled()) {
                log.debug(" 인증값이 없습니다. ");
            }
            
            throw new SecurityException();
            //response.sendRedirect(request.getContextPath() + "/user/login.do");
            //return false;
        } else {
            String sessionId = session.getId();
            String sessionCreationTime = CommonUtil.getStrDateTime(session.getCreationTime());
            String sessionLastAccessedTime = CommonUtil.getStrDateTime(session.getLastAccessedTime());
            String sessionControlValue = request.getParameter("sessionuser");
            session.setAttribute("sessionuser", sessionuser);
            
            if (log.isDebugEnabled()) {
                log.debug(" ==== > session-id: " + sessionId);
                log.debug(" ==== > session-creation-time: " + sessionCreationTime);
                log.debug(" ==== > session-last-accessed-time: " + sessionLastAccessedTime);
                log.debug(" ==== > session-control-value: " + sessionControlValue);
                log.debug(" ==== > sessionuser: " + sessionuser);
            }
        }
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("SecurityInterceptor postHandle~!!!");    
        }
        
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("SecurityInterceptor afterCompletion~!!!");    
        }
        
        super.afterCompletion(request, response, handler, ex);
    }

    
}
;