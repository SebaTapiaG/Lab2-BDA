package proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyecto.service.CategoriaService;
import proyecto.entities.CategoriaEntity;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("")

    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(categoriaService.findAll());
    }

    @GetMapping("/{id_categoria}")
    public ResponseEntity<?> findById(int id_categoria) {
        return ResponseEntity.ok().body(categoriaService.findById(id_categoria));
    }

    @GetMapping("/create")
    public ResponseEntity<?> create(CategoriaEntity categoria) {
        return ResponseEntity.ok().body(categoriaService.create(categoria));
    }

    @GetMapping("/update")
    public ResponseEntity<?> update(CategoriaEntity categoria) {
        return ResponseEntity.ok().body(categoriaService.update(categoria));
    }

    @GetMapping("/delete")
    public ResponseEntity<?> delete(int id_categoria) {
        return ResponseEntity.ok().body(categoriaService.delete(id_categoria));
    }



}
