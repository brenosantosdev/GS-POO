package br.fiap.breno562584;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public static void main(String[] args) {
	System.out.println("USER=[" + System.getenv("DB_USER") + "] tamanho da senha="
			+ (System.getenv("DB_PASSWORD") == null ? null : System.getenv("DB_PASSWORD").length()));
	SpringApplication.run(Breno562584Application.class, args);
}

@SpringBootApplication
public class Breno562584Application {

	public static void main(String[] args) {
		SpringApplication.run(Breno562584Application.class, args);
	}

}
