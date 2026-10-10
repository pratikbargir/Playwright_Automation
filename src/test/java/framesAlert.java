import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;

public class framesAlert {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeMethod
    public void setUp() {

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.navigate("https://rahulshettyacademy.com/AutomationPractice/");

    }

    @Test
    public void alertHandling() {
        page.locator("#alertbtn").click(new Locator.ClickOptions().setTimeout(5000));
        page.onDialog(dialog -> {
            System.out.println("Alert message: " + dialog.message());
            dialog.accept();
        }); // Handle the alert dialog and accept it

        page.locator("#confirmbtn").click(new Locator.ClickOptions().setTimeout(5000));
        page.onDialog( dialog -> dialog.accept());// Handle the confirm dialog and accept it
    }

    @Test(description = "Handle Frames")
    public void frameHandling() {
        FrameLocator frame = page.frameLocator("#courses-iframe");// Locate the iframe with id "courses-iframe"
        String text = frame.locator(".text-left").textContent(); // Get the text content of the element with class "col-md-6" inside the iframe
        System.out.println("Text inside the frame: " + text);
        frame.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName("Register")).click();

        
    }
}
