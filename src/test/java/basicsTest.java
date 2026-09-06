import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class basicsTest {

    @Test
    public void demoTest() {
        Playwright playwright = Playwright.create();
        Browser chromeBrowser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = chromeBrowser.newPage();
        page.navigate("https://eventhub.rahulshettyacademy.com/login");
        String Title = page.title();
        System.out.println("Title of the page is: " + Title);
        assertThat(page).hasTitle("EventHub — Discover & Book Events");

    }


}
