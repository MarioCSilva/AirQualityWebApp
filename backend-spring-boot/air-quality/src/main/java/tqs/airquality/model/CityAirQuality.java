package tqs.airquality.model;

import lombok.*;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CityAirQuality implements Serializable {
    @JsonProperty("city_name")
    private String cityName;

    private String timezone;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("state_code")
    private String stateCode;

    private double lon;

    private double lat;

    @JsonProperty("data")
    private AirData[] airData;
}