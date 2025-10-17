package ie.atu.passengerservice;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/passengers")
public class PassengerController {
    private final PassengerServiceApplication service;

    public PassengerController(PassengerServiceApplication service) {
        this.service = service;
    }

    @GetMapping
    public List<Passenger> getAll() {
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
                .created(URI.create("/passengers" + created.getPassengerId()))
                .body(created);
    }
}
