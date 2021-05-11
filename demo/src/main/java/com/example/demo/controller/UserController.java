package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/users")
    public ResponseEntity<List<UserManagement>> getAllUsers(){
        List<UserAccount> userAccountList = userService.findAll();
        List<UserManagement> userManagements = new ArrayList<>();
        for(UserAccount user : userAccountList){
            String companyName = user.getCompanyName();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<CompanyModel> companyModelResponseEntity = restTemplate.getForEntity
                    ("http://localhost:8080/api/v1/companies/getcompanybyname/" +companyName, CompanyModel.class);
            UserManagement userManagement = UserManagement.builder()
                    .companyModel(companyModelResponseEntity.getBody())
                    .userAccount(user)
                    .build();
            userManagements.add(userManagement);
        }

        return new ResponseEntity<>(userManagements, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/usersdetails")
    public ResponseEntity<List<UserDetail>> getUserDetails(){
        List<UserAccount> userAccountList = userService.findAll();
        List<UserDetail> userDetailList = new ArrayList<>();

        for(UserAccount user: userAccountList){
            String userId = user.getUserId().toString();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JobHunterModel> jobHunterModelResponseEntity = restTemplate.getForEntity
                    ("http://localhost:8080/api/v1/jobhunter/" + userId, JobHunterModel.class);
            UserDetail userDetail = UserDetail.builder()
                    .userAccount(user)
                    .jobHunterModel(jobHunterModelResponseEntity.getBody())
                    .build();
            userDetailList.add(userDetail);
        }
        return new ResponseEntity<>(userDetailList, new HttpHeaders(), HttpStatus.OK);
    }



    @GetMapping(value = "/{id}")
    public ResponseEntity<UserAccount> getUserById(@PathVariable Long id){

        if(userService.findById(id).isPresent()){
            return new ResponseEntity<>(userService.findById(id).get(), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }



}
