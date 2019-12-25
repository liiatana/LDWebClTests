package ru.lanit.ld.wc.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.pages.LoginPage;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {
    public Logger logger = LoggerFactory.getLogger(TestBase.class);


    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME), System.getProperty("browserSize", "1366x768")); //
    // в настроках запуска теста нужно дописать  -Dbrowser=firefox( в поле VM)
    // -DbrowserSize="1024х768"
    //-Dbrowser=firefox -DbrowserSize=1800x1400
    //gradlew cleanTest test
    //gradlew cleanTest test -Pbrowser=firefox

    @BeforeSuite
    public void setUp() throws Exception {

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
        app.init();

        app.allureManager.addEnviromentInfo("BackVersion", app.userList.users.get(0).getUserApi().getBackVersion());

        LoginPage lp = new LoginPage();

        app.allureManager.addEnviromentInfo("FrontVersion", lp.open().version.getText().substring(2));

        app.focusedUser.getUserApi().makeHomeAsLastUrl();

    }


    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
        SelenideLogger.removeListener("AllureSelenide");
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        //logger.info("start " + m.getName() + " with parameters " + Arrays.asList(p).toString());
        logger.info("Start test: " + m.getName() + " with parameters " + Arrays.asList(p));

        //lastURL=Сообщения/Входящая
        app.focusedUser.getUserApi().makeHomeAsLastUrl();

    }

    @AfterMethod(alwaysRun = true) //@AfterMethod //после каждого меnода
    public void logTestStop(Method m) {
        logger.info("End test: " + m.getName());
    }


    /*@AfterClass //Л
    public void tearDown() {
        if (driver != null)
            driver.quit();

    }*/


}


