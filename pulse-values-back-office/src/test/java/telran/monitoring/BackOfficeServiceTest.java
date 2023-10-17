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
	private static final LocalDateTime TO_DATE2 = LocalDateTime.parse("2023-09-21T13:08:00");

	private static final long PATIENT_ID_NO_VISIT = 444L;
	private static final LocalDateTime FROM_DATE_NO_VISIT = LocalDateTime.parse("2022-08-05T15:10:00");
	private static final LocalDateTime TO_DATE_NO_VISIT = LocalDateTime.parse("2022-10-17T13:08:00");

	private static final List<AvgPulseDoc> DOCS = Arrays.asList(
			new AvgPulseDoc(PATIENT_ID, LocalDateTime.of(2023, 8, 6, 10, 0), 60),
			new AvgPulseDoc(PATIENT_ID, LocalDateTime.of(2023, 9, 1, 14, 0), 70),
			new AvgPulseDoc(PATIENT_ID, LocalDateTime.of(2023, 9, 15, 12, 0), 80),
			new AvgPulseDoc(PATIENT_ID, LocalDateTime.of(2023, 9, 20, 17, 0), 90),
			new AvgPulseDoc(PATIENT_ID, LocalDateTime.of(2023, 10, 12, 13, 0), 100));

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
	public void avgValueTestNormal() {
		int avgValue = service.getAvgValue(PATIENT_ID, FROM_DATE, TO_DATE);
		assertEquals(80, avgValue);

		int avgValue2 = service.getAvgValue(PATIENT_ID, FROM_DATE, TO_DATE2);
		assertEquals(75, avgValue2);
	}

	@Test
	public void avgValueTestAbNormal() {
		int avgValue = service.getAvgValue(PATIENT_ID_NO_VISIT, FROM_DATE, TO_DATE);
		assertEquals(0, avgValue);

		int avgValue2 = service.getAvgValue(PATIENT_ID, FROM_DATE_NO_VISIT, TO_DATE_NO_VISIT);
		assertEquals(0, avgValue2);
	}

	@Test
	public void maxValueTestNormal() {
		int maxValue = service.getMaxValue(PATIENT_ID, FROM_DATE, TO_DATE);
		assertEquals(100, maxValue);

		int maxValue2 = service.getMaxValue(PATIENT_ID, FROM_DATE, TO_DATE2);
		assertEquals(90, maxValue2);
	}

	@Test
	public void maxValueTestAbNormal() {
		int maxValue = service.getMaxValue(PATIENT_ID_NO_VISIT, FROM_DATE, TO_DATE);
		assertEquals(0, maxValue);

		int maxValue2 = service.getMaxValue(PATIENT_ID, FROM_DATE_NO_VISIT, TO_DATE_NO_VISIT);
		assertEquals(0, maxValue2);
	}

	@Test
	public void allValuesTestNormal() {
		List<Integer> values = service.getAllValues(PATIENT_ID, FROM_DATE, TO_DATE);
		assertEquals(Arrays.asList(60, 70, 80, 90, 100), values);

		List<Integer> values2 = service.getAllValues(PATIENT_ID, FROM_DATE, TO_DATE2);
		assertEquals(Arrays.asList(60, 70, 80, 90), values2);
	}

	@Test
	public void allValuesTestAbNormal() {
		List<Integer> values = service.getAllValues(PATIENT_ID_NO_VISIT, FROM_DATE, TO_DATE);
		assertEquals(Arrays.asList(), values);

		List<Integer> values2 = service.getAllValues(PATIENT_ID, FROM_DATE_NO_VISIT, TO_DATE_NO_VISIT);
		assertEquals(Arrays.asList(), values2);
	}
}
