package telran.monitoring.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.monitoring.entities.AvgPulseDoc;
import java.time.LocalDateTime;
import java.util.List;

public interface AvgPulseRepository extends MongoRepository<AvgPulseDoc, String> {
    List<AvgPulseDoc> findByPatientIdAndDateTimeBetween(long patientId, LocalDateTime from, LocalDateTime to);
}
