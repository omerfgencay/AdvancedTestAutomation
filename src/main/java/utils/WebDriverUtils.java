package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverUtils extends LoggerUtils {

    public static WebDriver setUpDriver() {
        WebDriver driver = null;

        //String sBrowser = "chrome"; we called chrome from the properties class
        String sBrowser = PropertiesUtils.getBrowser();
       // boolean bRemote = false;
        boolean bRemote = PropertiesUtils.getRemote() ;
       // boolean bHeadless = false;  //headless mode or not
        boolean bHeadless = PropertiesUtils.getHeadless();
       // String sHubUrl = "http://localhost:4444";
        String sHubUrl = PropertiesUtils.getHubUrl();
       // String sDriversFolder = "C:\\Selenium\\"; // the path of Selenium folder
        String sDriversFolder = PropertiesUtils.getDriversFolder();

        String sPathDriverChrome = sDriversFolder + "chromedriver.exe";
        String sPathDriverFirefox = sDriversFolder + "geckodriver.exe";
        String sPathDriverEdge = sDriversFolder + "msedgedriver.exe";

        log.debug("setUpDriver(Browser:" + sBrowser + ", " + "Remote: " + bRemote + ", " + "Headless: " + bHeadless + ")");
        try {
            switch (sBrowser) {
                case "chrome": {
                    ChromeOptions options = new ChromeOptions();
                    options.setHeadless(bHeadless);
                    if (bRemote) {
                        RemoteWebDriver  remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver =  remoteDriver ; // this is for remote machine
                    } else {
                        System.setProperty("webdriver.chrome.driver", sPathDriverChrome);
                        driver = new ChromeDriver(options); // this is for our local enviroment
                    }
                    break;
                }
                case "firefox" : {
                    FirefoxOptions options = new FirefoxOptions();
                    options.setHeadless(bHeadless);
                    options.addArguments("--window-size=1600x900");
                    if (bRemote) {
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;
                    } else {
                        System.setProperty("webdriver.gecko.driver", sPathDriverFirefox);
                        driver = new FirefoxDriver(options);
                    }
                    break;
                }
                case "edge" : {
                    EdgeOptions options = new EdgeOptions();
                    options.setHeadless(bHeadless);
                    options.addArguments("--window-size=1600x900");
                    if (bRemote) {
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        remoteDriver.setFileDetector(new LocalFileDetector());// bu kod yerelde bir file varsa remote machine de de onu okuyabilmek icindir
                        // ayrica images folder da bir resim oldugunu ve bunu test yaparken upload etmemiz gerektigini dusunun
                        //  bu kod ile diyoruz ki localde ki bu file i remote a transfer et. cunku bu resim bizim local machine imizde duruyor
                        driver = remoteDriver;
                    } else {
                        System.setProperty("webdriver.edge.driver", sPathDriverEdge);
                        driver = new EdgeDriver(options);
                    }
                    break;
                }
                default: {
                    Assert.fail("Cannot create driver! Browser '" + sBrowser + "' is not recognised!");
                }
            }
        } catch (MalformedURLException e) {
            Assert.fail("Cannot create driver! Path to browser " + sBrowser + "' driver is not correct! Message: " + e.getMessage());
        }
       /*
         There is a question here!
         for example we have a path like this
         C:/DCDCDCCDCD/Project/test/resources/documents/filename.doc
         my machine when we provide this url it will read but on remote machine it will not work because resources is not there
         we need to say selenium i mean we need to implement, when we provide to file thread to this path as local path thread this path as a path
         and then transfer that file then file can uploaded the application.
        */

        return null;

    }
    // setUpDriver()
    // quitDriver()


}
