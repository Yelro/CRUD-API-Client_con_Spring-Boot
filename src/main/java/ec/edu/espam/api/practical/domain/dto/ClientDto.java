package ec.edu.espam.api.practical.domain.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto extends PersonDto{
    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private Boolean state;
}
