package ec.edu.espam.api.practical.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client extends Person{

    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private Boolean state;

}