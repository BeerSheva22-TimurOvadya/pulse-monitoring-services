package telran.monitoring.entities;

import java.time.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import telran.monitoring.dto.PulseProbe;

//@Document(collection = "documents")
@Data
@AllArgsConstructor 
public class AvgPulseDoc {
	long patientId;
	LocalDateTime dateTime;
	int value;

	
	public static AvgPulseDoc of(PulseProbe pulseProbe) {
		return new AvgPulseDoc(pulseProbe.patientId(),
				LocalDateTime.ofInstant(Instant.ofEpochMilli(pulseProbe.timestamp()), ZoneId.systemDefault()),
				pulseProbe.value());
	}
}
