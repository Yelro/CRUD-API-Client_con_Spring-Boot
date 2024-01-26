package ec.edu.espam.api.practical.controller;

import ec.edu.espam.api.practical.domain.dto.ClientDto;
import ec.edu.espam.api.practical.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/clients")
@Slf4j
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody @Valid ClientDto client) {
        return new ResponseEntity<>(clientService.create(client), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable Long id) {
        log.info("Get By Id {}", id);
        return ResponseEntity.ok(clientService.getById(id));
    }
    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll() {
        return ResponseEntity.ok(clientService.getAll());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Long id, @Valid @RequestBody ClientDto dto) {
        return ResponseEntity.ok(clientService.update(id, dto));
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }
}

