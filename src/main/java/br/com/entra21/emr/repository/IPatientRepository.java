package br.com.entra21.emr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.entra21.emr.model.Patient;

@Repository
@EnableJpaRepositories
public interface IPatientRepository extends JpaRepository<Patient, Integer>  {

	
	@Query("FROM Patient WHERE id =:id")
	Patient returnById(@Param("id")Integer id);

	public List<Patient> findByNameStartingWith(String prefix);
}
