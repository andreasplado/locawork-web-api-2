package com.locawork.webapi.respository;

import com.locawork.webapi.dao.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Integer> {

    @Query(value="SELECT j.* from jobs j " +
            "INNER JOIN users u ON j.user_id = u.id " +
            "WHERE earth_box(ll_to_earth(?1,?2),?3) @> ll_to_earth(j.latitude,j.longitude) AND j.applyer_id IS NULL AND u.id!=?4 AND j.is_done=false", nativeQuery = true)
    List<JobEntity> findAvailableJobs(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("distance") Double distance, @Param("userId") Integer userId);

    @Query(value="SELECT j.* from jobs j " +
            "INNER JOIN users u ON j.user_id = u.id " +
            "WHERE u.id=?1 AND j.is_done=false", nativeQuery = true)
    List<JobEntity> findPostedJobs(@Param("userId") Integer userId);

    @Query(value="SELECT j.* from jobs j " +
            "WHERE j.id=?1", nativeQuery = true)
    JobEntity findSingle(@Param("id") Integer id);

    @Query(value="SELECT j.* from jobs j " +
            "INNER JOIN users u ON j.user_id = u.id " +
            "WHERE j.applyer_id=?1 AND j.is_done=true AND j.work_end_time IS NOT NULL", nativeQuery = true)
    List<JobEntity> findMyDoneWork(@Param("userId") Integer userId);


    @Query(value="SELECT j.* from jobs j " +
            "INNER JOIN users u ON j.user_id = u.id " +
            "WHERE j.applyer_id=?1 AND j.is_done=true AND j.work_end_time IS NOT NULL AND j.created_at >= date_trunc('day', now())", nativeQuery = true)
    List<JobEntity> findMyDoneWorkToday(@Param("userId") Integer userId);

    @Query(value="SELECT j.* from jobs j " +
            "INNER JOIN users u ON j.user_id = u.id " +
            "WHERE j.applyer_id=?1 AND j.is_done=true AND j.work_end_time IS NOT NULL AND j.created_at >= date_trunc('week', now())", nativeQuery = true)
    List<JobEntity> findMyDoneWorkThisWeek(Integer userId);

    @Query(value="SELECT j.* from jobs j " +
            "INNER JOIN users u ON j.user_id = u.id " +
            "WHERE j.applyer_id=?1 AND j.is_done=true AND j.work_end_time IS NOT NULL AND j.created_at >= date_trunc('month', now())", nativeQuery = true)
    List<JobEntity> findMyDoneWorkThisMonth(Integer userId);
    @Modifying
    @Transactional
    @Query(value="UPDATE jobs SET is_done=true, status='working', work_start_time=?1 WHERE applyer_id=?2 AND id=?3", nativeQuery = true)
    void startWork(@Param("work_start_time")String startTime, @Param("applyer_id") int applyerId, @Param("id") int id);

    @Modifying
    @Transactional
    @Query(value="UPDATE jobs SET is_done=true, status='done', work_end_time=?1 WHERE applyer_id=?2 AND id=?3", nativeQuery = true)
    void endWork(@Param("work_end_time")String endTime, @Param("applyer_id") int applyerId, @Param("id") int id);



    @Query(value="SELECT j.id, j.title, j.user_id, j.description, j.category_id, j.salary, j.latitude, j.longitude, j.is_done, u.firebase_token from jobs j " +
            "INNER JOIN users u ON j.user_id = u.id " +
            "WHERE earth_box(ll_to_earth(?1,?2),?3) @> ll_to_earth(j.latitude,j.longitude) AND j.applyer_id IS NULL AND j.is_done=false", nativeQuery = true)
    List<JobEntity> findAllNearestJobs(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("distance") Double distance);

    @Query(value="SELECT j.* from jobs j " +
            "WHERE j.applyer_id=?1 AND j.is_done=false AND j.reason_quitting_job=''", nativeQuery = true)
    List<JobEntity> findUpcomingWork(@Param("userId") Integer userId);
}