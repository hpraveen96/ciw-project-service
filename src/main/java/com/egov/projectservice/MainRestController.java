package com.egov.projectservice;
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
    ProjectRepository projectRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("float/project")
    public ResponseEntity<String> addProfile(@RequestHeader("Authorization") String token,
                                             @RequestBody Project project) {
        logger.info("Received parameter for profile"+ project.toString());
        String phone;
        try {
            phone = tokenService.validateToken(token);
        }catch(WebClientResponseException e){
            logger.info("Token validation failed: " + e.getMessage());
            return ResponseEntity.status(401).body("Invalid token");
        }
        if(phone.equals(project.getPhone())) {
            logger.info("Phone Match, Saving Profile details");
            projectRepository.save(project);
            return ResponseEntity.ok("Project added Successfully");
        }

        return ResponseEntity.status(401).body("Invalid Phone Number");



    }
}
