package ru.lanit.ld.wc.tests.smoke.Login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import static com.codeborne.selenide.Selenide.sleep;


public class LoginWithBadAuthData extends TestBase {


    @DataProvider
    public Object[][] badAuthUsers() {
        UserInfo user = app.focusedUser;

        return new Object[][]{
                new Object[]{new UserInfo(user.getLogin(), "")},
                new Object[]{new UserInfo(user.getLogin(), "wrong")},
                new Object[]{new UserInfo("", user.getPassword())},
                new Object[]{new UserInfo("Abc", user.getPassword())},
                new Object[]{new UserInfo("", "")}};
    }

    @Epic(value = "Авторизация")
    @Feature(value = "По паре логин/пароль")

    @Test(dataProvider = "badAuthUsers",
            groups="LoginTests",
            description ="Авторизация пользователя с НЕкорректными данными" )
    public void loginWithWrongAuthenticationData(UserInfo badUser) {


        LoginPage lp = new LoginPage();
        LoginPage loginPage = lp.open().failedLoginAs(badUser);
        sleep(1000);

        loginPage.errorText.shouldHave(Condition.text("Не удалось авторизоваться. Попробуйте еще раз!"));

    }


    @AfterClass
    public void after() {
        Selenide.refresh();
    }


}
