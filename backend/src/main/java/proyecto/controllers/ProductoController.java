package proyecto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.entities.ProductoEntity;
import proyecto.service.ProductoService;
import proyecto.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/producto")

public class ProductoController {

    private final ProductoService productoService;
    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }


    @GetMapping("")
    public ResponseEntity<List<Object>> findAll(){
        return productoService.findAll();
    }

    @GetMapping("/producto/{id_categoria}")
    public ResponseEntity<?> findByCategoria(@PathVariable int id_categoria){
        return productoService.findByCategoria(id_categoria);
    }

    @GetMapping("/producto/{id_producto}")
    public ResponseEntity<Object> findById(@PathVariable int id_producto) {
        return productoService.findById(id_producto);

    }

    @PostMapping("/producto/create")
    public ResponseEntity<Object> create(@RequestBody ProductoEntity producto){
        return productoService.create(producto);
    }

    @PutMapping("/producto/update")
    public ResponseEntity<Object> update(@RequestBody ProductoEntity producto){
        return productoService.update(producto);
    }

    @DeleteMapping("/producto/delete/{id_producto}")
    public ResponseEntity<Object> delete(@PathVariable int id_producto){
        return productoService.delete(id_producto);
    }


}
