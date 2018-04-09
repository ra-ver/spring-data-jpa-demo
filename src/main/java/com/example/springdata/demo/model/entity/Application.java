package com.example.springdata.demo.model.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "egoapplication")
@Data
@JsonPropertyOrder({"id", "name", "clientId", "clientSecret", "redirectUri", "description", "status"})
@JsonInclude(JsonInclude.Include.ALWAYS)
@NoArgsConstructor
public class Application {

  @Id
  @Column(nullable = false, name = "id", updatable = false)
  int id;
  @NonNull
  @Column(nullable = false, name = "name", updatable = false)
  String name;
  @NonNull
  @Column(nullable = false, name = "clientid", updatable = false)
  String clientId;
  @NonNull
  @Column(nullable = false, name = "clientsecret", updatable = false)
  String clientSecret;
  @Column(nullable = false, name = "redirecturi", updatable = false)
  String redirectUri;
  @Column(nullable = false, name = "description", updatable = false)
  String description;
  @Column(nullable = false, name = "status", updatable = false)
  String status;

  @ManyToMany(mappedBy = "applications", cascade = CascadeType.ALL)
  @JsonIgnore List<Group> groups;

  @JsonIgnore
  public HashSet<String> getURISet(){
    val output = new HashSet<String>();
    output.add(this.redirectUri);
    return output;
  }
}