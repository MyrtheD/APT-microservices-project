package fact.it.travelerservice;

import fact.it.travelerservice.dto.TravelerResponse;
import fact.it.travelerservice.model.Traveler;
import fact.it.travelerservice.repository.TravelerRepository;
import fact.it.travelerservice.service.TravelerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TravelerServiceUnitTests {

	@InjectMocks
	private TravelerService travelerService;

	@Mock
	private TravelerRepository travelerRepository;

	@Test
	public void testGetAllTravelers() {
		// Arrange
		Traveler traveler1 = new Traveler();
		traveler1.setName("John Doe");
		traveler1.setAddress("12th avenue south");
		traveler1.setPhone("0489986589");
		traveler1.setEmail("johndoe@mail.com");
		traveler1.setBirthDate(DateFormat.getTimeInstance(01-1-2000));

		Traveler traveler2 = new Traveler();
		traveler2.setName("Jane Doe");
		traveler2.setAddress("12th avenue south");
		traveler2.setPhone("0487776389");
		traveler2.setEmail("janedoe@mail.com");
		traveler2.setBirthDate(DateFormat.getTimeInstance(01-1-2000));

		when(travelerRepository.findAll()).thenReturn(Arrays.asList(traveler1, traveler2));

		// Act
		List<TravelerResponse> result = travelerService.getAllTravelers();

		// Assert
		assertEquals(2, result.size());
		assertEquals("John Doe", result.get(0).getName());
		assertEquals("Jane Doe", result.get(1).getName());

		verify(travelerRepository, times(1)).findAll();
	}

}
