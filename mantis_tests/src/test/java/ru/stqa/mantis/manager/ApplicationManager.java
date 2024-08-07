package ru.stqa.mantis.manager;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ApplicationManager {

    private WebDriver driver;
    private String browser;
    private Properties properties;
    private SessionHelper sessionHelper;

    public void init(String browser, Properties properties) {
        this.browser = browser;
        this.properties = properties;
    }

    public WebDriver driver(){
        if (driver == null) {
            if ("chrome".equals(browser)) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-debugging-port=9222");
                //options.addArguments("start-maximized"); // open Browser in maximized mode
                //options.addArguments("disable-infobars"); // disabling infobars
                // options.addArguments("--disable-extensions"); // disabling extensions
                //options.addArguments("--disable-gpu"); // applicable to windows os only
                // options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
                //options.addArguments("--no-sandbox"); // Bypass OS security model
                driver = new ChromeDriver(options);

            } else if ("firefox".equals(browser))
            {
                // put gecko driver to the path. it starts selenium, not firefox!
                System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
                driver = new FirefoxDriver();
            } else {
                throw new IllegalArgumentException(String.format("Unknown browser %s", browser));
            }
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            //firefox driver works only with url to index.php. otherwise failed with Unable to locate element: *[name='user']
            driver.get(properties.getProperty("web.baseUrl"));
        }
        return driver;
    }

    public SessionHelper session(){
        if (sessionHelper == null){
            sessionHelper = new SessionHelper(this);
        }
        return sessionHelper;
    }

}
