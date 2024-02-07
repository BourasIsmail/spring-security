package ma.entraide.glpi.repository;

import ma.entraide.glpi.entity.Materiel;
import ma.entraide.glpi.entity.Ticket;
import ma.entraide.glpi.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT u FROM Ticket u WHERE u.nomAuteur = :nomAuteur")
    public List<Ticket> getTicketByNomAuteur(@Param("nomAuteur")String nomAuteur);

    @Query("SELECT u FROM Ticket u WHERE u.etat = :etat")
    public List<Ticket> getTicketByEtat(@Param("etat")Boolean etat);

    @Query("SELECT u FROM Ticket u WHERE u.userInfo.name = :name")
    public List<Ticket> getTicketByUserName(@Param("name")String name);

}
