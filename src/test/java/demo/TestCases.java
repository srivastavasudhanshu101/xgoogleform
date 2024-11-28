package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrappers; 

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */



     @Test
     public void testCase01() throws InterruptedException {
         // Navigate to the Google Form
         String formUrl = "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform";
         driver.get(formUrl); 
         Thread.sleep(3000);
         System.out.println("Navigated to Google Form: " + formUrl);
         
         wrappers = new Wrappers(driver);
     
         // Step 1: Enter "Crio Learner" in the first text box
         wrappers.enterText(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[1]/input"), "Crio Learner");
         System.out.println("Text is entered in the first text box");
     
         // Step 2: Enter a message with the current epoch time
         long epochTime = Instant.now().getEpochSecond();
         wrappers.enterText(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[2]/div/div/div[2]/div/div[1]/div[2]/textarea"), "I want to be the best QA Engineer! " + epochTime);
         System.out.println("Text is entered in the second text box");
     
         // Step 3: Select automation experience (radio button)
         wrappers.click(By.xpath("//*[@id='i16']/div[3]/div"));
         System.out.println("Selected automation experience radio button");
     
         // Step 4: Select checkboxes for Java, Selenium, and TestNG
         wrappers.selectCheckbox(By.xpath("//div[@id='i34']"));
         wrappers.selectCheckbox(By.xpath("//div[@id='i37']"));
         wrappers.selectCheckbox(By.xpath("//div[@id='i43']"));

         //Step 5: Enter Mr. In the form
         wrappers.click(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[1]/div[2]"));
         wrappers.click(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[2]/div[3]/span"));
        
     
       // Step 6: Enter the current date minus 7 days in the date field
LocalDate currentDateMinus7 = LocalDate.now().minusDays(7);
String formattedDate = currentDateMinus7.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

// Clear any pre-filled data in the date field, then enter the formatted date
By dateFieldLocator = By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[6]/div/div/div[2]/div/div/div[2]/div[1]/div/div[1]/input");
wrappers.clearField(dateFieldLocator); // Ensure any existing value is cleared
wrappers.enterText(dateFieldLocator, formattedDate); // Enter the formatted date
System.out.println("Date entered: " + formattedDate);

     
         // Step 7: Enter the time 07:30
         wrappers.enterText(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[7]/div/div/div[2]/div/div[1]/div[2]/div[1]/div/div[1]/input"), "07");
         wrappers.enterText(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[7]/div/div/div[2]/div/div[3]/div/div[1]/div/div[1]/input"), "30");
     
         // Step 8: Submit the form
         wrappers.click(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[3]/div[1]/div[1]/div/span/span"));
         System.out.println("Form submitted successfully");
     
         // Step 9: Capture and print the confirmation message
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
         String confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='vHW8K']"))).getText();
         System.out.println("Form submission confirmation: " + confirmationMessage);
     }





    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        wrappers = new Wrappers(driver);
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}