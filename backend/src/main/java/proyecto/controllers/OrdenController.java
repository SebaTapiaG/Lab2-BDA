package proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyecto.entities.OrdenEntity;
import proyecto.service.ClienteService;
import proyecto.service.OrdenService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/orden")
public class OrdenController {

   private final OrdenService ordenService;
   public OrdenController(OrdenService ordenService) {
       this.ordenService = ordenService;
    }

    @Autowired
    ClienteService clienteService;

    @GetMapping("")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(ordenService.findAll());
    }

    @GetMapping("/{id_orden}")
    public ResponseEntity<?> findById(int id_orden) {
        return ResponseEntity.ok().body(ordenService.findById(id_orden));
    }

    @GetMapping("/create")
    public ResponseEntity<?> create(OrdenEntity orden) {
        return ResponseEntity.ok().body(ordenService.create(orden));
    }

    @GetMapping("/update")
    public ResponseEntity<?> update(OrdenEntity orden) {
        return ResponseEntity.ok().body(ordenService.update(orden));
    }

    @GetMapping("/delete")
    public ResponseEntity<?> delete(int id_orden) {
        return ResponseEntity.ok().body(ordenService.delete(id_orden));
    }

    @GetMapping("/findByCliente")
    public ResponseEntity<?> findByCliente(int id_cliente) {
        return ResponseEntity.ok().body(ordenService.findByCliente(id_cliente));
    }
}
