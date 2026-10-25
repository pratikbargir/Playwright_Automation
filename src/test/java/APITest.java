import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.Test;

import java.util.HashMap;

public class APITest {

    @Test
    public void testAPI() {



        Playwright playwright = Playwright.create();
        APIRequestContext request = playwright.request().newContext();

        // Login API request
        HashMap<Object, Object> loginPayload = new HashMap<>();
        loginPayload.put("email", "pratikbargir2251@gmail.com");
        loginPayload.put("password", "Pratik@1234");

        APIResponse reaponse = request.post("https://api.eventhub.rahulshettyacademy.com/api/auth/login",
                RequestOptions.create().setData(loginPayload));

        if (reaponse.ok()) // Check if the response status code is in the 200-299 range
        {
            String responseBody = reaponse.text(); // Get the response body as a string
            System.out.println("Response Body: " + responseBody);
        } else {
            System.out.println("Request failed with status code: " + reaponse.status());
        }

        String token = JsonPath.read(reaponse.text(), "$.token"); // Extract the token from the response body using JSONPath
        System.out.println("Token: " + token);

        // Create Event API request

        HashMap<Object, Object> eventPayload = new HashMap<>();
        eventPayload.put("title","API Testing Traning");
        eventPayload.put("description", "Advance QA API Testing Traning with AI");
        eventPayload.put("category", "Conference");
        eventPayload.put("venue", "TCS Pune");
        eventPayload.put("city", "Pune");
        eventPayload.put("eventDate","2026-11-11T14:49:00.000Z");
        eventPayload.put("price","500");
        eventPayload.put("totalSeats","300");

        APIResponse eventResponse = request.post("https://api.eventhub.rahulshettyacademy.com/api/events", RequestOptions.create().setHeader("Authorization", "Bearer " + token).setData(eventPayload));
        System.out.println(eventResponse.text());
        String eventId = JsonPath.read(eventResponse.text(), "$.data.id").toString();
        System.out.println("Event ID: " + eventId);

        // Get Event API request
        APIResponse getEventResponse = request.get("https://api.eventhub.rahulshettyacademy.com/api/events?page=1&limit=12",
                RequestOptions.create().setHeader("Authorization", "Bearer " + token));
        System.out.println(getEventResponse.text());

        // Delete Event API request
        APIResponse deleteEventResponse = request.delete("https://api.eventhub.rahulshettyacademy.com/api/events/" + eventId,
                RequestOptions.create().setHeader("Authorization", "Bearer " + token));
        System.out.println(deleteEventResponse.text());

        // Re-requet the event to verify deletion
        APIResponse reEventResponse = request.get("https://api.eventhub.rahulshettyacademy.com/api/events?page=1&limit=12",
                RequestOptions.create().setHeader("Authorization", "Bearer " + token));
        String[] Id = JsonPath.read(reEventResponse.text(), "$.data[*].id").toString().split(",");

        for (String id : Id) {
            if (id.equals(eventId)) {
                System.out.println("Event is not deleted");
            }
       }

    }
}


