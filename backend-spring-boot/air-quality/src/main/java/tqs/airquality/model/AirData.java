package tqs.airquality.model;

import lombok.*;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AirData implements Serializable {
    @JsonProperty("mold_level")
    private double moldLevel;
    private double aqi;
    private double so2;
    private double no2;
    private double co;
    private double o3;
    private double pm25;
    private double pm10;

    @JsonProperty("pollen_level_tree")
    private double pollenLevelTree;
    @JsonProperty("pollen_level_weed")
    private double pollenLevelWeed;
    @JsonProperty("pollen_level_grass")
    private double pollenLevelGrass;
    @JsonProperty("predominant_pollen_type")
    private String predominantPollenType;
}
