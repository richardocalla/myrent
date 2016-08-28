package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;
import utils.LatLng;

@Entity
public class Message extends Model {
	public String firstName;
	public String lastName;
	public String email;
	public String messageTxt;

	public Message(String firstName, String lastName, String email, String messageTxt) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.messageTxt = messageTxt;
	}

}