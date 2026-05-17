package umc.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CursorResponseDto<T> {
    private List<T> data;
    private Boolean hasNext;
    private String nextCursor;
    private Integer pageSize;
}