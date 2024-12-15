package fact.it.travelerservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;

@Entity
@Table(name = "traveler")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Traveler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private DateFormat birthDate;
}
