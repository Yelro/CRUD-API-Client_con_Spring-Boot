package ec.edu.espam.api.practical.service.impl;

import ec.edu.espam.api.practical.domain.Client;
import ec.edu.espam.api.practical.domain.dto.ClientDto;
import ec.edu.espam.api.practical.exceptions.EntityNotFoundException;
import ec.edu.espam.api.practical.repository.ClientRepository;
import ec.edu.espam.api.practical.service.ClientService;
import ec.edu.espam.api.practical.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Override
    public ClientDto create(ClientDto dto) {
        log.info("Init method create");
        Client client = convertDtoToEntity(dto);
        log.info("End method create");
        return convertEntityToDto(this.repository.save(client));
    }
    @Override
    public List<ClientDto> getAll() {
        return repository.findAll().stream().map(this::convertEntityToDto).toList();
    }
    @Override
    public ClientDto getById(long id) {
        return convertEntityToDto(getEntityById(id));
    }
    @Override
    public ClientDto update(Long id, ClientDto dto) {
        Client client = getEntityById(id);
        client.setAge(dto.getAge());
        client.setPassword(dto.getPassword());
        client.setAddress(dto.getAddress());
        client.setState(dto.getState());
        client.setDni(dto.getDni());
        client.setName(dto.getName());
        client.setPhone(dto.getPhone());
        client.setGender(dto.getGender());
        return convertEntityToDto(repository.save(client));
    }
    @Override
    public void delete(Long id) {
        Client client = getEntityById(id);
        repository.delete(client);
    }
    @Override
    public Client getEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }
    private ClientDto convertEntityToDto(Client client) {
        return Mapper.modelMapper().map(client, ClientDto.class);
    }
    private Client convertDtoToEntity(ClientDto dto) {
        return Mapper.modelMapper().map(dto, Client.class);
    }
}
