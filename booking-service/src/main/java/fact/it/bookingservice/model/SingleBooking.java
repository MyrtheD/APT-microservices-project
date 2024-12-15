package fact.it.bookingservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "singlebooking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer tripId;
    private Integer numberOfPeople;
    private Integer travelerId;
    private BigDecimal totalPrice;
}
