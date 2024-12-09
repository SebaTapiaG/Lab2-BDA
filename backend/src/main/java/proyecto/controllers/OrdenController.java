package proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.entities.OrdenEntity;
import proyecto.service.ClienteService;
import proyecto.service.OrdenService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/orden")
public class OrdenController {

   private final OrdenService ordenService;
   public OrdenController(OrdenService ordenService) {
       this.ordenService = ordenService;
    }

    @GetMapping("")
    public ResponseEntity<List<Object>> findAll(){
        return ordenService.findAll();
    }

    @GetMapping("/{id_orden}")
    public ResponseEntity<?> findById(@PathVariable int id_orden) {
        return ordenService.findById(id_orden);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody OrdenEntity orden) {
       System.out.println(orden);
       return ordenService.create(orden);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody OrdenEntity orden) {
        return ordenService.update(orden);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(int id_orden) {
        return ordenService.delete(id_orden);
    }

    @GetMapping("/findByCliente/{id_cliente}")
    public ResponseEntity<List<Object>> findByCliente(@PathVariable int id_cliente) {
        return ordenService.findByCliente(id_cliente);
    }
}
