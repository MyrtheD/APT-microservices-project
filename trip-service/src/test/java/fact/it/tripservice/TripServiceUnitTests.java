package fact.it.tripservice;

import fact.it.tripservice.dto.TripRequest;
import fact.it.tripservice.dto.TripResponse;
import fact.it.tripservice.model.Trip;
import fact.it.tripservice.repository.TripRepository;
import fact.it.tripservice.service.TripService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripServiceUnitTests {

    @InjectMocks
    private TripService tripService;

    @Mock
    private TripRepository tripRepository;

    @Test
    public void testGetAllTrips() {
        // Arrange
        Trip trip1 = Trip.builder()
                .id("1")
                .numberOfDays(6)
                .destination("Spain")
                .hotel("Beach Hotel")
                .numberOfPlaces(50)
                .numberOfPlacesAvailable(50)
                .price(BigDecimal.valueOf(349.99))
                .departureDate(DateFormat.getTimeInstance(10-7-2025))
                .returnDate(DateFormat.getTimeInstance(16-7-2025))
                .build();

        Trip trip2 = Trip.builder()
                .id("2")
                .numberOfDays(10)
                .destination("Hungary")
                .hotel("Green Bean Hotel")
                .numberOfPlaces(23)
                .numberOfPlacesAvailable(23)
                .price(BigDecimal.valueOf(479.99))
                .departureDate(DateFormat.getTimeInstance(10-8-2025))
                .returnDate(DateFormat.getTimeInstance(20-8-2025))
                .build();

        when(tripRepository.findAll()).thenReturn(Arrays.asList(trip1, trip2));

        // Act
        List<TripResponse> trips = tripService.getAllTrips();

        // Assert
        assertEquals(2, trips.size());
        assertEquals("Spain", trips.get(0).getDestination());
        assertEquals("Hungary", trips.get(1).getDestination());
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateTrip() {
        // Arrange
        String tripId = "1";
        Trip existingTrip = Trip.builder()
                .id(tripId)
                .numberOfDays(6)
                .destination("Spain")
                .hotel("Beach Hotel")
                .numberOfPlaces(50)
                .numberOfPlacesAvailable(50)
                .price(BigDecimal.valueOf(349.99))
                .departureDate(DateFormat.getTimeInstance(10-7-2025))
                .returnDate(DateFormat.getTimeInstance(16-7-2025))
                .build();

        TripRequest updateRequest = TripRequest.builder()
                .numberOfDays(7)
                .destination("Portugal")
                .hotel("Luxury Beach Hotel")
                .numberOfPlaces(60)
                .numberOfPlacesAvailable(60)
                .price(BigDecimal.valueOf(399.99))
                .departureDate(DateFormat.getTimeInstance(1-9-2025))
                .returnDate(DateFormat.getTimeInstance(8-9-2025))                .build();

        Trip updatedTrip = Trip.builder()
                .id(tripId)
                .numberOfDays(7)
                .destination("Portugal")
                .hotel("Luxury Beach Hotel")
                .numberOfPlaces(60)
                .numberOfPlacesAvailable(60)
                .price(BigDecimal.valueOf(399.99))
                .departureDate(DateFormat.getTimeInstance(1-9-2025))
                .returnDate(DateFormat.getTimeInstance(8-9-2025))
                .build();

        when(tripRepository.findById(tripId)).thenReturn(java.util.Optional.of(existingTrip));
        when(tripRepository.save(any(Trip.class))).thenReturn(updatedTrip);

        // Act
        TripResponse response = tripService.updateTrip(tripId, updateRequest);

        // Assert
        assertEquals("Portugal", response.getDestination());
        assertEquals("Luxury Beach Hotel", response.getHotel());
        assertEquals(60, response.getNumberOfPlaces());
        assertEquals(BigDecimal.valueOf(399.99), response.getPrice());
        verify(tripRepository, times(1)).findById(tripId);
        verify(tripRepository, times(1)).save(any(Trip.class));
    }
}
