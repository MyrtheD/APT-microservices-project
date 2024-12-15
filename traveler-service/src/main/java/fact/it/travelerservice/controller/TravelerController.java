package fact.it.travelerservice.controller;

import fact.it.travelerservice.dto.TravelerResponse;
import fact.it.travelerservice.service.TravelerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/traveler")
@RequiredArgsConstructor
public class TravelerController {
    private final TravelerService travelerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TravelerResponse> getAllTravelersById
            (@RequestParam List<Long> id) {
        return travelerService.getAllTravelersById(id);
    }
}
