package telran.monitoring.entities;

import java.time.LocalDate;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "visits", indexes = { @Index(columnList = "patient_id") })
@Data
public class Visit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	LocalDate date;
	@ManyToOne
	@JoinColumn(name = "doctor_email")
	Doctor doctor;
	@ManyToOne
	@JoinColumn(name = "patient_id")
	Patient patient;
}