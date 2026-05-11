package umc.domain.user.dto.request;

import java.util.List;

public class UserRequestDto {
    public record SignUpDto(
            String name,
            String gender,
            String birth,
            String address,
            List<String> foodCategory
    ) {}
}
