package fact.it.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DateFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripResponse {
    private String id;
    private Integer numberOfDays;
    private String destination;
    private String hotel;
    private Integer numberOfPlaces;
    private Integer numberOfPlacesAvailable;
    private BigDecimal price;
    private DateFormat departureDate;
    private DateFormat returnDate;
}
