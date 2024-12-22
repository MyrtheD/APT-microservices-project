package fact.it.travelerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelerRequest {
    private String name;
    private String address;
    private String phone;
    private String email;
    private DateFormat birthDate;
}
