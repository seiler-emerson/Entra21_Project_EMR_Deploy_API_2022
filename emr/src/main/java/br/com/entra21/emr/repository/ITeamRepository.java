package br.com.entra21.emr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.entra21.emr.model.Team;

public interface ITeamRepository extends JpaRepository<Team, Integer> {

}
