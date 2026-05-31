package com.umc.umc10th.domain.user.dto.request;

import com.umc.umc10th.domain.user.enums.Provider;
import com.umc.umc10th.domain.user.enums.ServiceRole;
import com.umc.umc10th.domain.user.enums.Sex;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

public class UserRequestDto {
    @Builder
    public record CreateUser(
            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String email,
            @NotBlank(message = "비밀번호는 필수입니다.")
            String password,
            Provider provider,
            ServiceRole serviceRole,
            String name,
            Sex sex,
            LocalDate birthday,
            String address,
            String phone
    ) {}

    @Builder
    public record Login(
            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String email,
            @NotBlank(message = "비밀번호는 필수입니다.")
            String password
    ) {}

    @Builder
    public record GetMissionsRequest(
            @NotNull(message = "사용자 ID는 필수입니다")
            Long userId
    ) {}
}
