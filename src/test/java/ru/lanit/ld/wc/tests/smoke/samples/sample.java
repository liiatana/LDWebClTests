package ru.lanit.ld.wc.tests.smoke.samples;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.enums.RefreshMessageDefaultSettings;
import ru.lanit.ld.wc.enums.SendTypes;
import ru.lanit.ld.wc.model.InstructionType;
import ru.lanit.ld.wc.model.MessageDefaultSettings;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.NewInstructionPage;
import ru.lanit.ld.wc.pages.WorkArea;
import ru.lanit.ld.wc.tests.TestBase;

import static com.codeborne.selenide.Selenide.$$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class sample extends TestBase {
    private WorkArea wa;


    @DataProvider
    public Object[][] User() {
        UserInfo user = app.userList.users.get(1);
        return new Object[][]{new Object[]{user}};
    }


    @Epic(value = "Sample")
    @Feature(value = "Любая")
    @Test(dataProvider = "User",
            groups="Sample",
            description ="Sample1" )
    public void sample1(UserInfo user) throws InterruptedException {



        InstructionType type1=user.getUserTypes().getAnyTaskType();

        MessageDefaultSettings adminDefaultMessageSettingsForType = user
                .getUserApi().getDefaultMessageSettingsForType(type1,false,true);

        MessageDefaultSettings userDefaultMessageSettingsForType = user
                .getUserApi().getDefaultMessageSettingsForType(type1,false,false);

        //adminDefaultMessageSettingsForType.withSendType(SendTypes.PARALLEL);
        //userDefaultMessageSettingsForType.withSendType(SendTypes.CHAIN);

        //user.getUserApi().patchSettings("messageDefaultSettings",adminDefaultMessageSettingsForType.toJson(type1,false)) ;
        user.getUserApi().patchSettings(
                "messageDefaultSettings",
                userDefaultMessageSettingsForType.withSendType(SendTypes.PARALLEL).toJson(type1,false),
                false);

        app.userList.getAnyAdmin().getUserApi().patchSettings(
                "messageDefaultSettings",
                adminDefaultMessageSettingsForType.withSendType(SendTypes.CHAIN).toJson(type1,false),
                true);

        //userDefaultMessageSettingsForType.withSendType(SendTypes.CHAIN);

        app.userList.getAnyAdmin().getUserApi().patchSettings("RefreshMessageDefaultSettings", RefreshMessageDefaultSettings.YES.toString(),true);

        LoginPage lp = new LoginPage();
        wa = lp.open().loginAs(user);
        wa.newInstructionPage= wa.openNew(type1);

        assertThat(wa.newInstructionPage.getActiveSendType(), equalTo(SendTypes.PARALLEL));
    }


    @AfterClass
    public void after() {

    }
}
