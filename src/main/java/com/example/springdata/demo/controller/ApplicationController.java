package com.example.springdata.demo.controller;


import com.example.springdata.demo.model.entity.Application;
import com.example.springdata.demo.repository.ApplicationRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/applications")
public class ApplicationController {

  @Autowired
  ApplicationRepository applicationRepository;

  @RequestMapping(method = RequestMethod.GET, value = "")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page", dataType = "string", paramType = "query",
          value = "Results page you want to retrieve (0..N)"),
      @ApiImplicitParam(name = "size", dataType = "string", paramType = "query",
          value = "Number of records per page."),
      @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
          value = "Sorting criteria in the format: property(,asc|desc). " +
              "Default sort order is ascending. " +
              "Multiple sort criteria are supported.")
  })
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Page of applications", response = Page.class)
      }
  )
  public @ResponseBody
  Page<Application> getApplicationsList(Pageable pageable) {
    return applicationRepository.findAll(pageable);
  }


  @RequestMapping(method = RequestMethod.POST, value = "")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "New Application", response = Application.class)
      }
  )
  public @ResponseBody
  Application create(
      @RequestBody(required = true) Application applicationInfo) {
    return applicationRepository.save(applicationInfo);
  }



  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Application Details", response = Application.class)
      }
  )
  public @ResponseBody
  Application get(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) final String accessToken,
      @PathVariable(value = "id", required = true) String applicationId) {
    return applicationRepository.findOne(Integer.parseInt(applicationId));
  }



  @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Updated application info", response = Application.class)
      }
  )
  public @ResponseBody
  Application updateApplication(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) final String accessToken,
      @RequestBody(required = true) Application updatedApplicationInfo) {
    return applicationRepository.save(updatedApplicationInfo);
  }


  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteApplication(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) final String accessToken,
      @PathVariable(value = "id", required = true) String applicationId) {
    applicationRepository.delete(Integer.parseInt(applicationId));

  }



}
