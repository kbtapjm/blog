package kr.co.blog.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        Object siteLogin = request.getSession().getAttribute("sessionuser");
        if (siteLogin == null) {
            if (log.isDebugEnabled()) {
                log.debug(" 인증값이 없습니다. ");
            }
            response.sendRedirect(request.getContextPath() + "/user/login.do");
           // throw new SecurityException();
        }
        
        return super.preHandle(request, response, handler);
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