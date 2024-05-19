package ao.uan.fc.cc4.bikeshared;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BikeSharedApplication {

	public static void main(String[] args) {

		SpringApplication.run(BikeSharedApplication.class, args);
		System.out.println("Web Service Bike Shared On Fire!!!");

	}

}
