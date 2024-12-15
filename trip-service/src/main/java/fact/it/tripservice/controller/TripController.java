package fact.it.tripservice.controller;

import fact.it.tripservice.dto.TripRequest;
import fact.it.tripservice.dto.TripResponse;
import fact.it.tripservice.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createTrip
            (@RequestBody TripRequest tripRequest) {
        tripService.createTrip(tripRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TripResponse> getAllTripsById
            (@RequestParam List<String> id) {
        return tripService.getAllTripsById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TripResponse> getAllTrips() {
        return tripService.getAllTrips();
    }

}
