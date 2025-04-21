package kz.diploma.kitaphub;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@OpenAPIDefinition(info = @Info(
//    title = "KitapHub api definition",
//    contact = @Contact(
//        name = "Muratali, Alikhan and Kairkeldi",
//        email = "amangalimuratali@gmail.com"
//    )
//))
public class KitaphubApplication {
  public static void main(String[] args) {
    SpringApplication.run(KitaphubApplication.class, args);
  }
}
