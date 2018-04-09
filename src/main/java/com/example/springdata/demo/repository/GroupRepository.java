package com.example.springdata.demo.repository;

import com.example.springdata.demo.model.entity.Application;
import com.example.springdata.demo.model.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GroupRepository extends PagingAndSortingRepository<Group, Integer> {

}
