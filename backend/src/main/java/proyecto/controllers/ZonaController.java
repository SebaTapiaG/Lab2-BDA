package proyecto.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.entities.ZonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import proyecto.service.ZonaService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/zona")
public class ZonaController {

    private final ZonaService zonaService;

    public ZonaController(ZonaService zonaService){
        this.zonaService = zonaService;
    }


    @GetMapping("")
    public ResponseEntity<List<Object>> findAll(){
        return zonaService.findAll();
    }

    @GetMapping("/{id_zona}")
    public ResponseEntity<Object> findById(@PathVariable int id_zona) {
        return zonaService.findById(id_zona);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Object>> findByEstado(@PathVariable String estado){
        return zonaService.findByEstado(estado);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ZonaEntity zona){
        return zonaService.create(zona);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody ZonaEntity zona){
        return zonaService.update(zona);
    }

    @DeleteMapping("/delete/{id_zona}")
    public ResponseEntity<Object> delete(@PathVariable int id_zona){
        return zonaService.delete(id_zona);
    }
}
