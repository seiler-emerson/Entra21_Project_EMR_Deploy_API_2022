package br.com.entra21.emr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.entra21.emr.model.User;

public interface IUserRepository extends JpaRepository<User, Integer> {

	public List<User> findByNameStartingWith(String prefix);
	
	@Query("FROM User WHERE id =:id")
	User returnById(@Param("id")Integer id);
	
}
