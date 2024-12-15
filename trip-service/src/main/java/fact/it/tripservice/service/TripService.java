package fact.it.tripservice.service;

import fact.it.tripservice.dto.TripRequest;
import fact.it.tripservice.dto.TripResponse;
import fact.it.tripservice.model.Trip;
import fact.it.tripservice.repository.TripRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;

    @PostConstruct
    public void loadData() {
        if(tripRepository.count() <= 0){
            Trip trip = Trip.builder()
                    .numberOfDays(6)
                    .destination("Spain")
                    .hotel("Beach Hotel")
                    .numberOfPlaces(50)
                    .numberOfPlacesAvailable(50)
                    .price(BigDecimal.valueOf(349.99))
                    .departureDate(DateFormat.getTimeInstance(10-7-2025))
                    .returnDate(DateFormat.getTimeInstance(16- 7 -2025))
                    .build();

            Trip trip1 = Trip.builder()
                    .numberOfDays(10)
                    .destination("Hungary")
                    .hotel("Green Bean Hotel")
                    .numberOfPlaces(23)
                    .numberOfPlacesAvailable(23)
                    .price(BigDecimal.valueOf(479.99))
                    .departureDate(DateFormat.getTimeInstance(10-8-2025))
                    .returnDate(DateFormat.getTimeInstance(20-8-2025))
                    .build();

            tripRepository.save(trip);
            tripRepository.save(trip1);
        }
    }

    public void createTrip(TripRequest tripRequest){
        Trip trip = Trip.builder()
                .numberOfDays(tripRequest.getNumberOfDays())
                .destination(tripRequest.getDestination())
                .hotel(tripRequest.getHotel())
                .numberOfPlaces(tripRequest.getNumberOfPlaces())
                .numberOfPlacesAvailable(tripRequest.getNumberOfPlacesAvailable())
                .price(tripRequest.getPrice())
                .departureDate(tripRequest.getDepartureDate())
                .returnDate(tripRequest.getReturnDate())
                .build();

        tripRepository.save(trip);
    }

    public List<TripResponse> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();

        return trips.stream().map(this::mapToTripResponse).toList();
    }

    public List<TripResponse> getAllTripsById(List<String> id) {
        List<Trip> trips = tripRepository.findByIdIn(id);

        return trips.stream().map(this::mapToTripResponse).toList();
    }

    private TripResponse mapToTripResponse(Trip trip) {
        return TripResponse.builder()
                .id(trip.getId())
                .numberOfDays(trip.getNumberOfDays())
                .destination(trip.getDestination())
                .hotel(trip.getHotel())
                .numberOfPlaces(trip.getNumberOfPlaces())
                .numberOfPlacesAvailable(trip.getNumberOfPlacesAvailable())
                .price(trip.getPrice())
                .departureDate(trip.getDepartureDate())
                .returnDate(trip.getReturnDate())
                .build();
    }
}
