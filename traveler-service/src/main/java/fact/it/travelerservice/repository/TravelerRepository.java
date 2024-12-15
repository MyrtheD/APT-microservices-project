package fact.it.travelerservice.repository;

import fact.it.travelerservice.model.Traveler;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TravelerRepository extends JpaRepository<Traveler, Long> {
    List<Traveler> findByIdIn(List<Long> id);
}
