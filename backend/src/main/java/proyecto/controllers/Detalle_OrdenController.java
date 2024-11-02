package proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyecto.entities.Detalle_OrdenEntity;
import proyecto.service.Detalle_OrdenService;
import proyecto.service.OrdenService;
import proyecto.service.ProductoService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/detalle_orden")
public class Detalle_OrdenController {

    @Autowired
    Detalle_OrdenService detalle_ordenService;

    @Autowired
    OrdenService ordenService;

    @Autowired
    ProductoService productoService;

    @GetMapping("")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(detalle_ordenService.findAll());
    }

    @GetMapping("/{id_detalle_orden}")
    public ResponseEntity<?> findById(int id_detalle_orden) {
        return ResponseEntity.ok().body(detalle_ordenService.findById(id_detalle_orden));
    }

    @GetMapping("/create")
    public ResponseEntity<?> create(Detalle_OrdenEntity detalle_orden) {
        return ResponseEntity.ok().body(detalle_ordenService.create(detalle_orden));
    }

    @GetMapping("/update")
    public ResponseEntity<?> update(Detalle_OrdenEntity detalle_orden) {
        return ResponseEntity.ok().body(detalle_ordenService.update(detalle_orden));
    }

    @GetMapping("/delete")
    public ResponseEntity<?> delete(int id_detalle_orden) {
        return ResponseEntity.ok().body(detalle_ordenService.delete(id_detalle_orden));
    }

}
