package br.com.entra21.emr.model;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
@Table(name="user_emr")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User extends MaturidadeNivel3Richardson {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String login;
	private String email;
	private String password;
	private String type;
	@OneToOne
	@JoinColumn(name="doctor_id")
	private Doctor doctor;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(ArrayList<ItemNivel3> links) {
		super(links);
		// TODO Auto-generated constructor stub
	}
	public User(Integer id, String name, String login, String email, String password, String type, Doctor doctor) {
		super();
		this.id = id;
		this.name = name;
		this.login = login;
		this.email = email;
		this.password = password;
		this.type = type;
		this.doctor = doctor;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	

	
	
	
}
