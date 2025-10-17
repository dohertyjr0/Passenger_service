package ie.atu.passengerservice.controller;

import ie.atu.passengerservice.model.Passenger;
import ie.atu.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {
    private final PassengerService service;

    public PassengerController(PassengerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Passenger>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getById(@PathVariable String id) {
        Optional<Passenger> maybe = service.findById(id);
        if(maybe.isPresent()) {
            return ResponseEntity.ok(maybe.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Passenger> create(@Valid @RequestBody Passenger p) {
        Passenger created = service.create(p);
        return ResponseEntity
                .created(URI.create("api/passengers" + created.getPassengerId()))
                .body(created);
    }

    @PutMapping
    public ResponseEntity<Passenger> update(@PathVariable String id, @Valid @RequestBody Passenger p) {
        Optional<Passenger> maybe = service.findById(p.getPassengerId());
        if(maybe.isPresent()) {
            Passenger updated = maybe.get();
        }
        else{
            return ResponseEntity.notFound().build();
        }
        return null;
    }
}
