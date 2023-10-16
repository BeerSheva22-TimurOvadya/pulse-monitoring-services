package telran.monitoring.entities;


import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name="doctors")
@Data 
public class Doctor {
	@Id
	String email;
	String name;
}