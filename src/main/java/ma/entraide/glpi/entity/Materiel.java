package ma.entraide.glpi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Materiel {
    @Id
    @GeneratedValue
    @Column(name = "materielId")
    private Long id;

    @Column(unique = true)
    private String marqueEtRef;

    private String type;

    private String etat;

    private Boolean dispo;

    private String dateAchat;

    private String dateAffectation;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserInfo userInfo;

}
