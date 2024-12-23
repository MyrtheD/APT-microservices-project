package fact.it.bookingservice.controller;

import fact.it.bookingservice.BookingServiceApplication;
import fact.it.bookingservice.dto.BookingRequest;
import fact.it.bookingservice.dto.BookingResponse;
import fact.it.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String registerBooking(@RequestBody BookingRequest bookingRequest) {
        boolean result = bookingService.registerBooking(bookingRequest);
        return (result ? "Booking registered successfully" : "Booking failed");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }


    // New Delete Mapping for Booking
    @DeleteMapping("/booking/{bookingNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // 204 No Content response
    public String deleteBooking(@PathVariable String bookingNumber) {
        boolean result = bookingService.deleteBooking(bookingNumber);
        return (result ? "Booking deleted successfully" : "Booking deletion failed");
    }
}
