package umc.domain.mission.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.domain.user.entity.mapping.MissionStatus; // 👈 유저 매핑 패키지 명시
import umc.domain.user.entity.mapping.UserMission;   // 👈 유저 매핑 패키지 명시

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    @Query("SELECT um FROM UserMission um JOIN FETCH um.mission m JOIN FETCH m.store s " +
            "WHERE um.user.id = :userId AND um.status = :status")
    Page<UserMission> findMyMissions(
            @Param("userId") Long userId,
            @Param("status") MissionStatus status,
            Pageable pageable
    );
}