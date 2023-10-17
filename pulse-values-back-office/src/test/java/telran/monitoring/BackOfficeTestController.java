package telran.monitoring;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.monitoring.service.BackOfficeService;

@SpringBootTest
@AutoConfigureMockMvc
public class BackOfficeTestController {
	private static final int AVG_VALUE = 80;
	private static final int MAX_VALUE = 100;
	private static final List<Integer> ALL_VALUES = Arrays.asList(60, 70, 80, 90, 100);
	
	private static final String FROM_DATE = "2023-08-05T15:10:00";
    private static final String TO_DATE = "2023-10-17T13:08:00";
    private static final long PATIENT_ID = 333l;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	BackOfficeService backService;
	
	@Autowired
    ObjectMapper objectMapper;
	

	@BeforeEach
    void serviceMocking() {
        when(backService.getAvgValue(anyLong(), any(), any())).thenReturn(AVG_VALUE);
        when(backService.getMaxValue(anyLong(), any(), any())).thenReturn(MAX_VALUE);
        when(backService.getAllValues(anyLong(), any(), any())).thenReturn(ALL_VALUES);
    }

	@Test
	void avgValueTest() throws Exception {
		String res = mockMvc.perform(get("/pulse/values/avg/" + PATIENT_ID).param("from", FROM_DATE).param("to", TO_DATE))
            .andReturn()
            .getResponse()
            .getContentAsString();
		assertEquals(Integer.toString(AVG_VALUE), res);
	}

	@Test
	void maxValueTest() throws Exception {
		String res = mockMvc.perform(get("/pulse/values/max/"+ PATIENT_ID).param("from", FROM_DATE).param("to", TO_DATE))
            .andReturn()
            .getResponse()
            .getContentAsString();
		assertEquals(Integer.toString(MAX_VALUE), res);
	}

	@Test
	void allValuesTest() throws Exception {
		String res = mockMvc.perform(get("/pulse/values/all/"+ PATIENT_ID).param("from", FROM_DATE) .param("to", TO_DATE))
            .andReturn()
            .getResponse()
            .getContentAsString();            
		List<Integer> responseList = objectMapper.readValue(res, new TypeReference<List<Integer>>() {});
		assertEquals(ALL_VALUES, responseList);
	}
	
}
