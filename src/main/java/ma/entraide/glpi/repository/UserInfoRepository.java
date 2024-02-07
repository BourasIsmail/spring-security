package ma.entraide.glpi.repository;

import ma.entraide.glpi.entity.UserInfo;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {

    Optional<UserInfo> findByName(String name);

    Optional<UserInfo> findByEmail(String email);

    Optional<UserInfo> findByDivDuService(String divDuService);

    @Query("SELECT u FROM UserInfo u WHERE u.name = :name")
    public List<UserInfo> getUserByName(@Param("name")String name);

    @Query("SELECT u FROM UserInfo u WHERE u.divDuService = :divDuService")
    public List<UserInfo> getUserByDivision(@Param("divDuService")String divDuService);





}
