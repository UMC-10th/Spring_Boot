package com.umc.umc10th.domain.user.dto.request;

import com.umc.umc10th.domain.user.enums.Provider;
import com.umc.umc10th.domain.user.enums.ServiceRole;
import com.umc.umc10th.domain.user.enums.Sex;
import lombok.Builder;

import java.time.LocalDate;

public class UserRequestDto {
    @Builder
    public record CreateUser (
            String id,
            String password,
            Provider provider,
            ServiceRole serviceRole,
            String name,
            Sex sex,
            LocalDate birthday,
            String address,
            String phone
    ){}
}
