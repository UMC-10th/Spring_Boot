@RequiredArgsConstructor
public class OAuthMember implements OAuth2User {

    @Getter
    private final Member member;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    
    @Override
    public @Nullable String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return member.getSocialUid();
    }

    
}