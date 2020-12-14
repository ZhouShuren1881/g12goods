package cn.edu.xmu.g12demogenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class G12DemoGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(G12DemoGeneratorApplication.class, args);
	}

}
