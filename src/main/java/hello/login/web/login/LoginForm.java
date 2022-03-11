package hello.login.web.login;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {
    @NotEmpty
    private String loginId;//로그인 아이디

    @NotEmpty
    private String password;
}
