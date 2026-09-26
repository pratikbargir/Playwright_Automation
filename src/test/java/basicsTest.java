import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class basicsTest {

    Playwright playwright;
    Browser chromeBrowser;
    Page page;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        chromeBrowser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = chromeBrowser.newPage();
        page.navigate("https://eventhub.rahulshettyacademy.com/login");
    }

    @Test(description = "Validate the title of the login page")
    public void validateLoginPageTitle() {

        page.getByLabel("Email").fill("rahulshetty1@yahoo.com");
        page.getByLabel("Password").fill("Magiclife1!");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();
        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
        page.getByLabel("Title").fill("Cargo Events");
        page.locator("//*[@id=\"admin-event-form\"]/div[1]/div[2]/textarea").fill("Cargo");
        page.getByLabel("City").fill("Pune");
        page.getByLabel("Venue").fill("Orchid Hotel, Baner, Pune");
        page.getByLabel("Event Date & Time").fill("2026-09-11T08:30");
        page.getByLabel("Price").fill("300");
        page.getByLabel("Total Seats").fill("100");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("+ Add Event")).click();
        page.navigate("https://eventhub.rahulshettyacademy.com/events");
    }


}
