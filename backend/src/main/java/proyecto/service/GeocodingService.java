package proyecto.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {

     private RestTemplate restTemplate;

     @Autowired
     public GeocodingService(RestTemplate restTemplate) {
          this.restTemplate = restTemplate;
     }

     public double geocodeAddressAltitud(String direccion, String comuna) {
          String url = String.format("https://nominatim.openstreetmap.org/search?q=%s, %s, Chile&format=json", direccion, comuna);

          String response = restTemplate.getForObject(url, String.class);

          JSONArray jsonResponse = new JSONArray(response);
          if (jsonResponse.length() > 0) {
               JSONObject firstResult = jsonResponse.getJSONObject(0);
               double lat = firstResult.getDouble("lat");

               return lat;
          } else {
               throw new RuntimeException("No results found for the address.");
          }
     }

     public double geocodeAddressLongitud(String direccion, String comuna) {
          String url = String.format("https://nominatim.openstreetmap.org/search?q=%s, %s, Chile&format=json", direccion, comuna);

          String response = restTemplate.getForObject(url, String.class);

          JSONArray jsonResponse = new JSONArray(response);
          if (jsonResponse.length() > 0) {
               JSONObject firstResult = jsonResponse.getJSONObject(0);
               double lon = firstResult.getDouble("lon");

               return lon;
          } else {
               throw new RuntimeException("No results found for the address.");
          }
     }
}
