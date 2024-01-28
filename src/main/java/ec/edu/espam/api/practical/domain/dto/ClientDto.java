package ec.edu.espam.api.practical.domain.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto extends PersonDto{
    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private Boolean state;
}
