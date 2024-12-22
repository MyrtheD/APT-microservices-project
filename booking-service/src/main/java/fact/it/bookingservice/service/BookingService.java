package fact.it.bookingservice.service;

import fact.it.bookingservice.dto.BookingRequest;
import fact.it.bookingservice.dto.BookingResponse;
import fact.it.bookingservice.dto.SingleBookingDto;
import fact.it.bookingservice.dto.TripResponse;
import fact.it.bookingservice.model.Booking;
import fact.it.bookingservice.model.SingleBooking;
import fact.it.bookingservice.repository.BookingRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;
    private final WebClient webClient;

    @Value("${tripservice.baseurl}")
    private String tripServiceBaseUrl;

    @Value("${travelerservice.baseurl}")
    private String travelerServiceBaseUrl;

    public boolean deleteBooking(String bookingNumber) {
        // Fetch the booking by bookingNumber
        Booking booking = bookingRepository.findByBookingNumber(bookingNumber)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found for booking number: " + bookingNumber));

        // Iterate over the single bookings to update trip availability
        for (SingleBooking singleBooking : booking.getSingleBookingsList()) {
            // Fetch the trip details
            TripResponse tripResponse = webClient.get()
                    .uri("http://" + tripServiceBaseUrl + "/api/trip", uriBuilder -> uriBuilder.queryParam("id", singleBooking.getTripId()).build())
                    .retrieve()
                    .bodyToMono(TripResponse.class)
                    .block();

            if (tripResponse == null) {
                throw new IllegalArgumentException("Trip not found for trip ID: " + singleBooking.getTripId());
            }

            // Adjust the number of available seats by adding back the people who were previously booked
            tripResponse.setNumberOfPlacesAvailable(
                    tripResponse.getNumberOfPlacesAvailable() + singleBooking.getNumberOfPeople()
            );

            // Update trip availability in the Trip Service
            webClient.put()
                    .uri("http://" + tripServiceBaseUrl + "/api/trip")
                    .bodyValue(tripResponse)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        }

        // Delete the booking from the repository
        bookingRepository.delete(booking);

        return true;
    }

    public boolean registerBooking(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setBookingNumber(UUID.randomUUID().toString());

        List<SingleBooking> singleBookings = bookingRequest.getSingleBookingDtoList()
                .stream()
                .map(singleBookingDto -> {
                    // Fetch trip details for each single booking
                    TripResponse tripResponse = webClient.get()
                            .uri("http://" + tripServiceBaseUrl + "/api/trip",
                                    uriBuilder -> uriBuilder.queryParam("id", singleBookingDto.getTripId()).build())
                            .retrieve()
                            .bodyToMono(TripResponse.class)
                            .block();

                    if (tripResponse == null || tripResponse.getNumberOfPlacesAvailable() < singleBookingDto.getNumberOfPeople()) {
                        throw new IllegalArgumentException("Not enough seats available for trip ID: " + singleBookingDto.getTripId());
                    }

                    // Adjust the number of available seats
                    tripResponse.setNumberOfPlacesAvailable(
                            tripResponse.getNumberOfPlacesAvailable() - singleBookingDto.getNumberOfPeople()
                    );

                    // Update trip availability in Trip Service
                    webClient.put()
                            .uri("http://" + tripServiceBaseUrl + "/api/trip")
                            .bodyValue(tripResponse)
                            .retrieve()
                            .toBodilessEntity()
                            .block();

                    return mapToSingleBooking(singleBookingDto);
                })
                .toList();

        booking.setSingleBookingsList(singleBookings);

        // Save the booking to the database
        bookingRepository.save(booking);

        return true;
    }

    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream()
                .map(booking -> new BookingResponse(
                        booking.getBookingNumber(),
                        mapToSingleBookingsDto(booking.getSingleBookingsList())
                ))
                .collect(Collectors.toList());
    }

    private SingleBooking mapToSingleBooking(SingleBookingDto singleBookingDto) {
        SingleBooking singleBooking = new SingleBooking();
        singleBooking.setTripId(singleBookingDto.getTripId());
        singleBooking.setNumberOfPeople(singleBookingDto.getNumberOfPeople());
        singleBooking.setTravelerId(singleBookingDto.getTravelerId());
        singleBooking.setTotalPrice(singleBookingDto.getTotalPrice());
        return singleBooking;
    }

    private List<SingleBookingDto> mapToSingleBookingsDto(List<SingleBooking> singleBookings) {
        return singleBookings.stream()
                .map(singleBooking -> new SingleBookingDto(
                        singleBooking.getId(),
                        singleBooking.getTripId(),
                        singleBooking.getNumberOfPeople(),
                        singleBooking.getTravelerId(),
                        singleBooking.getTotalPrice()
                ))
                .collect(Collectors.toList());
    }
}
