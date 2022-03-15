package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    //회원 인증을 하지 않아도 진입가능
    /**
     * whitelist사용 이유
     * loginCheckFilter()에서         filterFilterRegistrationBean.addUrlPatterns("/*");를 사용하여 전체url에 적용 시키고
     * 미래의 생성되는 인증 구간을 관리하기 위해서
     */

    private static final String[] whitelist={"/","/member/add","/login","/logout","/css/*"};


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("logCheckfilter do filter");
    }

    //cf)default로 선언된 인터페이스의 메서드는 구현을 안해도 가능하다 따라서 init destroy 구현안해도 가능
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            log.info("인증 체크 필터 시작{}", requestURI);
            if (isLoginCheckPath(requestURI)) {
                //화이트리스트가 아니면 동작
                log.info("인증 체크 로직 실행 {}",requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {

                    log.info("미인증 사용자 요청 {}",requestURI);
                    //로그인으로 redirect
                    //?redirectURL="+requestURI 로그인 성공하고 다시 복귀하기 위한 로직 - loginController loginV4로직 참조
                    httpResponse.sendRedirect("/login?redirectURL="+requestURI);

                    return;//다음 서블릿->컨트롤러 과정들을 호출안한다
               }
            }
            chain.doFilter(request,response);
            //화이트 리스트이면 dofilter적용
            //로그인 인증되면 dofilter적용
        } catch (Exception e) {
            throw e;//예외 로깅 가능 하지만, 톰캣까지 예외를 보내주어야 한다
        }finally {
            log.info("인증 체크 필터 종료 {}",requestURI);
        }

    }

    /**
     * 화이트 리스트인 경우 인증 체크 x
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
        //white리스트가 아닌 경우만 true를 반납해야된다
        //로그인인증이기 때문에 부정문을 사용해야한다
    }
}
