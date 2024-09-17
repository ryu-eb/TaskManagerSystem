package jp.eightbit.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TaskManagerSystemApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
			return application.sources(TaskManagerSystemApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TaskManagerSystemApplication.class, args);
	}

}
