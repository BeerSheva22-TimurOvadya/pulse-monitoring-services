package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.service.EmailProviderService;

@SpringBootTest
@AutoConfigureMockMvc
class EmailDataProviderTest {

	@Autowired
	MockMvc mockMvc;
	private String DOCTOR_MAIL = "doctor2@gmail.com";
	private String DOCTOR_NAME = "doctor2";
	private String PATIENT_NAME = "Vasya";
	
	 @Autowired
	  EmailProviderService service;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	@Sql(scripts = { "Visits.sql" })
	void test() throws Exception {
		String jsonResponse = mockMvc.perform(get("/data/333")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		EmailNotificationData notificationData = mapper.readValue(jsonResponse, EmailNotificationData.class);
		assertEquals(DOCTOR_MAIL, notificationData.doctorMail());
		assertEquals(DOCTOR_NAME, notificationData.doctorName());
		assertEquals(PATIENT_NAME, notificationData.patientName());

	}

	@Test
	@Disabled
	@Sql(scripts = { "Visits.sql" })
	void test2() throws Exception {
		EmailNotificationData notificationData = service.getEmailNotificationData(333);
		assertEquals(DOCTOR_MAIL, notificationData.doctorMail());
		assertEquals(DOCTOR_NAME, notificationData.doctorName());
		assertEquals(PATIENT_NAME, notificationData.patientName());
	}

}