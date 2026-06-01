package com.example.Spring_Boot.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

public class UserReqDTO {

    @Getter
    @NoArgsConstructor
    public static class JoinDTO {

        @NotBlank(message = "이름은 필수입니다.")
        private String name;

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;

        private String gender;
        private String birthDate;
        private String address;
        private List<String> foodCategories;
    }

    @Getter
    @NoArgsConstructor
    public static class LoginDTO {

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;
    }
}