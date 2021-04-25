package tqs.airquality.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import com.fasterxml.jackson.databind.node.IntNode;
import tqs.airquality.model.AirData;

import java.io.IOException;

public class AirDataDeserializer {
 //   public AirDataDeserializer() {
   //     this(null);
   // }

   // public AirDataDeserializer(Class<?> vc) {
     //   super(vc);
 //   }

 //   @Override
  //  public AirData deserialize(JsonParser jp, DeserializationContext ctxt)
   //         throws IOException, JsonProcessingException {
   //     JsonNode node = jp.getCodec().readTree(jp);
   //     int aqi = (Integer) ((IntNode) node.get("aqi")).numberValue();
   //     int so2 = (Integer) ((IntNode) node.get("so2")).numberValue();
  //      int no2 = (Integer) ((IntNode) node.get("no2")).numberValue();
   //     int o3 = (Integer) ((IntNode) node.get("o3")).numberValue();
  //      int pm25 = (Integer) ((IntNode) node.get("pm25")).numberValue();
   //     int pm10 = (Integer) ((IntNode) node.get("pm10")).numberValue();

    //    return new AirData(aqi, so2, no2, o3, pm25, pm10);
  //  }
}