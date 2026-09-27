import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
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
        page.setDefaultTimeout(2000);// Default wait for all actions time out
        page.navigate("https://eventhub.rahulshettyacademy.com/login");
        PlaywrightAssertions.setDefaultAssertionTimeout(7000); // Default wait for all assertion time out
    }

    @Test(description = "Validate the title of the login page")
    public void validateLoginPageTitle() {

        page.getByLabel("Email").fill("rahulshetty1@yahoo.com");
        page.getByLabel("Password").fill("Magiclife1!");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();
        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
        page.getByLabel("Title").fill("Cargo Events");
        page.locator("//*[@id=\"admin-event-form\"]/div[1]/div[2]/textarea").fill("Cargo", new Locator.FillOptions().setTimeout(100) ); // Step Level wait for the description to be filled
        page.getByLabel("City").fill("Pune");
        page.getByLabel("Venue").fill("Orchid Hotel, Baner, Pune");
        page.getByLabel("Event Date & Time").fill("2026-11-11T08:30");
        page.getByLabel("Price ($)").fill("300");
        page.getByLabel("Total Seats").fill("100");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("+ Add Event")).click();
        assertThat(page.getByText("Event created!")).isVisible();
        page.locator("#nav-events").click( new Locator.ClickOptions().setTimeout(1000)); // Step Level wait for the events page to load
        Locator carts = page.locator("#event-card");
        Locator Event =carts.filter(new Locator.FilterOptions().setHasText("Cargo Events"));
        assertThat(Event).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(1000));// Step Level wait for the event to be visible in the list of events

        String AvailableSeats = Event.getByText("seats").innerText();
        System.out.println("Available Seats: " + AvailableSeats);

        Event.getByTestId("book-now-btn").click();

        assertThat(page.getByText("Book Tickets")).isVisible();

        page.getByLabel("Full Name").fill("Pratik Bargir");
        page.getByLabel("Email").fill("pratik.bhagvan.bargir@gmail.com");
        page.getByLabel("Phone Number").fill("7666385171");
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("+")).click();
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Confirm Booking")).click();
        assertThat(page.getByText("Booking Confirmed!")).isVisible();
        String BookingRef= page.locator(".booking-ref").innerText();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View My Bookings")).click();
        Locator ViewBooking = page.getByTestId("booking-card");
        Locator detailsFound = ViewBooking.filter(new Locator.FilterOptions().setHasText(BookingRef));

        assertThat(detailsFound).isVisible();
        page.locator("#nav-events").click();
        String AvailableSeatsAfterBooking = Event.getByText("seats").innerText();

        String BookingNumber = AvailableSeats.split(" ")[0];
        String AvailableSeatsAfterBookingNumber = AvailableSeatsAfterBooking.split(" ")[0];

        if (Integer.parseInt(AvailableSeatsAfterBookingNumber) == Integer.parseInt(BookingNumber) - 1) {
            System.out.println("Available seats decreased by 1 after booking.");
        } else {
            System.out.println("Available seats did not decrease correctly after booking.");
        }



    }


}
