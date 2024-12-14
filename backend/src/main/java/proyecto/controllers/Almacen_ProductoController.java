package proyecto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyecto.entities.Almacen_ProductoEntity;
import proyecto.service.Almacen_ProductoService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/almacen_producto")
public class Almacen_ProductoController {
    private final Almacen_ProductoService almacen_productoService;

    public Almacen_ProductoController(Almacen_ProductoService almacen_productoService) {
        this.almacen_productoService = almacen_productoService;
    }

    @GetMapping("")
    public ResponseEntity<List<Object>> findAll(){
        return almacen_productoService.findAll();
    }

    @GetMapping("/{id_almacen_producto}")
    public ResponseEntity<Object> findById(int id_almacen_producto){
        return almacen_productoService.findById(id_almacen_producto);
    }

    @GetMapping("/create")
    public ResponseEntity<Object> create(Almacen_ProductoEntity almacen_producto){
        return almacen_productoService.create(almacen_producto);
    }

    @GetMapping("/update")
    public ResponseEntity<Object> update(Almacen_ProductoEntity almacen_producto){
        return almacen_productoService.update(almacen_producto);
    }

    @GetMapping("/delete")
    public ResponseEntity<Object> delete(int id_almacen_producto){
        return almacen_productoService.delete(id_almacen_producto);
    }

}
