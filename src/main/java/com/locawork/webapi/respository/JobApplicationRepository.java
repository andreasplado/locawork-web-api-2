package com.locawork.webapi.respository;

import com.locawork.webapi.dao.entity.JobApplicationEntity;
import com.locawork.webapi.dto.JobApplicationDTO;
import com.locawork.webapi.dto.MyApplicationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplicationEntity, Integer> {

    @Query(value="SELECT ja.id, j.title, j.description, j.salary, ja.created_at, ja.updated_at, ja.job_id, ja.user_id, u.email, u.contact FROM job_applications ja" +
            " INNER JOIN jobs j ON ja.job_id = j.id" +
            " INNER JOIN users u ON ja.user_id = u.id" +
            " WHERE j.user_id=?1 AND ja.is_approved=FALSE", nativeQuery = true)
    List<JobApplicationDTO> findCandidates(@Param("user_id") int userId);

    @Query(value="SELECT ja.id, j.title, j.description, j.salary, ja.created_at, ja.updated_at, ja.job_id, ja.user_id, u.email, j.longitude, j.latitude, u.contact FROM job_applications ja" +
            " INNER JOIN jobs j ON ja.job_id = j.id" +
            " INNER JOIN users u ON ja.user_id = u.id" +
            " WHERE ja.user_id=?1 AND ja.is_approved=FALSE", nativeQuery = true)
    List<MyApplicationDTO> findMyApplications(@Param("user_id") int userId);

    @Query(value="SELECT ja.* from job_applications ja " +
            "WHERE ja.user_id=?1 AND ja.job_id=?2", nativeQuery = true)
    List<JobApplicationEntity> existJobByUserId(@Param("user_id") int userId, @Param("job_id") int jobId);

    @Query(value="SELECT ja.id, j.title, j.description, j.salary, ja.created_at, ja.updated_at, ja.job_id, ja.user_id, u.email, j.longitude, j.latitude, u.contact FROM job_applications ja" +
            " INNER JOIN jobs j ON ja.job_id = j.id" +
            " INNER JOIN users u ON ja.user_id = u.id" +
            " WHERE ja.id=?1 AND ja.is_approved=FALSE", nativeQuery = true)
    JobApplicationDTO findJobApplicationById(@Param("user_id") int jobApplicationId);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM job_applications ja WHERE ja.user_id=?1", nativeQuery = true)
    void deleteUserJobApplication(@Param("user_id") int userId);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM job_applications ja WHERE ja.id=?1", nativeQuery = true)
    void deleteJobApplication(@Param("id") int jobApplicationId);

    @Modifying
    @Transactional
    @Query(value="UPDATE jobs SET applyer_id=?1 WHERE user_id=?2", nativeQuery = true)
    void updateJob(@Param("applyer_id") int applyerId, @Param("user_id") int userId);

    @Query(value="SELECT ja.*, j.* FROM job_applications ja" +
            " INNER JOIN jobs j ON ja.fk_job = j.id" +
            " WHERE job_applications_pkey!=?1 AND ja.is_approved=TRUE", nativeQuery = true)
    List<JobApplicationEntity> getApprovedJobApplications(@Param("user_id") int userId);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM job_applications ja WHERE ja.job_id=?1", nativeQuery = true)
    void deleteAllByJobId(@Param("job_id") Integer id);

    @Query(value="SELECT ja.id, j.title, ja.contact, ja.email, j.description, j.salary, ja.created_at, ja.updated_at, ja.job_id, ja.user_id, u.email, u.contact FROM job_applications ja" +
            " INNER JOIN jobs j ON ja.job_id = j.id" +
            " INNER JOIN users u ON ja.user_id = u.id" +
            " WHERE j.user_id=?1 AND ja.is_approved=FALSE AND j.title=?2", nativeQuery = true)
    List<JobApplicationDTO> findCandidatesWithFilter(Integer userId, String filter);

    @Modifying
    @Transactional
    @Query(value="UPDATE job_applications SET reason_quitting_job=?1 WHERE job_id=?2", nativeQuery = true)
    void cantGoToWork(@Param("reason")String reason, @Param("jobId")Integer jobId);
}