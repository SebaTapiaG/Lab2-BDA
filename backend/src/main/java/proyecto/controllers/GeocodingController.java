package proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.service.GeocodingService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/geocoding")
public class GeocodingController {

    @Autowired
    private GeocodingService geocodingService;

    @GetMapping("/obtenerAltitud/{direccion}/{comuna}")
    public ResponseEntity<Double> geocodeAddressAltitud(@PathVariable String direccion,
                                                        @PathVariable String comuna) {
        return ResponseEntity.ok().body(geocodingService.geocodeAddressAltitud(direccion, comuna));
    }

    @GetMapping("/obtenerLongitud/{direccion}/{comuna}")
    public ResponseEntity<Double> geocodeAddressLongitud(@PathVariable String direccion,
                                                         @PathVariable String comuna) {
        return ResponseEntity.ok().body(geocodingService.geocodeAddressLongitud(direccion, comuna));
    }
}
