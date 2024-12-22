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
            Trip trip1 = Trip.builder()
                    .numberOfDays(6)
                    .destination("Spain")
                    .hotel("Beach Hotel")
                    .numberOfPlaces(50)
                    .numberOfPlacesAvailable(50)
                    .price(BigDecimal.valueOf(349.99))
                    .departureDate(DateFormat.getTimeInstance(10-7-2025))
                    .returnDate(DateFormat.getTimeInstance(16- 7 -2025))
                    .build();

            Trip trip2 = Trip.builder()
                    .numberOfDays(10)
                    .destination("Hungary")
                    .hotel("Green Bean Hotel")
                    .numberOfPlaces(23)
                    .numberOfPlacesAvailable(23)
                    .price(BigDecimal.valueOf(479.99))
                    .departureDate(DateFormat.getTimeInstance(10-8-2025))
                    .returnDate(DateFormat.getTimeInstance(20-8-2025))
                    .build();

            Trip trip3 = Trip.builder()
                    .numberOfDays(7)
                    .destination("Italy")
                    .hotel("Villa Paradise")
                    .numberOfPlaces(30)
                    .numberOfPlacesAvailable(30)
                    .price(BigDecimal.valueOf(399.99))
                    .departureDate(DateFormat.getTimeInstance(15-6-2025))
                    .returnDate(DateFormat.getTimeInstance(22-6-2025))
                    .build();

            Trip trip4 = Trip.builder()
                    .numberOfDays(5)
                    .destination("France")
                    .hotel("Luxe Paris Hotel")
                    .numberOfPlaces(40)
                    .numberOfPlacesAvailable(40)
                    .price(BigDecimal.valueOf(299.99))
                    .departureDate(DateFormat.getTimeInstance(20-5-2025))
                    .returnDate(DateFormat.getTimeInstance(25-5-2025))
                    .build();

            Trip trip5 = Trip.builder()
                    .numberOfDays(8)
                    .destination("Germany")
                    .hotel("Berlin Comfort Inn")
                    .numberOfPlaces(35)
                    .numberOfPlacesAvailable(35)
                    .price(BigDecimal.valueOf(359.99))
                    .departureDate(DateFormat.getTimeInstance(1-9-2025))
                    .returnDate(DateFormat.getTimeInstance(9-9-2025))
                    .build();

            Trip trip6 = Trip.builder()
                    .numberOfDays(12)
                    .destination("Greece")
                    .hotel("Santorini Dream")
                    .numberOfPlaces(20)
                    .numberOfPlacesAvailable(20)
                    .price(BigDecimal.valueOf(499.99))
                    .departureDate(DateFormat.getTimeInstance(25-7-2025))
                    .returnDate(DateFormat.getTimeInstance(6-8-2025))
                    .build();

            Trip trip7 = Trip.builder()
                    .numberOfDays(14)
                    .destination("Australia")
                    .hotel("Sydney Opera Hotel")
                    .numberOfPlaces(10)
                    .numberOfPlacesAvailable(10)
                    .price(BigDecimal.valueOf(1599.99))
                    .departureDate(DateFormat.getTimeInstance(1-12-2025))
                    .returnDate(DateFormat.getTimeInstance(15-12-2025))
                    .build();

            tripRepository.saveAll(List.of(trip1, trip2, trip3, trip4, trip5, trip6, trip7));
        }
    }


    public List<TripResponse> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();

        return trips.stream().map(this::mapToTripResponse).toList();
    }

    public List<TripResponse> getAllTripsById(List<String> id) {
        List<Trip> trips = tripRepository.findByIdIn(id);

        return trips.stream().map(this::mapToTripResponse).toList();
    }

    public TripResponse updateTrip(String id, TripRequest tripRequest) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trip with ID " + id + " not found"));

        // Update the trip fields
        trip.setNumberOfDays(tripRequest.getNumberOfDays());
        trip.setDestination(tripRequest.getDestination());
        trip.setHotel(tripRequest.getHotel());
        trip.setNumberOfPlaces(tripRequest.getNumberOfPlaces());
        trip.setNumberOfPlacesAvailable(tripRequest.getNumberOfPlacesAvailable());
        trip.setPrice(tripRequest.getPrice());
        trip.setDepartureDate(tripRequest.getDepartureDate());
        trip.setReturnDate(tripRequest.getReturnDate());

        // Save the updated trip
        Trip updatedTrip = tripRepository.save(trip);

        return mapToTripResponse(updatedTrip);
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
