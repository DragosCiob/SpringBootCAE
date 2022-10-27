package ro.siit.SpringBootCAE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

//@ComponentScan("ro.siit.SpringBootCAE")
public class SpringBootCaeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCaeApplication.class, args);
	}

}
