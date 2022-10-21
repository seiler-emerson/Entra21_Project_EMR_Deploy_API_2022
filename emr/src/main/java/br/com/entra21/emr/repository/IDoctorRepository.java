package br.com.entra21.emr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.entra21.emr.model.Doctor;

@Repository
@EnableJpaRepositories
public interface IDoctorRepository extends JpaRepository<Doctor, Integer> {

	@Query("FROM Doctor WHERE id =:id")
	Doctor returnById(@Param("id")Integer id);
	
	public List<Doctor> findByNameStartingWith(String prefix);
}
