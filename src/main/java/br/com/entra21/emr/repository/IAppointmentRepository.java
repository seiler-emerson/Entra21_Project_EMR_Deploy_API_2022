package br.com.entra21.emr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.entra21.emr.model.Appointment;

@Repository
@EnableJpaRepositories
public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {

	@Query("FROM Appointment WHERE id =:id")
	Appointment returnById(@Param("id")Integer id);
	
	public List<Appointment> findByPatient_idEquals(Integer id);
	
}
