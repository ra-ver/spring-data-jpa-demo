package com.example.springdata.demo.repository;

import com.example.springdata.demo.model.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ApplicationRepository
    extends PagingAndSortingRepository<Application, Integer>, JpaSpecificationExecutor {

  Page<Application> findAllByNameContaining(String name, Pageable pageable);
  Page<Application> findAllByClientIdContaining(String clientId, Pageable pageable);
  Page<Application> findAllByIdOrderById(Integer id, Pageable pageable);
  Page<Application> findAllByGroupsId(Integer id, Pageable pageable);
  Page<Application> findAllByGroupsId(Integer id, Specification<Application> specification, Pageable pageable);


}