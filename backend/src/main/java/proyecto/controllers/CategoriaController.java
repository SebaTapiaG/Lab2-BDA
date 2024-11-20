package proyecto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.service.CategoriaService;
import proyecto.entities.CategoriaEntity;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categoria")
public class CategoriaController {

   private final CategoriaService categoriaService;
   public CategoriaController(CategoriaService categoriaService) {
       this.categoriaService = categoriaService;
    }

    @GetMapping("/categoria")

    public ResponseEntity<List<Object>> findAll(){
        return categoriaService.findAll();
    }

    @GetMapping("/categoria/{id_categoria}")
    public ResponseEntity<Object> findById(@PathVariable int id_categoria) {
        return categoriaService.findById(id_categoria);
    }

    @GetMapping("/categoria/create")
    public ResponseEntity<Object> create(@RequestBody CategoriaEntity categoria) {
        return categoriaService.create(categoria);
    }

    @GetMapping("/categoria")
    public ResponseEntity<Object> update(@RequestBody CategoriaEntity categoria) {
        return categoriaService.update(categoria);
    }

    @GetMapping("/categoria/{id_categoria}")
    public ResponseEntity<Object> delete(@PathVariable int id_categoria) {
        return categoriaService.delete(id_categoria);
    }



}
