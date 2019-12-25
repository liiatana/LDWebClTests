package ru.lanit.ld.wc.tests.smoke;

import io.qameta.allure.Epic;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class __Version extends TestBase {

    private String version;

    @Epic(value = "Версия")


    @Test(groups="VersionTests",
            description = "Проверка версии front-end")
    public void getFrontVersion() {
        LoginPage lp = new LoginPage();

        version = System.getProperty("fversion", lp.open().version.getText().substring(2));

        assertThat(lp.open().version.getText().substring(2),equalTo(version));


    }

    @Epic(value = "Версия")

    @Test (groups="VersionTests",
            description = "Проверка версии back-end")
    public void getBackVersion() {
        version = System.getProperty("bversion", app.userList.users.get(0).getUserApi().getBackVersion());

        assertThat(app.userList.users.get(0).getUserApi().getBackVersion(),equalTo(version));
    }

}
