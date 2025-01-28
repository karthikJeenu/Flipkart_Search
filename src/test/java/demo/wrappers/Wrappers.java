package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;

public class Wrappers {

    // Method to search for text in a search box
    public static void Search_text(WebDriver driver, By element, String text) {
        try {
            WebElement searchBox = driver.findElement(element);
            String currentValue = searchBox.getAttribute("value");
            if (currentValue != null && !currentValue.isEmpty()) {
                // Send backspace to delete all characters
                for (int i = 0; i < currentValue.length(); i++) {
                    searchBox.sendKeys(Keys.BACK_SPACE);
                }
            }
            searchBox.sendKeys(text);
            searchBox.sendKeys(Keys.ENTER);
            System.out.println("Search performed with text: " + text);
  
        } catch (Exception e) {
            System.out.println("An error occurred while searching: "+ e.getMessage());
        }
    }

    public static void ClickonElement(WebDriver driver, WebElement element) {
    if (element != null) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Ensure wait is initialized
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            System.out.println("Clicked on the element successfully.");
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + e.getMessage());
        } catch (StaleElementReferenceException e) {
            System.out.println("Element is stale: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while clicking the element: " + e.getMessage());
        }
    } else {
        System.out.println("The WebElement is null. Cannot perform click action.");
    }
}


    // Method to display discounted iPhones greater than 17%
    public static void discountedIphones(WebElement locator) {
        try {
            List<WebElement> discounts = locator.findElements(By.xpath("//div[@class='UkUFwK']"));
            for (WebElement discount : discounts) {
                int percentage = Integer.parseInt(discount.getText().replaceAll("\\D", ""));
                if (percentage > 17) {
                    String title = locator.findElement(By.xpath("//div[@class='KzDlHZ']")).getText();
                    System.out.println("The title is: " + title + " and discount percent is: " + percentage + "%");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Failed to parse discount value: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while processing discounted iPhones: " + e.getMessage());
        }
    }
// Method to display the top-rated coffee mugs based on rating
public static void Top_Coffemugs(WebDriver driver,By locator) {
    try {
        
        List<WebElement> ratings = driver.findElements(locator);
        Set<Integer> uniqueRatings = new HashSet<>();
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);

        for(WebElement rating:ratings){
           int Userreview=Integer.parseInt(rating.getText().replaceAll("[^\\d]", ""));
            uniqueRatings.add(Userreview);
        }
        

        // Sorting ratings in descending order
        List<Integer> sortedRatings = new ArrayList<>(uniqueRatings);
        Collections.sort(sortedRatings, Collections.reverseOrder());

        // Displaying the top 5 coffee mugs or available ratings
        int topRatingsCount = Math.min(sortedRatings.size(), 5);
        for (int i = 0; i < topRatingsCount; i++) {
            String number = "(" + numberFormat.format(sortedRatings.get(i)) + ")";

            // Wait for title and image elements to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='" + number + "']/../../a[2]")));
            WebElement imageElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='" + number + "']/../../a[1]//img")));

            // Retrieve the title and image URL
            String title = titleElement.getText();
            String productImage = imageElement.getAttribute("src");

            // Log the title and image
            System.out.println((i + 1) + ". The title is " + title + " and image URL is " + productImage);
        }
    } catch (Exception e) {
       
        System.out.println("An error occurred while processing top coffee mugs: " + e.getMessage());
    }
}

  }
