package fact.it.tripservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.text.DateFormat;

@Document(value = "trip")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Trip {
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
