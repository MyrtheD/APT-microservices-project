package fact.it.travelerservice.service;

import fact.it.travelerservice.dto.TravelerResponse;
import fact.it.travelerservice.model.Traveler;
import fact.it.travelerservice.repository.TravelerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelerService {
    private  final TravelerRepository travelerRepository;
    @PostConstruct
    public void loadData() {
        if(travelerRepository.count() <= 0){
            Traveler traveler = new Traveler();
            traveler.setName("John Doe");
            traveler.setAddress("12th avenue south");
            traveler.setPhone("0489986589");
            traveler.setEmail("johndoe@mail.com");
            traveler.setBirthDate(DateFormat.getTimeInstance(01-1-2000));

            Traveler traveler1 = new Traveler();
            traveler1.setName("Jane Doe");
            traveler1.setAddress("12th avenue south");
            traveler1.setPhone("0487776389");
            traveler1.setEmail("janedoe@mail.com");
            traveler1.setBirthDate(DateFormat.getTimeInstance(01-1-2000));

            travelerRepository.save(traveler);
            travelerRepository.save(traveler1);
        }
    }

//    @Transactional(readOnly = true)
    public List<TravelerResponse> getAllTravelersById(List<Long> id) {

        return travelerRepository.findByIdIn(id).stream()
                .map(traveler ->
                        TravelerResponse.builder()
                                .name(traveler.getName())
                                .address(traveler.getAddress())
                                .phone(traveler.getPhone())
                                .email(traveler.getEmail())
                                .birthDate(traveler.getBirthDate())
                                .build()
                ).toList();
    }
}
