package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;


@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init ");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter do filter");
        //ServletRequest은 HttpServletRequest의 부모 인터페이스 따라서 다운 캐스팅을 해준다
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST[{}][{}]", uuid, requestURI);
            chain.doFilter(request,response);
            //***             chain.doFilter(request,response); 를 사용해 다음 필터를 호출해줘야 한다(없으면 서블릿 호출 못한다)
            //다음 필터가 있으면 다음필터호출 없으면 서블릿
            //매우 중요 프레임 워크 구조흐름(최상위 우선순위로 돌아오는 구조) 이해하기.즉,  어떠한 함수도 호출하는 상위 메서드가있고 최종은 main함수다.
        } catch (Exception e) {
            throw e;
        }finally {
            log.info("RESPONSE[{}][{}]", uuid, requestURI);
        }


    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
