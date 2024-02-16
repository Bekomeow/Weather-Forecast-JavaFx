package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class HelloController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text FeelsLike;

    @FXML
    private Text Humidity;

    @FXML
    private Text Pressure;

    @FXML
    private Text Temperature;

    @FXML
    private Text Wind;

    @FXML
    private Button checkButton;

    @FXML
    private TextField cityField;

    public HelloController() {
    }

    @FXML
    void initialize() {
        checkButton.setOnMouseEntered(mouseEvent -> {
            checkButton.setTextFill(Paint.valueOf("#0079b0"));
        });
        checkButton.setOnMouseExited(mouseEvent -> {
            checkButton.setTextFill(Paint.valueOf("#67cdff"));
        });


        checkButton.setOnAction((actionEvent) -> {
            String city = cityField.getText();
            String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid=9714754a807fba1c9a40b5eb1e9d8f8f&units=metric");

            if(!output.isEmpty()) {
                JSONObject jsonObject = new JSONObject(output);
                Temperature.setText("Temperature:   "+jsonObject.getJSONObject("main").getDouble("temp")+" °C");
                FeelsLike.setText("Feels Like:   "+jsonObject.getJSONObject("main").getDouble("feels_like")+" °C");
                Humidity.setText("Humidity:   "+jsonObject.getJSONObject("main").getInt("humidity")+" %");
                Wind.setText("Wind:   "+jsonObject.getJSONObject("wind").getInt("speed")+" m/s");
                Pressure.setText("Pressure:   "+jsonObject.getJSONObject("main").getDouble("pressure")+" mbar");
            }
        });
    }

    private static String getUrlContent(String urlAddress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }

            bufferedReader.close();
        } catch (Exception var6) {
            System.out.println("this city isn't found");
        }

        return content.toString();
    }
}
