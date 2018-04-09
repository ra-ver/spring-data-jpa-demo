package com.example.springdata.demo.specification;

import com.example.springdata.demo.model.entity.Application;
import com.example.springdata.demo.model.entity.Group;
import lombok.val;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class ApplicationSpecification {

  public static Specification<Application> containsTextInAttributes(String text, List<String> attributes) {
    if (!text.contains("%")) {
      text = "%" + text + "%";
    }
    String finalText = text;
    return (root, query, builder) -> builder.or(root.getModel().getDeclaredSingularAttributes().stream()
        .filter(a -> attributes.contains(a.getName()))
        .map(a -> builder.like(root.get(a.getName()), finalText))
        .toArray(Predicate[]::new)
    );
  }

  public static Specification<Application> containsText(String text) {
    if (!text.contains("%")) {
      text = "%" + text + "%";
    }
    val finalText = text.toLowerCase();
    return (root, query, builder) -> builder.or(
        builder.like(builder.lower(root.get("name")), finalText),
        builder.like(builder.lower(root.get("clientId")), finalText),
        builder.like(builder.lower(root.get("description")), finalText)

    );
  }

  public static Specification<Application> inGroup(Integer groupId) {
    val finalGroupId = groupId;
    return (root, query, builder) ->
    {
      Join<Application, Group> groupJoin = root.join("groups");
      return builder.equal(groupJoin.<Integer> get("id"), groupId);
    };

  }
}
