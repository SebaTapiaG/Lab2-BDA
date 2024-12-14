package proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.entities.RepartidorEntity;
import proyecto.service.RepartidorService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/repartidor")
public class RepartidorController {
    @Autowired
    private RepartidorService repartidorService;

    @GetMapping("")
    public ResponseEntity<List<Object>> findAll(){
        return repartidorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id){
        return repartidorService.findById(id);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Object> getByEmail(@PathVariable String email){
        return repartidorService.findByEmail(email);
    }

    @GetMapping("/almacen/{id}")
    public ResponseEntity<List<RepartidorEntity>> findByAlmacen(@PathVariable Integer id){
        return repartidorService.findByAlmacen(id);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody RepartidorEntity repartidor){
        return repartidorService.update(repartidor);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id){
        return repartidorService.delete(id);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RepartidorEntity repartidor){
        return repartidorService.register(repartidor);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody RepartidorEntity repartidor){
        return repartidorService.login(repartidor);
    }

}
