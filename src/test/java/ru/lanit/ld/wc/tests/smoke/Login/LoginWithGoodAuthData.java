package ru.lanit.ld.wc.tests.smoke.Login;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.pages.WorkArea;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

public class LoginWithGoodAuthData extends TestBase {

    private WorkArea wa;

    @DataProvider
    public Object[][] GoodUsers() {
        UserInfo user = app.focusedUser;
        return new Object[][]{new Object[]{user}};
    }


    @Epic(value = "Авторизация")
    @Feature(value = "По паре логин/пароль")
    @Test(dataProvider = "GoodUsers",
            groups="LoginTests",
            description ="Авторизация пользователя с верными данными" )
    public void loginWithGoodAuthenticationData(UserInfo user) {


        LoginPage lp = new LoginPage();
        wa = lp.open().loginAs(user);

        Assert.assertEquals(wa.header.getLastName().toUpperCase(), user.getLastName().toUpperCase());


    }

    @AfterClass
    public void after() {
        wa.header.exit();
    }
}
