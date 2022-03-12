package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.*;


class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTEst() {

        //세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();//***

        Member member = new Member();
        sessionManager.creatSession(member,response);


        //요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());//mySessionId=2132132135354

        //세션 조회
        Object result = sessionManager.getSession(request);

        assertThat(result).isEqualTo(member);

        //세션만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isEqualTo(null);

    }

}
