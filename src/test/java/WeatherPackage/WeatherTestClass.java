package WeatherPackage;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WeatherTestClass {
    DesiredCapabilities caps = new DesiredCapabilities();
    MobileSelectors selectors = new MobileSelectors();
    HelperClass helper = new HelperClass();
    String deviceName = "Infinix X682C";
    String appPackage = "com.info.weather.forecast";
    String appActivity = "com.info.weather.forecast.activity.SettingUnitActivity";
    String appiumServerURL = "http://127.0.0.1:8081/wd/hub";
    AndroidDriver driver;

    @BeforeMethod
    public void before() throws MalformedURLException{
        caps.setCapability("deviceName", deviceName);
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);//set app package
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);//set activity package
        driver = new AndroidDriver(new URL(appiumServerURL), caps);//set driver with appium server


        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);//implicit wait


        driver.findElement(selectors.doneButton).click();//press done
        driver.findElement(selectors.gotItButton).click();//press got it
        driver.findElement(selectors.allowPermissionWhileOnlyUsingApp).click();//allow permission


        for (int i = 0; i < 2; i++) {  // Attempt to find loading element multiple times
            try {
                MobileElement loadingElement = (MobileElement) driver.findElement(selectors.xPathContains("Loading data..."));
                if (loadingElement.isDisplayed()) {
                    System.out.println("Loading Element found!");
                    break;
                }
            } catch (Exception e) {
                System.err.println("Loading Element is Not Found !");
            }
        }

    }


    @Test(priority = 1)
    public void testChangeTemperatureUnit(){

        Object tempUnitAfterOpenApp = helper.getTemperatureUnit(driver);
        if(!("°C".equals(tempUnitAfterOpenApp.toString())))
        {
            helper.toggleTemperatureUnit(tempUnitAfterOpenApp, driver);//toggle temperature TO C after open app
        }

        //get temperature and unit before toggle
        Object temperatureUnitBeforeToggle = helper.getTemperatureUnit(driver);
        Object temperatureBeforeToggle = helper.getTemperature(driver);


        helper.toggleTemperatureUnit(temperatureUnitBeforeToggle, driver);//toggle temperature


        //get temperature and unit after toggle
        Object temperatureUnitAfterToggle = helper.getTemperatureUnit(driver);
        Object temperatureAfterToggle = helper.getTemperature(driver);


       //calculate new temperature
        int calculatedTempreture = helper.calculateTemperatureInFahrenheitFloored(helper.getDoubleValue(temperatureBeforeToggle));

        Assert.assertEquals(helper.getIntegerValue(temperatureAfterToggle),calculatedTempreture,"Tempreture is not toggled from Celsius to Fahrenheit");
        Assert.assertEquals(temperatureUnitAfterToggle,"°F","Unit is not toggled from Celsius to Fahrenheit");

        driver.quit();
    }

    @Test(priority = 2)
    public void testChangeTimeFormat(){

        helper.changeTimeFormat(24,driver);//set time format to be 24

        Object TimeAndDateIn24Format = helper.getTimeAndDate(driver);//get time and date in 24 format

        helper.changeTimeFormat(12,driver);//set time format to be 12

        Object TimeAndDateIn12Format = helper.getTimeAndDate(driver);//get time and date in 12 format

        String extractTime24Format = helper.extractTime(TimeAndDateIn24Format.toString());//extract time
        String extractTime12Format = helper.extractTime(TimeAndDateIn12Format.toString());//extract time


        String timeConvertedFrom24FormatTo12fORMAT = HelperClass.convertTo12HourFormat(extractTime24Format);// convert time 24 to 12 format

        Assert.assertEquals(extractTime12Format,timeConvertedFrom24FormatTo12fORMAT,"Time Conversion From 24 Format to 12 Format is Wrong");

        driver.quit();
    }

    @Test(priority = 3)
    public void testChanceOfRainAndHumidityValuesAreDisplayed(){
        SoftAssert softAssert = new SoftAssert();


        List<String> chanceOfRainList = helper.getAppearedChanceOfRainText(driver);
        List<String> humidity = helper.getAppearedHumidityText(driver);
        System.out.println("chanceOfRainList" +chanceOfRainList);
        System.out.println("humidity" +humidity);

        softAssert.assertNotEquals(chanceOfRainList.size(),0,"Chance of rain is not displayed !");
        softAssert.assertNotEquals(humidity.size(),0,"humidity is not displayed ");
        softAssert.assertEquals(chanceOfRainList.size(),humidity.size(),"Chance of rain count is not equal to humidity count !");
        softAssert.assertEquals(chanceOfRainList.size(),6,"Only "+chanceOfRainList.size()+" Chance of rain are displayed !");
        softAssert.assertEquals(humidity.size(),6,"Only "+humidity.size()+" humidity are displayed !");

        for(int i = 0 ; i < chanceOfRainList.size() ;i ++)
        {
            String actualRainChance = chanceOfRainList.get(i);
            softAssert.assertTrue(actualRainChance.contains("%"), "Rain chance value does not contain '%'");

        }
        for(int i = 0 ; i < humidity.size() ;i ++)
        {
            String actualHumidity = humidity.get(i);
            softAssert.assertTrue(actualHumidity.contains("%"), "Humidity value does not contain '%'");
        }

        driver.quit();
        softAssert.assertAll();
    }


}
