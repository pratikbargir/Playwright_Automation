import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class multiWindowHandling {

    Playwright playwright;
    Page page;
    Browser browser;
    BrowserContext context;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(); // Create a new browser context(window)like incognito window
        context.tracing().start( new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)); // Start tracing with screenshots, snapshots, and sources enabled
        page = context.newPage(); // Create a new page in the browser context
        page.navigate("https://rahulshettyacademy.com/loginpagePractise/");
    }

    @AfterMethod
    public void tearDown() {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip"))); // Stop tracing and save the trace to a file
        context.close(); // Close the browser context
        browser.close(); // Close the browser
        playwright.close(); // Close Playwright
    }

    @Test
    public void childWindowHandling()
    {
        Locator link = page.locator(".blinkingText");

        /*Code For Child Window Handling Started*/
        Page newPage= context.waitForPage(()-> link.first().click()); // Wait for the new page to open after clicking the link
        newPage.waitForLoadState(); // Wait for the new page to load completely
        String FullText = newPage.locator(".red").textContent(); // Get the text content of the element with class "red"
        String ExtractedMail = FullText.split("at ")[1].split(" ")[0]; // Extract the email address from the text content
       /*Code For Child Window Handling Ended*/

        page.getByLabel("Username").fill(ExtractedMail);
        String filledMail = page.getByLabel("Username").inputValue();
        System.out.println("Extracted Email: " + ExtractedMail);
        System.out.println("Filled Email: " + filledMail);
    }


}
