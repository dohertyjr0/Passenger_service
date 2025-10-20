package ie.atu.passengerservice.service;

import ie.atu.passengerservice.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PassengerService {

    private final List<Passenger> store = new ArrayList<>();

    public List<Passenger> findAll() {
        return new ArrayList<>(store);
    }

    public Optional<Passenger> findById(String id) {
        for (Passenger p : store) {
            if(p.getPassengerId().equals(id)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public Passenger create(Passenger p) {
        if(findById(p.getPassengerId()).isPresent()) {
            throw new IllegalArgumentException("passenger already exists");
        }
        store.add(p);
        return p;
    }

    public Passenger update(Passenger p) {
        for(int i = 0; i < store.size(); i++) {
            if(store.get(i).getPassengerId().equals(p.getPassengerId())) {
                store.set(i, p);
                return p;
            }
        }
        throw new NoSuchElementException("passenger not found");
    }

    public boolean deleteById(String id) {
            return store.removeIf(p -> p.getPassengerId().equals(id));
    }
}
