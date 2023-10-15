package com.locawork.webapi.respository;

import com.locawork.webapi.dao.entity.CommentsEntity;
import com.locawork.webapi.dao.entity.JobEntity;
import com.locawork.webapi.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<CommentsEntity, Integer> {

    @Query(value="SELECT c.* from comments c " +
            "WHERE j.id=?1", nativeQuery = true)
    List<JobEntity> findAllByJobId(Integer jobId);
}
