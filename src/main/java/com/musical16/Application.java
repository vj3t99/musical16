package com.musical16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.musical16.config.FileStorageProperties;

@Controller
@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class Application {

	@RequestMapping("/")
    @ResponseBody
    String home() {
      return "Hello World!";
    }
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
