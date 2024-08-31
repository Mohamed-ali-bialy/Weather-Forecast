package WeatherPackage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperClass {
    MobileSelectors selectors = new MobileSelectors();

    public void toggleTemperatureUnit(Object currentUnit, AndroidDriver driver) {
        switch (currentUnit.toString()) {
            case "°F":
                driver.findElement(selectors.settingsBarButton).click();
                driver.findElement(selectors.unitSettings).click();
                driver.findElement(selectors.temperatureUnitDropDownUnit).click();
                driver.findElement(selectors.selectTemperatureUnitFromDropDown("C")).click();
                driver.findElement(selectors.doneButton).click();
                break;
            case "°C":
                driver.findElement(selectors.settingsBarButton).click();
                driver.findElement(selectors.unitSettings).click();
                driver.findElement(selectors.temperatureUnitDropDownUnit).click();
                driver.findElement(selectors.selectTemperatureUnitFromDropDown("F")).click();
                driver.findElement(selectors.doneButton).click();
                break;
        }
    }

    public Object getTemperature(AndroidDriver driver) {

        return driver.findElement(selectors.temperatureFromHomeScreen).getText();
    }

    public Object getTemperatureUnit(AndroidDriver driver) {

        return driver.findElement(selectors.temperatureUnitFromHomeScreen).getText();
    }

    public double getDoubleValue(Object object){
        String s = object.toString();
        return Double.parseDouble(s);
    }

    public int getIntegerValue(Object object){
        String s = object.toString();
        return Integer.parseInt(s);
    }

    public static int calculateTemperatureInFahrenheitFloored(double tempC) {
        double tempF = (tempC * 9 / 5) + 32;
        return (int) Math.round(tempF);
    }



    public void changeTimeFormat(int targetFormat, AndroidDriver driver) {
        if (targetFormat != 12 && targetFormat != 24) {
            throw new IllegalArgumentException("Hour format must be either 12 or 24");
        }
        driver.findElement(selectors.settingsBarButton).click();
        driver.findElement(selectors.unitSettings).click();
        driver.findElement(selectors.timeFormatDropDownSettings).click();
        driver.findElement(selectors.selectTimeFormatButtonFromDropDown(targetFormat)).click();
        driver.findElement(selectors.doneButton).click();
    }

    public Object getTimeAndDate(AndroidDriver driver){
        return driver.findElement(selectors.getTimeFromHomeScreen).getText();
    }

    public static String extractTime(String input) {
        // Regular expression to match time with optional AM/PM
        String regex = "\\d{1,2}:\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group();
        }

        return "";
    }



    public static String convertTo12HourFormat(String time24) {
        // Split the input time into hour and minutes
        String[] parts = time24.split(":");
        int hour24 = Integer.parseInt(parts[0]);
        String minutes = parts[1];

        // Convert hour to 12-hour format
        int hour12 = hour24 % 12;
        if (hour12 == 0) {
            hour12 = 12; // Midnight case
        }

        // Format the hour to be two digits
        String formattedHour = String.format("%02d", hour12);

        // Return the formatted time
        return formattedHour + ":" + minutes;
    }


    public List<String> getAppearedChanceOfRainText(AndroidDriver driver){
        List<AndroidElement> chanceOfRainElements;
        chanceOfRainElements = driver.findElements(selectors.chanceOfRainValue);
        List<String> list = new ArrayList<>();
        for(int i = 0 ; i < chanceOfRainElements.size(); i++)
        {
            list.add(chanceOfRainElements.get(i).getText());
        }
        return list;

    }

    public List<String> getAppearedHumidityText(AndroidDriver driver){
        List<AndroidElement> chanceOfRainElements;
        chanceOfRainElements = driver.findElements(selectors.humidityValue);
        List<String> list = new ArrayList<>();
        for(int i = 0 ; i < chanceOfRainElements.size(); i++)
        {
            list.add(chanceOfRainElements.get(i).getText());
        }
        return list;
    }





}
