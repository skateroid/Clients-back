package com.example.clientsDB.resource;

import com.example.clientsDB.dto.ClientChangeRequest;
import com.example.clientsDB.model.Client;
import com.example.clientsDB.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@Api(tags = "client", description = "Клиенты")
public class ClientResource {
    private ClientService clientService;

    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @ApiOperation("Получить список всех клиентов")
    public List<ClientChangeRequest> getAll() {
        return clientService.getAll();
    }

    @GetMapping("{id}")
    @ApiOperation("Получить клиента по id")
    public ClientChangeRequest getClientById(@PathVariable(name = "id") Long id) {
        return clientService.getClientById(id);
    }

    @GetMapping("/search")
    @ApiOperation("Получить клиента по имени и фамилии")
    public List<ClientChangeRequest> getClientByFullName(@RequestParam(name = "fullName") String fullName) {
        return clientService.getClientByFullName(fullName);
    }

    @PostMapping
    @ApiOperation("Создать клиента")
    public ClientChangeRequest create(@Valid @RequestBody ClientChangeRequest clientChangeRequest) {
        return clientService.createClient(clientChangeRequest);
    }

    @PutMapping("{clientId}")
    @ApiOperation("Изменить существующего клиента")
    public ClientChangeRequest update(@PathVariable("clientId") Long id,
                       @RequestBody ClientChangeRequest request) {
        return clientService.updateClient(id, request);
    }

    @DeleteMapping("{clientId}")
    @ApiOperation("Удалить клиента")
    public void delete(@PathVariable("clientId") Long id) {
        clientService.deleteClient(id);
    }
}
