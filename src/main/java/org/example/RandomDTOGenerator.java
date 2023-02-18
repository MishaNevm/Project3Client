package org.example;

import org.example.dto.MeasurementDTO;
import org.example.dto.SensorDTO;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RandomDTOGenerator {

    private SensorDTO[] sensorDTOS;

    private MeasurementDTO[] measurementDTOS;

    public RandomDTOGenerator (int quantitySensorDTO, int quantityMeasurementDTO) {
        generatedRandomSensorDTO(quantitySensorDTO);
        generatedRandomMeasurementDTOS(quantityMeasurementDTO);
    }


    private void generatedRandomSensorDTO(int quantity) {
        sensorDTOS = new SensorDTO[quantity];
        byte[] array;
        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            array = new byte[7];
            random.nextBytes(array);
            String generatedString = new String(array, StandardCharsets.UTF_8);
            sensorDTOS[i] = new SensorDTO(generatedString);
        }
    }

    private void generatedRandomMeasurementDTOS (int quantity) {
        measurementDTOS = new MeasurementDTO[quantity];
        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            measurementDTOS[i] = new MeasurementDTO(random.nextFloat(-100,100)
                    , random.nextInt(3) > 1.5,
                    null);
        }
    }

    public SensorDTO[] getSensorDTOS() {
        return sensorDTOS;
    }

    public MeasurementDTO[] getMeasurementDTOS() {
        return measurementDTOS;
    }
}
