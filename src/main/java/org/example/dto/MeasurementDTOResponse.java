package org.example.dto;

import java.util.List;

public class MeasurementDTOResponse {
    private List<MeasurementDTO> measurementDTOList;


    public List<MeasurementDTO> getMeasurementDTOList() {
        return measurementDTOList;
    }

    public void setMeasurementDTOList(List<MeasurementDTO> measurementDTOList) {
        this.measurementDTOList = measurementDTOList;
    }
}
