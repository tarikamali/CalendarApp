package components.screens;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class HomeScreen extends BaseClass{

    public AppiumDriver<? extends MobileElement> driver;

        @AndroidFindBy(id = "com.google.android.calendar:id/right_arrow_touch")
        public MobileElement nextButton;

        @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[2]/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView")
        public MobileElement tabNew;

        @AndroidFindBy(id = "com.google.android.calendar:id/input")
        public MobileElement title;

        @AndroidFindBy(id = "com.google.android.calendar:id/save")
        public MobileElement save;

        @AndroidFindBy(id = "com.google.android.calendar:id/start_time")
        public MobileElement startTime;

        @AndroidFindBy(id = "com.google.android.calendar:id/end_time")
        public MobileElement endTime;

        @AndroidFindBy(id = "android:id/pm_label")
        public MobileElement pm;

        @AndroidFindBy(id = "com.google.android.calendar:id/floating_action_button")
        public MobileElement floatingActionButton;

        @AndroidFindBy(xpath = "//hierarchy/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.ImageView")
        public MobileElement meetingButton;

        @AndroidFindBy(id = "com.google.android.calendar:id/guest_input")
        public MobileElement inviteBox;



    public HomeScreen(AppiumDriver<? extends MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);

    }

    public void checkRightArrowAndClick(){
        if(driver.findElements(MobileBy.id("com.google.android.calendar:id/right_arrow_touch")).size()>0)
        {
            nextButton.click();
        }
    }

    public boolean checkIfWeekendToday() {
        Calendar c = Calendar.getInstance();
        c.setTime(c.getTime());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return !(dayOfWeek==1) && !(dayOfWeek==7);
    }

    public boolean checkIfTodayIsNotHoliday() {
       return tabNew.getText().equalsIgnoreCase("Nothing planned. Tap to create.");
    }

    public void enterTitleForMeeting(String arg0) {
        floatingActionButton.click();
        meetingButton.click();
        title.sendKeys(arg0);

    }

    public void setMeetingDuration(String arg0, String arg1) throws Exception {
        startTime.click();
        waitSeconds(1);
        driver.findElement(MobileBy.AccessibilityId(arg1)).click();
        waitSeconds(1);
        driver.findElement(MobileBy.AccessibilityId("0")).click();
        pm.click();
        clickButton("OK");
        waitSeconds(1);
        endTime.click();
        waitSeconds(1);
        driver.findElement(MobileBy.AccessibilityId(arg1)).click();
        waitSeconds(1);
        driver.findElement(MobileBy.AccessibilityId(arg0)).click();
        pm.click();
        clickButton("OK");
    }

    public void saveMeeting() {
        save.click();
    }

    public void inviteParticipent(String arg0) {
        inviteBox.sendKeys(arg0);
    }
}
