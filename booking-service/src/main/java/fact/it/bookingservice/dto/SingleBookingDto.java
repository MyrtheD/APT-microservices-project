package fact.it.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleBookingDto {
    private Long id;
    private Integer tripId;
    private Integer numberOfPeople;
    private Integer travelerId;
    private BigDecimal totalPrice;
}
