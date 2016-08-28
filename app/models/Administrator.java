package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Administrator extends Model {
	public String email;
	public String password;

	public Administrator(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}

	public static Administrator findByEmail(String email) {
		return find("email", email).first();
	}

}