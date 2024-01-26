package ec.edu.espam.api.practical.service;

import ec.edu.espam.api.practical.domain.Client;
import ec.edu.espam.api.practical.domain.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto create(ClientDto client);
    List<ClientDto> getAll();
    ClientDto getById(long id);
    ClientDto update(Long id, ClientDto dto);
    void delete(Long id);
    Client getEntityById(Long id);
}
