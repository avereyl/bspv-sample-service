package org.bspv.sample;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class SampleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleServiceApplication.class, args);
    }
    
   
    //FIXME TO MOVE
    @RestController
    @RequestMapping("/")
    class DummyController {

        @RequestMapping(method = RequestMethod.GET)
        @ResponseBody
        public String helloWorld(Principal principal) {
            return principal == null ? "Hello anonymous" : "Hello " + principal.getName();
        }

        @PreAuthorize("#oauth2.hasScope('openid') and hasRole('ROLE_ADMIN')")
        @RequestMapping(value = "secret", method = RequestMethod.GET)
        @ResponseBody
        public String helloSecret(Principal principal) {
            return principal == null ? "Hello anonymous" : "S3CR3T  - Hello " + principal.getName();
        }
    }
    
    
    
    
}
