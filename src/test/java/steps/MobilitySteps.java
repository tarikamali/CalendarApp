package steps;

import au.com.utils.Constants;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.PageFactory;
import components.screens.BaseClass;
import components.screens.HomeScreen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;


public class MobilitySteps extends BaseClass{
    public HomeScreen homeScreen;
    public MobilitySteps() {
    }

    @Before()
    public void setUp(Scenario scenario) {
        initialization();
        PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
        System.out.println("**********************************************");
        homeScreen= new HomeScreen(driver);
        System.out.println(scenario.getName()+" - scenario test execution started");
    }

    @After()
    public void tearDown(Scenario scenario) throws IOException {

        if (scenario.isFailed()) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String currentDir = System.getProperty("user.dir");
            FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
            byte[] fileContent = Files.readAllBytes(scrFile.toPath());
            scenario.embed(fileContent,"image/png");
        }
        Constants.NoResetApp=true;
        driver.quit();
        System.out.println(scenario.getName()+" - scenario test execution finished");
    }



    @Given("^I have launched the Calendar App$")
    public void iHaveLaunchedTheCalendarApp() {
        homeScreen.checkRightArrowAndClick();
        homeScreen.checkRightArrowAndClick();
        homeScreen.checkRightArrowAndClick();
        if(driver.findElements(MobileBy.xpath("//*[@text='GOT IT'][1]")).size()>0) {
            homeScreen.clickButton("GOT IT");
        }
        Assert.assertTrue(driver.findElements(MobileBy.id("com.google.android.calendar:id/date_picker_text_view")).size()>0);
    }

    @When("^It is not a weekend$")
    public void itIsNotAWeekend()  {
        Assert.assertTrue(homeScreen.checkIfWeekendToday());
    }

    @And("^It is not a public holiday$")
    public void itIsNotAPublicHoliday() {
        Assert.assertTrue(homeScreen.checkIfTodayIsNotHoliday());
    }


    @Then("^I want to book a meeting with the title \"([^\"]*)\"$")
    public void iWantToBookAMeetingWithTheTitle(String arg0) throws Throwable {
        homeScreen.enterTitleForMeeting(arg0);

    }

    @And("^I save the meeting$")
    public void iSaveTheMeeting() {
        homeScreen.saveMeeting();
    }


    @And("^I invite people \"([^\"]*)\"$")
    public void iInvitePeople(String arg0) throws Throwable {
        homeScreen.inviteParticipent(arg0);
    }

    @Then("^I Check if the meeting is created as expected \"([^\"]*)\"$")
    public void iCheckIfTheMeetingIsCreatedAsExpected(String arg0)  {
        Assert.assertTrue(driver.findElements(MobileBy.xpath("//android.view.View[@content-desc='"+arg0+"']")).size()>0);

    }

    @And("^Set Meeting duration as <(\\d+) Minutes> in the evening at \"([^\"]*)\"$")
    public void setMeetingDurationAsMinutesInTheEveningAt(int arg0, String arg1) throws Throwable {
        homeScreen.setMeetingDuration(arg0+"",arg1);
    }
}
