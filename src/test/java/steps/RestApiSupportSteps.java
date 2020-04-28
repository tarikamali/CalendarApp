package steps;

import au.com.utils.BackendHttpRequestClient;
import au.com.utils.Constants;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertTrue;


public class RestApiSupportSteps {
    public BackendHttpRequestClient httpRequest=new BackendHttpRequestClient();

    @Given("^I like to holiday in \"([^\"]*)\"$")
    public void iLikeToHolidayIn(String arg0) throws Throwable {
        Constants.city = arg0;
    }

    @When("^I look up the weather forecast$")
    public void iLookUpTheWeatherForecast() {
        httpRequest.isServiceOn();
    }

    @And("^the temperature is warmer than (\\d+) degrees$")
    public void theTemperatureIsWarmerThanDegrees(int arg0) throws Exception {
        assertTrue(httpRequest.verifyDateFromCity(Constants.city, Constants.checkDate, arg0));
    }

    @And("^I only like to holiday on \"([^\"]*)\"$")
    public void iOnlyLikeToHolidayOn(String arg0) {
        Constants.checkDate = httpRequest.getDateforComingDay(arg0.toUpperCase());
    }
}
