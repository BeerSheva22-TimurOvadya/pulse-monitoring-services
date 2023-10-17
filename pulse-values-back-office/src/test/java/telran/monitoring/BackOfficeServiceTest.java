package telran.monitoring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import telran.monitoring.entities.AvgPulseDoc;
import telran.monitoring.repo.AvgPulseRepository;
import telran.monitoring.service.BackOfficeService;

@SpringBootTest
public class BackOfficeServiceTest {

    private static final long PATIENT_ID = 333L;
    private static final LocalDateTime FROM_DATE = LocalDateTime.parse("2023-08-05T15:10:00");
    private static final LocalDateTime TO_DATE = LocalDateTime.parse("2023-10-17T13:08:00");
    
    private static final List<AvgPulseDoc> DOCS = Arrays.asList(
        new AvgPulseDoc(PATIENT_ID, LocalDateTime.of(2023, 8, 6, 10, 0), 60),
        new AvgPulseDoc(PATIENT_ID, LocalDateTime.of(2023, 9, 1, 14, 0), 70),
        new AvgPulseDoc(PATIENT_ID, LocalDateTime.of(2023, 9, 15, 12, 0), 80),
        new AvgPulseDoc(PATIENT_ID, LocalDateTime.of(2023, 9, 20, 17, 0), 90),
        new AvgPulseDoc(PATIENT_ID, LocalDateTime.of(2023, 10, 12, 13, 0), 100)
        
    );

    @Autowired
    AvgPulseRepository repository;
    
    @Autowired
    BackOfficeService service;

    @BeforeEach
    public void setUp() {       
        repository.saveAll(DOCS);        
    }
    
    @AfterEach
    public void tearDown() {       
        repository.deleteAll();
    }

   

    @Test
    public void avgValueTest() {
        int avgValue = service.getAvgValue(PATIENT_ID, FROM_DATE, TO_DATE);
        assertEquals(80, avgValue); 
    }

    @Test
    public void maxValueTest() {
        int maxValue = service.getMaxValue(PATIENT_ID, FROM_DATE, TO_DATE);
        assertEquals(100, maxValue);
    }

    @Test
    public void allValuesTest() {
        List<Integer> values = service.getAllValues(PATIENT_ID, FROM_DATE, TO_DATE);
        assertEquals(Arrays.asList(60, 70, 80, 90, 100), values);
    }
}
