package ma.entraide.glpi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue
    @Column(name = "ticketId")
    private Long id;

    private String nomAuteur;

    private Boolean etat;

    private String message;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserInfo userInfo;
}
