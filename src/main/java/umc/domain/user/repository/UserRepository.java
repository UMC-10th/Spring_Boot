package umc.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.domain.user.entity.User;
import umc.domain.user.entity.mapping.SocialType;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialTypeAndSocialUid(SocialType socialType, String socialUid);
}