package fact.it.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelerResponse {
    private String name;
    private String address;
    private String phone;
    private String email;
    private DateFormat birthDate;
}
