package com.egov.profileservice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "profiles")
public class Profile {

    @Id
    String phone;
    String firstName;
    String lastName;
    String email;
    String location;
    String aadhar;
}
