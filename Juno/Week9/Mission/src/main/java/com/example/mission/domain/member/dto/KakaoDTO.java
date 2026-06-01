import com.example.mission.domain.member.enums.SocialType;

public class KakaoDTO extends OAuthDTO {
    public KakaoDTO(String socialUid, String email, String name) {
        super(socialUid, email, name, SocialType.KAKAO);
    }
}
