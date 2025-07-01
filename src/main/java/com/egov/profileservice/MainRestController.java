package com.egov.profileservice;
import com.egov.profileservice.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestController
@RequestMapping("api/v1")
public class MainRestController {
    private static final Logger logger = LoggerFactory.getLogger(MainRestController.class);

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("save/profile")
    public ResponseEntity<String> addProfile(@RequestHeader("Authorization") String token,
                                             @RequestBody Profile profile) {
        logger.info("Received parameter for profile"+ profile.toString());
        String phone;
        try {
            phone = tokenService.validateToken(token);
        }catch(WebClientResponseException e){
            logger.info("Token validation failed: " + e.getMessage());
            return ResponseEntity.status(401).body("Invalid token");
        }
        if(phone.equals(profile.getPhone())) {
            logger.info("Phone Match, Saving Profile details");
            profileRepository.save(profile);
            return ResponseEntity.ok("Profile added Successfully");
        }

        return ResponseEntity.status(401).body("Invalid Phone Number");



    }
}
