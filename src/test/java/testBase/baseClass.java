package testBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class baseClass {
    public WebDriver driver;
    public Properties configProp;
    public static Logger logger;

    @BeforeClass
    @Parameters({"browser", "os"}) // Parameterized for browser and OS
    public void setup(String browser, String os) throws IOException {
        // Initialize logger
        logger = LogManager.getLogger("BaseClass");

        // Load configuration properties
        configProp = new Properties();
        FileInputStream configFile = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties");
        configProp.load(configFile);

        logger.info("Tests running on OS: " + os);  // Log the OS parameter for later Grid use

        // Choose browser based on the XML parameter
        if (browser.equalsIgnoreCase("chrome")) 
        {
            driver = new ChromeDriver();
            logger.info("Launching Chrome Browser");
        } 
        else if (browser.equalsIgnoreCase("firefox")) 
        {
            driver = new FirefoxDriver();
            logger.info("Launching Firefox Browser");
        } 
        else 
        {
            throw new IllegalArgumentException("Unsupported browser specified: " + browser);
        }

        // Set up WebDriver configurations
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Launch the URL from the config file
        driver.get(configProp.getProperty("baseUrl"));
        logger.info("Navigating to: " + configProp.getProperty("baseUrl"));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        logger.info("Browser closed");
    }
}
