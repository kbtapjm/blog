package kr.co.blog.web.security;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.blog.common.CommonUtil;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 웹 요청처리시 인증값 확인
 * @author jmpark
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = Logger.getLogger(SecurityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(log.isDebugEnabled()) {
            log.debug("SecurityInterceptor preHandle~!!!");    
        }
        
        // /user/로 시작하는 uri에서 맵팽처리 제외하는 방법도 있음
        String uri = request.getRequestURI();
        String host = request.getRemoteHost();
        int port = request.getRemotePort();
        String address = request.getRemoteAddr();
        String user = request.getRemoteUser();
        String url = request.getRequestURL().toString();
        int remotePort = request.getRemotePort();
        String requestSessionId = request.getRequestedSessionId();
        Locale locale = request.getLocale();
        if(log.isDebugEnabled()) {
            log.debug(" ==================================================");
            log.debug(" ==== >  uri: " +  uri);
            log.debug(" ==== >  host: " +  host);
            log.debug(" ==== >  port: " +  port);
            log.debug(" ==== >  address: " +  address);
            log.debug(" ==== >  user: " +  user);
            log.debug(" ==== >  url: " +  url);
            log.debug(" ==== >  remotePort: " +  remotePort);
            log.debug(" ==== >  requestSessionId: " +  requestSessionId);
            log.debug(" ==== >  locale: " +  locale.toString());
            log.debug(" ==================================================");
        }
        
        // 인터셉터에서 user 경로의 요청에서 체크제외  mapping
        /*
        if(uri.lastIndexOf("login") != -1 || uri.lastIndexOf("signUp") != -1 || uri.lastIndexOf("createUser") != -1 || uri.lastIndexOf("membercheck") != -1 || uri.lastIndexOf("loginProc") != -1) {
            return true;
        }
        */
        
        // session검사
        HttpSession session = request.getSession();
        Object sessionuser = session.getAttribute("sessionuser");
        if (sessionuser == null) {
            if (log.isDebugEnabled()) {
                log.debug(" 인증값이 없습니다. ");
            }
            
            // 1) Exception Resolver로 처리
            throw new SecurityException();
            
            // 2) redirect로 처리
            //response.sendRedirect(request.getContextPath() + "/user/login.do");
            //return false;
        } else {
            String sessionId = session.getId();
            String sessionCreationTime = CommonUtil.getStrDateTime(session.getCreationTime());
            String sessionLastAccessedTime = CommonUtil.getStrDateTime(session.getLastAccessedTime());
            session.setAttribute("sessionuser", sessionuser);
            
            if (log.isDebugEnabled()) {
                log.debug(" ==================================================");
                log.debug(" ==== > session-id: " + sessionId);
                log.debug(" ==== > session-creation-time: " + sessionCreationTime);
                log.debug(" ==== > session-last-accessed-time: " + sessionLastAccessedTime);
                log.debug(" ==== > sessionuser: " + sessionuser);
                log.debug(" ==================================================");
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