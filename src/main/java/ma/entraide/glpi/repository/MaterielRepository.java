package ma.entraide.glpi.repository;


import ma.entraide.glpi.entity.Materiel;
import ma.entraide.glpi.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterielRepository extends JpaRepository<Materiel,Long> ,
        PagingAndSortingRepository<Materiel, Long> {

    @Query("SELECT u FROM Materiel u WHERE u.userInfo.name = :name")
    public List<Materiel> getMaterielByUserName(@Param("name")String name);

    @Query("SELECT u FROM Materiel u WHERE u.userInfo.divDuService = :divDuService")
    public List<Materiel> getMaterielByDivDuService(@Param("divDuService")String divDuService);

    @Query("SELECT u FROM Materiel u WHERE u.etat = :etat")
    public List<Materiel> getMaterielByEtat(@Param("etat")String etat);

    @Query("SELECT u FROM Materiel u WHERE u.dispo = :dispo")
    public List<Materiel> getMaterielByDisponibilite(@Param("dispo")Boolean dispo);

    @Query("SELECT u FROM Materiel u WHERE u.type = :type")
    public List<Materiel> getMaterielByType(@Param("type")String type);

    Optional<Materiel> findMaterielByMarqueEtRef(String marqueRef);

    Page<Materiel> findByDispo(boolean dispo, Pageable pageable);




}
