package fact.it.bookingservice;

import fact.it.bookingservice.dto.BookingRequest;
import fact.it.bookingservice.dto.SingleBookingDto;
import fact.it.bookingservice.dto.TripResponse;
import fact.it.bookingservice.model.Booking;
import fact.it.bookingservice.model.SingleBooking;
import fact.it.bookingservice.repository.BookingRepository;
import fact.it.bookingservice.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceUnitTests {

	@InjectMocks
	private BookingService bookingService;

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private WebClient webClient;

	@Mock
	private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

	@Mock
	private WebClient.RequestHeadersSpec requestHeadersSpec;

	@Mock
	private WebClient.ResponseSpec responseSpec;

	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(bookingService, "tripServiceBaseUrl", "http://localhost:8080");
	}

	@Test
	public void testRegisterBooking_Success() {
		// Arrange
		int numberOfPeople = 3;

		// Mock trip details
		TripResponse tripResponse = new TripResponse();
		tripResponse.setId("1");
		tripResponse.setNumberOfPlacesAvailable(5);
		tripResponse.setPrice(BigDecimal.valueOf(200.00));

		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(TripResponse.class)).thenReturn(Mono.just(tripResponse));

		BookingRequest bookingRequest = new BookingRequest();
		SingleBookingDto singleBookingDto = new SingleBookingDto();
		singleBookingDto.setTripId(1);
		singleBookingDto.setNumberOfPeople(numberOfPeople);
		singleBookingDto.setTravelerId(1);
		bookingRequest.setSingleBookingDtoList(Collections.singletonList(singleBookingDto));

		when(bookingRepository.save(any(Booking.class))).thenReturn(new Booking());

		// Act
		boolean result = bookingService.registerBooking(bookingRequest);

		// Assert
		assertTrue(result);
		verify(bookingRepository, times(1)).save(any(Booking.class));
		verify(webClient, times(1)).get();
	}

	@Test
	public void testRegisterBooking_Failure_NotEnoughSeats() {
		// Arrange
		int numberOfPeople = 6;

		// Mock trip details
		TripResponse tripResponse = new TripResponse();
		tripResponse.setId("1");
		tripResponse.setNumberOfPlacesAvailable(5);
		tripResponse.setPrice(BigDecimal.valueOf(200.00));

		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(TripResponse.class)).thenReturn(Mono.just(tripResponse));

		BookingRequest bookingRequest = new BookingRequest();
		SingleBookingDto singleBookingDto = new SingleBookingDto();
		singleBookingDto.setTripId(1);
		singleBookingDto.setNumberOfPeople(numberOfPeople);
		singleBookingDto.setTravelerId(1);
		bookingRequest.setSingleBookingDtoList(Collections.singletonList(singleBookingDto));

		// Act
		boolean result = bookingService.registerBooking(bookingRequest);

		// Assert
		assertFalse(result);
		verify(bookingRepository, times(0)).save(any(Booking.class));
		verify(webClient, times(1)).get();
	}
}
