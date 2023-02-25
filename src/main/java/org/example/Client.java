package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import org.example.dto.MeasurementDTO;
import org.example.dto.SensorDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class Client {
    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(getJwtTokenFromServer(new RestTemplate()));
    }

    public static void registrationSensors(RestTemplate restTemplate, SensorDTO[] sensorDTOS) {
        try {
            String url = "http://localhost:9090/sensors/registration";
            Map<Object, Object> map;
            for (SensorDTO sensorDTO : sensorDTOS) {
                map = Map.of("name", sensorDTO.getName());
                makePostMethodToServer(restTemplate, url, map);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addMeasurement(RestTemplate restTemplate,
                                      MeasurementDTO[] measurementDTOS,
                                      SensorDTO[] sensorDTOS) {
        try {
            String url = "http://localhost:9090/measurements/add";
            Map<Object, Object> map;
            for (int i = 0; i < measurementDTOS.length; i++) {
                map = new HashMap<>();

                measurementDTOS[i].setSensor(sensorDTOS[i % sensorDTOS.length]);

                map.put("temperature", measurementDTOS[i].getTemperature());
                map.put("rain", measurementDTOS[i].isRain());
                map.put("sensor", Map.of("name", measurementDTOS[i].getSensor().getName()));

                makePostMethodToServerWithJWT(restTemplate, url, map, getJwtTokenFromServer(restTemplate));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static String getJwtTokenFromServer (RestTemplate restTemplate) throws JsonProcessingException {
        String name = "Test1";
        String password = "123";
        String url = "http://localhost:9090/auth/login";
        Map<Object, Object> mapToSend = new HashMap<>();
        mapToSend.put("name", name);
        mapToSend.put("password", password);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(makePostMethodToServer(restTemplate, url, mapToSend));
        return jsonNode.get("jwt-token").asText();
    }
    private static String makePostMethodToServer (RestTemplate restTemplate,String url, Map<Object, Object> mapToSend) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(mapToSend, headers);
        return restTemplate.postForObject(url, entity, String.class);
    }
    private static String makePostMethodToServerWithJWT (RestTemplate restTemplate,String url,
                                                         Map<Object, Object> mapToSend, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(mapToSend, headers);
        return restTemplate.postForObject(url, entity, String.class);
    }
}
