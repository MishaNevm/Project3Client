package org.example;

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
            HttpEntity<Map.Entry<String, String>> httpEntity;
            for (SensorDTO sensorDTO : sensorDTOS) {
                httpEntity = new HttpEntity<>(Map.entry("name", sensorDTO.getName()));
                System.out.println(restTemplate.postForObject(url, httpEntity, String.class));
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
            Map<String, Object> map;
            HttpHeaders headers;
            for (int i = 0; i < measurementDTOS.length; i++) {
                map = new HashMap<>();
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                measurementDTOS[i].setSensor(sensorDTOS[i % sensorDTOS.length]);

                map.put("temperature", measurementDTOS[i].getTemperature());
                map.put("rain", measurementDTOS[i].isRain());
                map.put("sensor", Map.of("name", measurementDTOS[i].getSensor().getName()));

                HttpEntity<Object> objectHttpEntity = new HttpEntity<>(map,headers);

                System.out.println(restTemplate.postForObject(url, objectHttpEntity, String.class));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
