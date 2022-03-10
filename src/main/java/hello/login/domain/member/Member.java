package hello.login.domain.member;

import lombok.Data;

@Data
public class Member {
    private Long id;//데이터 저장 아이디
    private String loginId;//로그인 아이디
    private String name; //사용자 이름

}
