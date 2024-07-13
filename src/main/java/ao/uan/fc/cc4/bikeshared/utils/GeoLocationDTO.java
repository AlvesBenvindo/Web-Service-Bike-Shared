package ao.uan.fc.cc4.bikeshared.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoLocationDTO {
    private String pais; //country
    private String provincia; //state
    private String municipio; //city
    private String distrito; //suburb
    private String avenida; //road
}
