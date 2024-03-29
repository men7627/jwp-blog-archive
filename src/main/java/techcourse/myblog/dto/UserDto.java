package techcourse.myblog.dto;

import lombok.Data;
import techcourse.myblog.domain.user.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {

    @Data
    public static class Create {
        @Size(max = 40)
        @NotBlank
        @Email(message = "올바른 email 형식이 아닙니다.")
        private String email;


        @NotBlank
        @Pattern(regexp = ".*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*",
                message = "올바른 비밀번호 형식이 아닙니다.")
        private String password;

        @NotBlank
        @Pattern(regexp = "[A-Za-zㄱ-ㅎㅏ-ㅣ가-힣]{2,10}",
                message = "올바른 이름 형식이 아닙니다.")
        private String name;

        public User toUser() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .build();
        }
    }

    @Data
    public static class Update {
        @NotBlank
        @Pattern(regexp = "[A-Za-zㄱ-ㅎㅏ-ㅣ가-힣]{2,10}",
                message = "올바른 이름 형식이 아닙니다.")
        private String name;
    }

    @Data
    public static class Response {
        private Long id;
        private String email;
        private String name;

        public Response(Long userId, String userEmail, String userName) {
            id = userId;
            email = userEmail;
            name = userName;
        }

        public User toUser() {
            return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();
        }

    }

    @Data
    public static class Login {
        private String email;
        private String password;
    }
}
