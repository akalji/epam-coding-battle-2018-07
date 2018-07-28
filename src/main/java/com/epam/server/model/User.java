package com.epam.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name="user_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

@Column(name="user_name")
  private String name;

@Column(name="user_password")
  private String password;

@Column(name="user_password")
  private String email;

@Column(name="user_requests")
  private int requests;


  public User(String name, String password, String email, int requests) {
    this.name = name;
    this.password = password;
    this.email = email;
    this.requests = requests;
  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getRequests() {
    return requests;
  }

  public void setRequests(int requests) {
    this.requests = requests;
  }
}
