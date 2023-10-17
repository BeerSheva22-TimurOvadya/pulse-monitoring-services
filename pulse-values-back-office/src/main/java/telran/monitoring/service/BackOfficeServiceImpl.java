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
        List<AvgPulseDoc> docs = repository.findByPatientIdAndDateTimeBetween(patientId, from, to);
        if (docs.isEmpty()) {
            log.debug("No documents found for patientId: {}. Returning average value of 0.", patientId);
            return 0;
        }
        int avgValue = docs.stream().mapToInt(AvgPulseDoc::getValue).sum() / docs.size();
        log.debug("Average value calculated: {}", avgValue);
        return avgValue;
    }

    @Override
    public int getMaxValue(long patientId, LocalDateTime from, LocalDateTime to) {       
        int maxValue = repository.findByPatientIdAndDateTimeBetween(patientId, from, to)
                         .stream().mapToInt(AvgPulseDoc::getValue).max().orElse(0);
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
