package com.example.springdata.demo.model.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@Table(name = "egogroup")
@Entity
@JsonPropertyOrder({"id", "name", "description", "status","applications"})
@JsonInclude(JsonInclude.Include.ALWAYS)
@NoArgsConstructor
@EqualsAndHashCode(of={"id"})
public class Group {

  @Id
  @Column(nullable = false, name = "id", updatable = false)
  int id;
  @Column(nullable = false, name = "name", updatable = false)
  @NonNull
  String name;
  @Column(nullable = false, name = "description", updatable = false)
  String description;
  @Column(nullable = false, name = "status", updatable = false)
  String status;
  @ManyToMany(targetEntity = Application.class, cascade = {CascadeType.ALL})
  @JoinTable(name = "groupapplication", joinColumns = { @JoinColumn(name = "grpid") },
      inverseJoinColumns = { @JoinColumn(name = "appid") })
  @JsonIgnore List<Application> applications;

}
