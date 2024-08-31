package WeatherPackage;

import org.openqa.selenium.By;

public class MobileSelectors {

    public By doneButton = By.id("com.info.weather.forecast:id/tv_button_done");
    public By gotItButton = By.id("com.info.weather.forecast:id/ll_got_it");
    public By allowPermissionWhileOnlyUsingApp = By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button");

    public By settingsBarButton = By.id("com.info.weather.forecast:id/iv_bt_navigation_setting");

    public By unitSettings = By.xpath("//android.widget.TextView[@text=\"Unit setting\"]");
    public By temperatureUnitDropDownUnit = By.id("com.info.weather.forecast:id/iv_temp_dropdown");
    public By temperatureFromHomeScreen = By.xpath("//android.widget.TextView[@resource-id=\"com.info.weather.forecast:id/iostv_temperature\"]");
    public By temperatureUnitFromHomeScreen = By.xpath("//android.widget.TextView[@resource-id=\"com.info.weather.forecast:id/tv_current_temper_unit\"]");
    public By getTimeFromHomeScreen = By.id("com.info.weather.forecast:id/tv_date");
    public By timeFormatDropDownSettings =By.id("com.info.weather.forecast:id/tv_timeformat_setting");

    public By selectTimeFormatButtonFromDropDown(int hourFormat){

        String str =  String.format("//android.widget.TextView[@text=\"%d Hour\"]", hourFormat);
        return By.xpath(str);
    }

    public By selectTemperatureUnitFromDropDown(String unit)
    {
        String xPath= String.format("(//android.widget.TextView[@text=\"%s\"])[1]", unit);
        return By.xpath(xPath);
    }
    public By xPathContains(String str){

        return  By.xpath("//*[contains(., '"+ str +"')]");
    }
    public By chanceOfRainValue = By.xpath("//android.widget.TextView[@resource-id='com.info.weather.forecast:id/tv_rain_chance']");

    public By humidityValue = By.xpath("//android.widget.TextView[@resource-id='com.info.weather.forecast:id/tv_humidity']");

}
