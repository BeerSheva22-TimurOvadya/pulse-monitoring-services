package telran.monitoring.entities;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "patients")
@Data
public class Patient {
	@Id
	long id;	
	String name;
}
