package org.bspv.sample;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@EnableResourceServer
@SpringBootApplication
public class BspvSampleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BspvSampleServiceApplication.class, args);
    }

    @RestController
	class ServiceInstanceRestController {

	    @Autowired
	    private DiscoveryClient discoveryClient;

	    @RequestMapping("/service-instances/{applicationName}")
	    public List<ServiceInstance> serviceInstancesByApplicationName(
	            @PathVariable String applicationName) {
	        return this.discoveryClient.getInstances(applicationName);
	    }
    }
    
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
