package sg.edu.nus.leaveapplication.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@ManyToMany(mappedBy = "roles")
	private List<Credentials> credentials;
	
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public List<Credentials> getCredentials() {
		return credentials;
	}
	public void setCredentials(List<Credentials> credentials) {
		this.credentials = credentials;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
