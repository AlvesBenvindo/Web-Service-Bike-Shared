// package ao.uan.fc.cc4.bikeshared.wsAsCliente.jOpenCage;

// import org.springframework.beans.factory.parsing.Location;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.boot.web.client.RestTemplateBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;
// import org.springframework.oxm.jaxb.Jaxb2Marshaller;
// import org.springframework.web.client.RestTemplate;

// import ao.uan.fc.cc4.bikeshared.wsAsCliente.ofStation.WSstation;

// @Configuration
// public class CoordinatesToPlaceConfiguration {

//     @Bean
//     public RestTemplate restTemplate(RestTemplateBuilder builder) {
// 		return builder.build();
// 	}

//     @Bean
//     public Location stationClient (RestTemplate restTemplate) {

//         Location location = restTemplate.getForObject(
//             "http://localhost:8080/api/random", 
//             Location.class
//             );

//         return location;
//     }

// }
