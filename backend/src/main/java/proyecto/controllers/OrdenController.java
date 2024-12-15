package proyecto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.dto.RepartidorDTO;
import proyecto.entities.OrdenEntity;
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

    @GetMapping("/pagadas")
    public ResponseEntity<List<Object>> findPagadas(){
        return ordenService.findPagadas();
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody OrdenEntity orden) {
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

    @PutMapping("/updateEstado/{id_orden}/{id_repartidor}/{estado}")
    public ResponseEntity<Object> updateEstado(@PathVariable int id_orden, @PathVariable String estado, @PathVariable int id_repartidor) {
        return ordenService.updateEstado(id_orden, estado, id_repartidor);
    }

    @GetMapping("/findDeliveryCompletedInArea/{id_zona}")
    public ResponseEntity<List<RepartidorDTO>> findDeliveryCompletedInArea(@PathVariable int id_zona){
        return ordenService.findDeliveryCompletedInArea(id_zona);
    }

}
