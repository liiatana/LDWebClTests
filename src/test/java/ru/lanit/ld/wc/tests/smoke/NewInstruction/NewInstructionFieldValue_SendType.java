package ru.lanit.ld.wc.tests.smoke.NewInstruction;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.enums.InstructionTopFolders;
import ru.lanit.ld.wc.enums.RefreshMessageDefaultSettings;
import ru.lanit.ld.wc.enums.SendTypes;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.InstructionType;
import ru.lanit.ld.wc.model.MessageDefaultSettings;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.WorkArea;
import ru.lanit.ld.wc.tests.TestBase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class NewInstructionFieldValue_SendType extends TestBase {

    private WorkArea wa;
    private UserInfo user;
    private UserInfo admin;

    @BeforeClass
    public void before() {
        admin = app.userList.getAnyAdmin();
        admin.getUserApi().patchSettings("RefreshMessageDefaultSettings", RefreshMessageDefaultSettings.YES.toString(), true);

        user = app.userList.getAnyUser();
        user.getUserApi().makeHomeAsLastUrl();

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @DataProvider
    public Object[][] taskType() {

        return new Object[][]{
                //new Object[]{user.getUserTypes().getAnyTaskType(),SendTypes.PARALLEL},
                new Object[]{user.getUserTypes().getAnyTaskType(),SendTypes.CHAIN}

        };
    }

    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Тип рассылки")
    @Test(dataProvider = "taskType",
            groups = "NewInstructionTests",
            description = "Проверка типа рассылки в отправленном сообщении ( без смены типа на форме)")

    public void sendTypeForTaskWthioutChangingOnInstructionForm(InstructionType instructionType, SendTypes chosenSendType) {

        //установить тип рассылки = chosenSendType
        MessageDefaultSettings adminDefaultMessageSettingsForType = admin
                .getUserApi().getDefaultMessageSettingsForType(instructionType, false, true);

        admin.getUserApi().patchSettings(
                "messageDefaultSettings",
                adminDefaultMessageSettingsForType.withSendType(chosenSendType).toJson(instructionType, false),
                true);

        //авторизоваться
        LoginPage lp = new LoginPage();
        wa = lp.open().loginAs(user);

        //открыть форму Новое сообщение типа instructionType и отправить сообщение
        wa.createMessage(new Instruction(app).withInstructionType(instructionType),"send",app);

        //получить последнее отправленное сообщение
        Instruction createdInstruction =
                user.getUserApi().getLastInstruction(InstructionTopFolders.OUTCOMING.getId());

        //проверить. что в нем тип рассылки = chosenSendType
        assertThat(createdInstruction.getSendTypeAsEnum(), equalTo(chosenSendType));

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

        user.getUserApi().makeHomeAsLastUrl();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}