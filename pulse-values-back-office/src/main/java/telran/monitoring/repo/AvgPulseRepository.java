package telran.monitoring.repo;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import telran.monitoring.entities.AvgPulseDoc;
import java.time.LocalDateTime;
import java.util.List;

public interface AvgPulseRepository extends MongoRepository<AvgPulseDoc, String> {
    List<AvgPulseDoc> findByPatientIdAndDateTimeBetween(long patientId, LocalDateTime from, LocalDateTime to);

    @Aggregation(pipeline = {
            "{ $match: { patientId: ?0, dateTime: { $gte: ?1, $lte: ?2 } } }",
            "{ $group: { _id: null, avgValue: { $avg: '$value' } } }"
    })
    Double getAverageValue(long patientId, LocalDateTime from, LocalDateTime to);

    @Aggregation(pipeline = {
            "{ $match: { patientId: ?0, dateTime: { $gte: ?1, $lte: ?2 } } }",
            "{ $group: { _id: null, maxValue: { $max: '$value' } } }"
    })
    Integer getMaxValue(long patientId, LocalDateTime from, LocalDateTime to);
}
