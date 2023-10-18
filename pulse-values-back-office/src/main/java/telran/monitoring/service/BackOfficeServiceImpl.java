package telran.monitoring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telran.monitoring.entities.AvgPulseDoc;
import telran.monitoring.repo.AvgPulseRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BackOfficeServiceImpl implements BackOfficeService {

	@Autowired
    AvgPulseRepository repository;

    @Override
    public int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to) {
        Double avgValue = repository.getAverageValue(patientId, from, to);
        if (avgValue == null) {
            log.debug("No documents found for patientId: {}. Returning average value of 0.", patientId);
            return 0;
        }
        log.debug("Average value calculated: {}", avgValue);
        return avgValue.intValue();
    }

    @Override
    public int getMaxValue(long patientId, LocalDateTime from, LocalDateTime to) {
        Integer maxValue = repository.getMaxValue(patientId, from, to);
        if (maxValue == null) {
            log.debug("No documents found for patientId: {}. Returning max value of 0.", patientId);
            return 0;
        }
        log.debug("Max value found: {}", maxValue);
        return maxValue;
    }

    @Override
    public List<Integer> getAllValues(long patientId, LocalDateTime from, LocalDateTime to) {        
        List<Integer> allValues = repository.findByPatientIdAndDateTimeBetween(patientId, from, to)
                         .stream().map(AvgPulseDoc::getValue).collect(Collectors.toList());
        log.debug("All values retrieved: {}", allValues);
        return allValues;
    }
}
