package org.example;

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
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        RandomDTOGenerator randomDTOGenerator = new RandomDTOGenerator(10, 1000);
        registrationSensors(restTemplate,randomDTOGenerator.getSensorDTOS());
        addMeasurement(restTemplate, randomDTOGenerator.getMeasurementDTOS(), randomDTOGenerator.getSensorDTOS());
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

                makePostMethodToServer(restTemplate, url, map);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void makePostMethodToServer (RestTemplate restTemplate,String url, Map<Object, Object> mapToSend) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(mapToSend, headers);
        restTemplate.postForObject(url, entity, String.class);
    }
}
