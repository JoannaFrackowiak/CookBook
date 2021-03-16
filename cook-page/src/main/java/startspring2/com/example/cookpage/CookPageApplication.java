package startspring2.com.example.cookpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class CookPageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookPageApplication.class, args);
	}

}
