package com.locawork.webapi.respository;

import com.locawork.webapi.dao.entity.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SettingsRepository extends JpaRepository<SettingsEntity, Integer> {

    @Query(value="SELECT * FROM settings s " +
            "WHERE s.user_id=?1", nativeQuery = true)
    SettingsEntity findUserSettings(@Param("user_id") Integer userId);

    @Query(value="SELECT CASE WHEN COUNT(s)> 0 then true else false end FROM settings s WHERE s.user_id=?1", nativeQuery = true)
    boolean exists(@Param("user_id") Integer userId);

    @Modifying
    @Transactional
    @Query(value="UPDATE settings SET radius=?1 WHERE user_id=?2", nativeQuery = true)
    void setRadius(@Param("radius") Double radius, @Param("user_id") Integer userId);

    @Modifying
    @Transactional
    @Query(value="UPDATE settings SET view_by_default=?1 WHERE user_id=?2", nativeQuery = true)
    void setViewByDefault(@Param("value") String value, @Param("user_id") Integer userId);


    @Modifying
    @Transactional
    @Query(value="UPDATE settings SET ask_permissions_before_deleting_a_job=?1 WHERE user_id=?2", nativeQuery = true)
    void setAskPermissionsBeforeDeletingAJob(@Param("value") Boolean value, @Param("user_id") Integer userId);

    @Modifying
    @Transactional
    @Query(value="UPDATE settings SET show_information_on_startup=?1 WHERE user_id=?2", nativeQuery = true)
    void setShowInformationOnStartup(@Param("value") Boolean value, @Param("user_id") Integer userId);

    @Modifying
    @Transactional
    @Query(value="UPDATE settings SET is_biometric=?1 WHERE user_id=?2", nativeQuery = true)
    void updateBiometric(@Param("value") Boolean value, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value="UPDATE settings SET role=?1 WHERE user_id=?2", nativeQuery = true)
    void purchaseMember(@Param("role") String role, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value="UPDATE settings SET role='' WHERE member_start_time < DATE_SUB(CURDATE(),INTERVAL 1 MONTH)", nativeQuery = true)
    void removeAllRoles();
}
