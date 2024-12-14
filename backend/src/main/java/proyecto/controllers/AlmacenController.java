package proyecto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.entities.AlmacenEntity;
import proyecto.service.AlmacenService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/almacen")
public class AlmacenController {

    private final AlmacenService almacenService;

    public AlmacenController(AlmacenService almacenService) {
        this.almacenService = almacenService;
    }

    @GetMapping("")
    public ResponseEntity<List<Object>> findAll(){
        return almacenService.findAll();
    }

    @GetMapping("/{id_almacen}")
    public ResponseEntity<Object> findById(int id_almacen){
        return almacenService.findById(id_almacen);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(AlmacenEntity almacen){
        return almacenService.create(almacen);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(AlmacenEntity almacen){
        return almacenService.update(almacen);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(int id_almacen){
        return almacenService.delete(id_almacen);
    }


}
