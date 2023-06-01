package utils;

import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils extends LoggerUtils {
    // we will implement properties here from common.properties files
    private static final String sPropertiesPath = "common.properties";
    private static final Properties properties = loadPropertiesFile();
    // create a common method for reading information from common.properties
    // we should create from Properties class because it is best way to read this informations
    // this is a common method reading own properties file
    public static Properties loadPropertiesFile(String sFilePath) {
        // common.properties icinde ki bilgiler key value seklinde yazilmalidir uniq olmalidir
        // bu sebepten oturu map kullanacagiz
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(sFilePath) ;
                // we used getClassLoader because it is loading run time so it is dynamic
        try {
            properties.load(inputStream);
        }
        catch (IOException e){
            Assert.fail("Connot load " + sFilePath + " file! Message: " + e.getMessage());
        }
        return properties ;
    }
    // we need to create overload method to read just our common properties
    // this is just internal use to read properties from common.properties
    private static Properties loadPropertiesFile(){
        return loadPropertiesFile(sPropertiesPath) ;
    }

    // we can create a common method to read any property that i want to read
    private static String getProperty(String sProperty){
        log.trace("getPoperty(" + sProperty + ")"); // log ekledik cunku log bize butun stepleri gosterecek
        String result = properties.getProperty(sProperty) ;
        Assert.assertNotNull(result, "Connot find property '" + sProperty + "' in " + sPropertiesPath + " file!");
        //Assert not null geldiginde bize yukardaki mesaji dondurecek
        return result;
    }

    public static String getBrowser(){ // when you create this method change String sBrowser = "chrome"; from WebDriverUtil class call this method
        return getProperty("browser") ;
    // we created this method to read browser from the common.properties file
    }

    public static String getEnvironment(){
        return getProperty("environment") ;

    }

    public static boolean getRemote(){
        String sRemote = getProperty("remote").toLowerCase() ;
        // parseBoolean eger true haricinde her hangi birsey gorurse false dondurur yes gorse dahi bu sebepten if condition ile belirttik
        if (!(sRemote.equals("true") || sRemote.equals("false"))){
        Assert.fail("Cannot convert 'Remote' property value '" + sRemote + "' to boolean value!");
        }
       return Boolean.parseBoolean(sRemote);
       // it was string we used wrap method to use boolean
        // Bu method u create ettikten sonra WebDriverUtil class in daki bRemote variable ini bu method ile cagirmamiz gerekiyor
    }
    public static boolean getHeadless(){ // when you create this method call WebDriverUtils from here
        String sHeadless = getProperty("headless").toLowerCase() ;

        if (!(sHeadless.equals("true") || sHeadless.equals("false"))){
            Assert.fail("Cannot convert 'Remote' property value '" + sHeadless + "' to boolean value!");
        }
        return Boolean.parseBoolean(sHeadless);
    }
    public static String getHubUrl(){ // when you create this method call WebDriverUtils from here
        return getProperty("hubUrl") ;
    }

    public static String getDriversFolder(){// when you create this method call WebDriverUtils from here
        return getProperty("driversFolder") ;
        // add ==>> driversFolder=C:\\Selenium\\ in the common.properties under the #Folder
    }
}
