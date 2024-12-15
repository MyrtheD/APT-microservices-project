package fact.it.tripservice.repository;

import fact.it.tripservice.model.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TripRepository extends MongoRepository<Trip, String> {
    List<Trip> findByIdIn(List<String> id);
}
