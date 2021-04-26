package tqs.airquality.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CacheStatus implements Serializable {
    private Integer numberHits;
    private Integer numberMisses;
    private double timeToLive;
}
