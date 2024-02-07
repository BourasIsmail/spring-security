package ma.entraide.glpi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Integer id;

    private String name;

    @Column(unique=true)
    private String email;

    private String roles;

    private String password;

    private String divDuService;

    @OneToMany(mappedBy = "userInfo")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "userInfo")
    private List<Materiel> materiels;
}
