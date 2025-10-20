package ie.atu.passengerservice;

import ie.atu.passengerservice.model.Passenger;
import ie.atu.passengerservice.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PassengerServiceTests {
    private PassengerService service;

    @BeforeEach
    void setUp() {
        service = new PassengerService();
    }

    @Test
    void createThenFindById() {
        Passenger p = Passenger.builder()
                .passengerId("P1")
                .name("Paul")
                .email("paul@atu.ie")
                .build();

        service.create(p);

        Optional<Passenger> found = service.findById("P1");
        assertTrue(found.isPresent());
        assertEquals("Paul", found.get().getName());
    }

    @Test
    void duplicateIdThrows(){
        service.create(Passenger.builder()
                .passengerId("P2")
                .name("Bob")
                .email("bob@ex.com")
                .build()
        );

        assertThrows(IllegalArgumentException.class, () ->
                service.create(Passenger.builder()
                        .passengerId("P2")
                        .name("Bobby")
                        .email("bob@ex.com")
                        .build()
                )
                );
    }

    @Test
    void updateMissing(){
        Passenger john = (Passenger.builder()
                .passengerId("P3")
                .name("John")
                .email("john@atu.ie")
                .build());

        assertThrows(NoSuchElementException.class, () -> service.update(john));
    }

    @Test
    void deleteExisiting(){
        boolean deleted = service.deleteById("P1");
        assertTrue(deleted);
        assertFalse(service.findById("P1").isPresent());
    }
}
