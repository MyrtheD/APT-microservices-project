package fact.it.bookingservice.service;

import fact.it.bookingservice.dto.BookingRequest;
import fact.it.bookingservice.dto.BookingResponse;
import fact.it.bookingservice.dto.SingleBookingDto;
import fact.it.bookingservice.model.Booking;
import fact.it.bookingservice.model.SingleBooking;
import fact.it.bookingservice.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    final BookingRepository bookingRepository;
    private final WebClient webClient;

    @Value("${tripservice.baseurl}")
    private String tripServiceBaseUrl;

    @Value("${travelerservice.baseurl}")
    private String travelerServiceBaseUrl;


    public boolean registerBooking(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setBookingNumber(UUID.randomUUID().toString());

        List<SingleBooking> singleBookings = bookingRequest.getSingleBookingDtoList()
                .stream()
                .map(this::mapToSingleBooking)
                .toList();

        booking.setSingleBookingsList(singleBookings);
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
