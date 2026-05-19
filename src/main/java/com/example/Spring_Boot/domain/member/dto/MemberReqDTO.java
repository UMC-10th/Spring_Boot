package com.example.Spring_Boot.domain.member.dto;

import com.example.Spring_Boot.domain.member.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    @Builder
    public record CreateMemberRequest(
            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            String email,

            @NotBlank(message = "비밀번호는 필수입니다.")
            @Size(min = 8, max = 30, message = "비밀번호는 8자 이상 30자 이하로 입력해야 합니다.")
            String password,

            @NotBlank(message = "이름은 필수입니다.")
            @Size(max = 5, message = "이름은 5자 이하로 입력해야 합니다.")
            String name,

            @NotBlank(message = "닉네임은 필수입니다.")
            @Size(max = 15, message = "닉네임은 15자 이하로 입력해야 합니다.")
            String nickname,

            @NotBlank(message = "전화번호는 필수입니다.")
            @Size(max = 15, message = "전화번호는 15자 이하로 입력해야 합니다.")
            String phoneNumber,

            @NotNull(message = "성별은 필수입니다.")
            Gender gender,

            @NotNull(message = "생년월일은 필수입니다.")
            LocalDate birth,

            @NotBlank(message = "주소는 필수입니다.")
            String address,
            List<Long> categoryIds
    ) {
    }
}
