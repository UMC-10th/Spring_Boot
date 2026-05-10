package com.example.Spring_Boot.domain.mission.repository;

import com.example.Spring_Boot.domain.mission.entity.mapping.UserMission;
import com.example.Spring_Boot.domain.mission.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    @Query(
            value = """
                    select um
                    from UserMission um
                    join fetch um.mission m
                    join fetch m.store s
                    where um.member.userId = :memberId
                      and um.status = :status
                    order by um.userMissionId desc
                    """,
            countQuery = """
                    select count(um)
                    from UserMission um
                    where um.member.userId = :memberId
                      and um.status = :status
                    """
    )
    Page<UserMission> findUserMissionsByMemberIdAndStatus(
            @Param("memberId") Long memberId,
            @Param("status") Status status,
            Pageable pageable
    );

    @Query(
            value = """
                    select um
                    from UserMission um
                    join fetch um.mission m
                    join fetch m.store s
                    where um.member.userId = :memberId
                      and um.status = :status
                      and s.region.regionId = :regionId
                    order by um.userMissionId desc
                    """,
            countQuery = """
                    select count(um)
                    from UserMission um
                    join um.mission m
                    join m.store s
                    where um.member.userId = :memberId
                      and um.status = :status
                      and s.region.regionId = :regionId
                    """
    )
    Page<UserMission> findUserMissionsByMemberIdAndRegionIdAndStatus(
            @Param("memberId") Long memberId,
            @Param("regionId") Long regionId,
            @Param("status") Status status,
            Pageable pageable
    );

    @Query("""
            select count(um)
            from UserMission um
            where um.member.userId = :memberId
              and um.status = :status
            """)
    Long countUserMissionsByMemberIdAndStatus(
            @Param("memberId") Long memberId,
            @Param("status") Status status
    );
}
