package proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.entities.Detalle_OrdenEntity;
import proyecto.service.Detalle_OrdenService;
import proyecto.service.OrdenService;
import proyecto.service.ProductoService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/detalle_orden")
public class Detalle_OrdenController {

    private final Detalle_OrdenService detalle_ordenService;
    public Detalle_OrdenController(Detalle_OrdenService detalle_ordenService) {
        this.detalle_ordenService = detalle_ordenService;
    }

    @GetMapping("")
    public ResponseEntity<List<Object>> findAll(){
        return detalle_ordenService.findAll();
    }

    @GetMapping("/{id_detalle_orden}")
    public ResponseEntity<Object> findById(@PathVariable int id_detalle_orden) {
        return detalle_ordenService.findById(id_detalle_orden);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Detalle_OrdenEntity detalle_orden) {
        return detalle_ordenService.create(detalle_orden);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Detalle_OrdenEntity detalle_orden) {
        return detalle_ordenService.update(detalle_orden);
    }

    @DeleteMapping("/delete/{id_detalle_orden}")
    public ResponseEntity<Object> delete(@PathVariable int id_detalle_orden) {
        return ResponseEntity.ok().body(detalle_ordenService.delete(id_detalle_orden));
    }

    @GetMapping("/productos/{id_orden}")
    public ResponseEntity<List<Object>> findProductosByOrdenId(@PathVariable("id_orden") Integer id_orden) {
        return detalle_ordenService.findProductosByOrdenId(id_orden);
    }

}
