package com.websystique.springmvc.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="USER")
public class User {

	public static final int minPasswordLength = 6;
	public static final int maxPasswordLength = 20;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min=3, max=50)
	@Column(name = "name", nullable = false)
	private String firstName;

//	@Size(min=3, max=50)
//	@Column(name = "secondname", nullable = false)
//	private String secondName;

	@NotEmpty
	@Column(name = "nickname", unique=true, nullable = false)
	private String nickname;

	@Size(min=minPasswordLength, max=maxPasswordLength)
	@Column(name = "password", nullable = false)
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

//	public String getSecondName() {
//		return secondName;
//	}
//
//	public void setSecondName(String secondname) {
//		this.secondName = secondname;
//	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + firstName +", nickname=" + nickname + "]";
	}

}
