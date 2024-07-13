package ao.uan.fc.cc4.bikeshared.wsAsCliente.ofJOpenCage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ao.uan.fc.cc4.bikeshared.utils.Coordinates;
import ao.uan.fc.cc4.bikeshared.utils.GeoLocationDTO;

import java.util.List;
import java.util.Map;

@Component
public class ServiceGeoLocation{

    @Autowired
    private RestTemplate restTemplate;

    @Value("${opencage.api.key}")
    private String apiKey;

    // URL para solicitar a geoLocalização
    private static final String API_URL = "https://api.opencagedata.com/geocode/v1/json?q={lat}+{lng}&key={key}";

    public GeoLocationDTO getGeoLocation(String latitude, String longitude) {

        try {
            String url = API_URL
                    .replace("{lat}", latitude)
                    .replace("{lng}", longitude)
                    .replace("{key}", apiKey);

            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            Map<String, Object> results = (Map<String, Object>) ((List<?>) response.getBody().get("results")).get(0);
            Map<String, Object> components = (Map<String, Object>) results.get("components");

            GeoLocationDTO resposta = new GeoLocationDTO();

            resposta.setPais(""+components.get("country"));
            resposta.setProvincia(""+components.get("state"));
            resposta.setMunicipio(""+components.get("city"));
            resposta.setDistrito(""+components.get("suburb"));
            resposta.setAvenida(""+components.get("road"));

            return resposta;
        } catch (Exception e) {
            return null;
        }
    }

    //Pela fórmula de algébrica de Euclides para calcular a distância entre dois pontos
    public boolean calcDistanceEucliadiana (Coordinates a, Coordinates b, double radius) {
        /*
         * O valor do raio (radius) passado como parâmetro está em metros
         * distancia = √((x1 - x2)² + (y1 - y2)²)
         * x1 e y1 = coordenadas do ponto A
         * x2 e y2 = coordenadas do ponto B
        */
        double distance = Math.abs(Math.pow( (Math.pow((a.getLatitude()-b.getLatitude()), 2) + Math.pow((a.getLongitude()-b.getLongitude()), 2)), 0.5));
        
        /**
         * Convertendo o valor da distância que veio em quilómetro para metro;
        */
        distance = (distance*1000);
        System.out.println("distância encontrada em metros: "+distance);
        /*
         *Verificando se o local está dentro do raio (radius) passado por parâmetro
        */
        return ((radius-distance)>=0)? true: false;
    }

    //Pela fórmula dos haversine ou inverso do seno
    public double calcDistanceHaversine (Coordinates a, Coordinates b, double radius) {
        // O valor do raio (radius) passado como parâmetro está em metros
        // distancia = 2R × sen-¹(√[sen²((θ₂ - θ₁)/2) + cosθ₁ × cosθ₂ × sen²((φ₂ - φ₁)/2)])
        // fórmula de trigonométrica "haversine" para calcular a distância em linha recta entre dois pontos geográficos

        // Raio da Terra
        final double RAIO_TERRA = 6371;
        // Definindo os ângulos
        double theta1 = Math.toRadians(a.getLatitude());
        double theta2 = Math.toRadians(b.getLatitude());
        double phi1 = Math.toRadians(a.getLongitude());
        double phi2 = Math.toRadians(b.getLongitude());
        // Calculando os cossenos dos ângulos cosθ₁ e cosθ₂
        double cosTheta1 = Math.cos(theta1);
        double cosTheta2 = Math.cos(theta2);
        // Calculando os senos dos ângulos divididos por 2 sen²((θ₂ - θ₁)/2) e sen²((φ₂ - φ₁)/2)
        double senThetaMeio = Math.sin((theta2 - theta1) / 2);
        double senPhiMeio = Math.sin((phi2 - phi1) / 2);

        // Calculando o termo dentro da raiz quadrada √[sen²((θ₂ - θ₁)/2) + cosθ₁ × cosθ₂ × sen²((φ₂ - φ₁)/2)]
        double termoRaiz = (senThetaMeio * senThetaMeio) + cosTheta1 * cosTheta2 * (senPhiMeio * senPhiMeio);
        // Calculando o arco seno do termo dentro da raiz quadrada sen-¹(√[sen²((θ₂ - θ₁)/2) + cosθ₁ × cosθ₂ × sen²((φ₂ - φ₁)/2)])
        double arcSeno = Math.asin(Math.sqrt(termoRaiz));
        // Calculando a distância em linha recta entre dois pontos geográficos distancia = 2R × sen-¹(√[sen²((θ₂ - θ₁)/2) + cosθ₁ × cosθ₂ × sen²((φ₂ - φ₁)/2)])
        double distance = (2 * RAIO_TERRA) * arcSeno;

        // Convertendo o valor da distância que veio em quilómetro para metro;
        System.out.println("distância encontrada em quilometros: "+distance);
        distance = (distance*1000);
        System.out.println("distância encontrada em metros: "+distance);
        // Verificando se o local está dentro do raio (radius) passado por parâmetro
        return ((radius-distance)>=0)? distance: 0;
    }

}
