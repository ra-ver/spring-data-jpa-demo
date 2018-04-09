package com.example.springdata.demo.controller;

import com.example.springdata.demo.model.entity.Application;
import com.example.springdata.demo.model.entity.Group;
import com.example.springdata.demo.repository.ApplicationRepository;
import com.example.springdata.demo.repository.GroupRepository;
import com.example.springdata.demo.specification.ApplicationSpecification;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

@Slf4j
@RestController
@RequestMapping("/groups")
public class GroupController {


  @Autowired
  GroupRepository groupRepository;

  @Autowired
  ApplicationRepository applicationRepository;


  @RequestMapping(method = RequestMethod.GET, value = "")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Page of groups", response = Page.class)
      }
  )
  public @ResponseBody
  Page<Group> getGroupsList(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) final String accessToken,
      @RequestParam(value = "query", required = false) String query, Pageable pageable) {
    return groupRepository.findAll(pageable);
  }


  @RequestMapping(method = RequestMethod.POST, value = "")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "New Group", response = Group.class)
      }
  )
  public @ResponseBody
  Group createGroup(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) final String accessToken,
      @RequestBody(required = true) Group groupInfo) {
    return groupRepository.save(groupInfo);
  }



  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Group Details", response = Group.class)
      }
  )
  public @ResponseBody
  Group getGroup(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) final String accessToken,
      @PathVariable(value = "id", required = true) String groupId) {
    return groupRepository.findOne(Integer.parseInt(groupId));
  }



  @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Updated group info", response = Group.class)
      }
  )
  public @ResponseBody
  Group updateGroup(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) final String accessToken,
      @RequestBody(required = true) Group updatedGroupInfo) {
    return groupRepository.save(updatedGroupInfo);
  }


  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteGroup(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) final String accessToken,
      @PathVariable(value = "id", required = true) String groupId) {
    groupRepository.delete(Integer.parseInt(groupId));
  }

  @RequestMapping(method = RequestMethod.GET, value = "/{id}/applications")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Page of applications of group", response = Page.class)
      }
  )
  public @ResponseBody
  Page<Application> getGroupsApplications(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) final String accessToken,
      @PathVariable(value = "id", required = true) String groupId,
      @RequestParam(value = "query", required = false) String query, Pageable pageable)
  {
    if(query != null  && query.isEmpty() ==  false) {
      return applicationRepository.findAll(
          where(ApplicationSpecification.inGroup(Integer.parseInt(groupId))).
              and(ApplicationSpecification.containsText(query)), pageable);
    } else {
      return  applicationRepository.findAllByGroupsId(Integer.parseInt(groupId), pageable);
    }

  }


  @RequestMapping(method = RequestMethod.POST, value = "/{id}/applications")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Add Apps to Group", response = String.class)
      }
  )
  public @ResponseBody
  String addAppsToGroups(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) final String accessToken,
      @PathVariable(value = "id", required = true) String grpId,
      @RequestBody(required = true) List<String> apps) {


    // TODO: add a new relationship entity
    return apps.size() + " apps added successfully.";
  }



  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}/applications/{appIDs}")
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Delete Apps from Group")
      }
  )
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteAppsFromGroup(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) final String accessToken,
      @PathVariable(value = "id", required = true) String grpId,
      @PathVariable(value = "appIDs", required = true) List<String> appIDs) {
    //TODO: delete the relationship
  }
}
