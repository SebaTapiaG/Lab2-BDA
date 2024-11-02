package proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyecto.entities.ClienteEntity;
import proyecto.service.ClienteService;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(clienteService.findAll());
    }

    @GetMapping("/{id_cliente}")
    public ResponseEntity<?> findById(int id_cliente) {
        return ResponseEntity.ok().body(clienteService.findById(id_cliente));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(String email) {
        return ResponseEntity.ok().body(clienteService.findByEmail(email));
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(ClienteEntity cliente) {
        return ResponseEntity.ok().body(clienteService.login(cliente));
    }

    @GetMapping("/register")
    public ResponseEntity<?> register(ClienteEntity cliente) {
        return ResponseEntity.ok().body(clienteService.register(cliente));
    }

    @GetMapping("/update")
    public ResponseEntity<?> update(ClienteEntity cliente) {
        return ResponseEntity.ok().body(clienteService.update(cliente));
    }

    @GetMapping("/delete")

    public ResponseEntity<?> delete(int id_cliente) {
        return ResponseEntity.ok().body(clienteService.delete(id_cliente));
    }

    @GetMapping("/getCliente")
    public ResponseEntity<?> getCliente(String token) {
        return ResponseEntity.ok().body(clienteService.getCliente(token));
    }




}
