package tqs.airquality.model;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class City implements Serializable {
    @Id
    @JsonProperty("city_id")
    private int cityId;

    @JsonProperty("city_name")
    private String cityName;

    @JsonProperty("state_code")
    private String stateCode;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("country_full")
    private String countryFull;

    private double lat;

    private double lon;
}
