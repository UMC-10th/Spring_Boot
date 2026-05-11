package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query(
            value = """
                    select m
                    from Mission m
                    join fetch m.store s
                    join fetch s.location l
                    where l.id = :locationId
                    """,
            countQuery = """
                    select count(m)
                    from Mission m
                    join m.store s
                    join s.location l
                    where l.id = :locationId
                    """
    )
    Page<Mission> findMissionsByLocation(
            @Param("locationId") Long locationId,
            Pageable pageable
    );
}