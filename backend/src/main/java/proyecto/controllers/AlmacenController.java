package proyecto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/create")
    public ResponseEntity<Object> create(AlmacenEntity almacen){
        return almacenService.create(almacen);
    }

    @GetMapping("/update")
    public ResponseEntity<Object> update(AlmacenEntity almacen){
        return almacenService.update(almacen);
    }

    @GetMapping("/delete")
    public ResponseEntity<Object> delete(int id_almacen){
        return almacenService.delete(id_almacen);
    }


}
