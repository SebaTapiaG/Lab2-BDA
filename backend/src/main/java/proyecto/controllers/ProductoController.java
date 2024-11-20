package proyecto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import proyecto.service.ProductoService;
import proyecto.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/producto")

public class ProductoController {

    private final ProductoService productoService;
    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }


    @GetMapping("")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(productoService.findAll());
    }

    @GetMapping("/categoria/{id_categoria}")
    public ResponseEntity<?> findByCategoria(int id_categoria){
        return ResponseEntity.ok().body(productoService.findByCategoria(id_categoria));
    }

    @GetMapping("/{id_producto}")
    public ResponseEntity<?> findById(int id_producto) {
        return ResponseEntity.ok().body(productoService.findById(id_producto));


    }


}
