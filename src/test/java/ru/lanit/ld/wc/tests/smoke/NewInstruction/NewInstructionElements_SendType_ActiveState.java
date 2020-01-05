package ru.lanit.ld.wc.tests.smoke.NewInstruction;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.enums.RefreshMessageDefaultSettings;
import ru.lanit.ld.wc.enums.SendTypes;
import ru.lanit.ld.wc.model.InstructionType;
import ru.lanit.ld.wc.model.MessageDefaultSettings;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.WorkArea;
import ru.lanit.ld.wc.tests.TestBase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class NewInstructionElements_SendType_ActiveState extends TestBase {

    private WorkArea wa;
    private UserInfo user;
    private UserInfo admin;

    @BeforeClass
    public void before() {
        admin = app.userList.getAnyAdmin();
        user = app.userList.getAnyUser();
        user.getUserApi().makeHomeAsLastUrl();

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @DataProvider
    public Object[][] taskType() {

        return new Object[][]{
                new Object[]{SendTypes.PARALLEL, SendTypes.CHAIN, RefreshMessageDefaultSettings.YES},
                new Object[]{SendTypes.PARALLEL, SendTypes.CHAIN, RefreshMessageDefaultSettings.NO},
                new Object[]{SendTypes.CHAIN, SendTypes.PARALLEL, RefreshMessageDefaultSettings.YES},
                new Object[]{SendTypes.CHAIN, SendTypes.PARALLEL, RefreshMessageDefaultSettings.NO}
        };
    }

    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Тип рассылки")
    @Test(dataProvider = "taskType",
            groups = "NewInstructionTests",
            description = "Проверка активного значения по умолчанию на форме задания в зависимости от настроек администратора")

    public void activeSendTypeForTask(SendTypes sendTypeforAdmin, SendTypes sendTypesForUser, RefreshMessageDefaultSettings defaultSettings) {
        InstructionType instructionType = user.getUserTypes().getAnyTaskType();
        System.out.println(instructionType.getId()+instructionType.getName());
        SendTypes expectedActiveType = setSendTypesForAdminAndUser(instructionType, sendTypeforAdmin, sendTypesForUser, defaultSettings);

        LoginPage lp = new LoginPage();
        wa = lp.open().loginAs(user);
        wa.newInstructionPage = wa.openNew(instructionType);

        assertThat(wa.newInstructionPage.getActiveSendType(), equalTo(expectedActiveType));
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Тип рассылки")
    @Test(  groups = "NewInstructionTests",
            description = "Проверка активного значения по умолчанию на форме уведомления")

    public void activeSendTypeForNotice() {
        LoginPage lp = new LoginPage();
        wa = lp.open().loginAs(user);
        wa.newInstructionPage = wa.openNew(user.getUserTypes().getAnyNoticeType());

        assertThat(wa.newInstructionPage.getActiveSendType(), equalTo(SendTypes.PARALLEL));
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @AfterMethod
    private void afterEachMethod() {
        //wa.newInstructionPage.exitWithoutSaving();
        user.getUserApi().makeHomeAsLastUrl();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private SendTypes setSendTypesForAdminAndUser(InstructionType type, SendTypes adminSendType, SendTypes userSendType, RefreshMessageDefaultSettings adminMainSettings) {
        admin.getUserApi().patchSettings("RefreshMessageDefaultSettings", adminMainSettings.toString(), true);

        MessageDefaultSettings adminDefaultMessageSettingsForType = admin
                .getUserApi().getDefaultMessageSettingsForType(type, false, true);

        MessageDefaultSettings userDefaultMessageSettingsForType = user
                .getUserApi().getDefaultMessageSettingsForType(type, false, false);

        admin.getUserApi().patchSettings(
                "messageDefaultSettings",
                adminDefaultMessageSettingsForType.withSendType(adminSendType).toJson(type, false),
                true);

        user.getUserApi().patchSettings(
                "messageDefaultSettings",
                userDefaultMessageSettingsForType.withSendType(userSendType).toJson(type, false),
                false);

        SendTypes expectedSendType = null;
        switch (adminMainSettings) {
            case NO:
                expectedSendType = userSendType;
                break;
            case YES:
                expectedSendType = adminSendType;
                break;
        }
        return expectedSendType;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}