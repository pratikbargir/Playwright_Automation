import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class GreenKart {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeMethod
    public void setUp(){

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.navigate("https://rahulshettyacademy.com/seleniumPractise/#/");
        page.setDefaultTimeout(2000);
    }

    @Test(description = "Search Vegitables and Add to Cart")
    public void searchVegitablesAndAddToCart() {
        Locator Products = page.locator(".product");
        //Product 1 add to cart
        Locator Product1 = Products.filter(new Locator.FilterOptions().setHasText("Grapes - 1 Kg"));
        Product1.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("ADD TO CART")).click();

        //Product 2 add to cart
        Locator Product2 = Products.filter(new Locator.FilterOptions().setHasText("Brocolli - 1 Kg"));
        Product2.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("ADD TO CART")).click();

        // Product 3 add to cart
        Locator Product3 = Products.filter(new Locator.FilterOptions().setHasText("Cucumber - 1 Kg"));
        Product3.locator(".increment").click();
        Product3.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("ADD TO CART")).click();

        page.locator(".cart-icon").click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("PROCEED TO CHECKOUT")).click();

       // Validate the products in the cart
        Locator p1 = page.locator("#productCartTables").filter(new Locator.FilterOptions().setHasText("Grapes - 1 Kg"));
        assertThat(p1).isVisible();
        Locator p2 = page.locator("#productCartTables").filter(new Locator.FilterOptions().setHasText("Brocolli - 1 Kg"));
        assertThat(p2).isVisible();
        Locator p3 = page.locator("#productCartTables").filter(new Locator.FilterOptions().setHasText("Cucumber - 1 Kg"));
        assertThat(p3).isVisible();

        // Proceed to place order
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Place Order")).click();

        // Select country and agree to terms
        page.locator("//*[@id=\"root\"]/div/div/div/div/div/select").selectOption("India");
        page.locator(".chkAgree").check();

        //Confirm the order
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();
    }

}
