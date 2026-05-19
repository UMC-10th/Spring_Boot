package umc.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}