package org.example;

import org.example.dto.MeasurementDTO;
import org.example.dto.MeasurementDTOResponse;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DrawChart {
    public static void main(String[] args) {
        drawChart(Objects.requireNonNull(getMeasurementsFromServer()));
    }

    public static List<Double> getMeasurementsFromServer() {
        final RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:9090/measurements";

        MeasurementDTOResponse measurementDTOResponse = restTemplate.getForObject(url, MeasurementDTOResponse.class);
        if (measurementDTOResponse == null) return null;
        return measurementDTOResponse.getMeasurementDTOList()
                .stream().map(MeasurementDTO::getTemperature).collect(Collectors.toList());
    }

    public static void drawChart(List<Double> measurements) {
        XYChart chart = new XYChartBuilder().width(1700).height(1000).title("График")
                .xAxisTitle("Times").yAxisTitle("Temperatures").theme(Styler.ChartTheme.GGPlot2).build();
        double[] yData = measurements.stream().mapToDouble(Double::doubleValue).toArray();
        double[] xData = new double[yData.length];
        for (int i = 0; i < yData.length; i++) {
            xData[i] = i+1;
        }
        chart.addSeries("Measurements", xData, yData);
        new SwingWrapper<>(chart).displayChart();
    }
}
