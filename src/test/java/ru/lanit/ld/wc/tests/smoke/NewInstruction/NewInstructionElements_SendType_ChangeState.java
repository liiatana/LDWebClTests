package ru.lanit.ld.wc.tests.smoke.NewInstruction;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.*;
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

public class NewInstructionElements_SendType_ChangeState extends TestBase {

    private WorkArea wa;
    private UserInfo user;

    @BeforeClass
    public void before() {
        user = app.userList.getAnyUser();
        user.getUserApi().makeHomeAsLastUrl();

        LoginPage lp = new LoginPage();
        wa = lp.open().loginAs(app.focusedUser);

        wa.newInstructionPage= wa.openNew(user.getUserTypes().getAnyTaskType());

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Тип рассылки")
    @Test(  invocationCount = 2,
            groups = "NewInstructionTests",
            description = "Проверка смены состояния типа рассылки")

    public void changeActiveSendTypeForTask() {

        SendTypes activeSendType = wa.newInstructionPage.getActiveSendType();
        SendTypes newSendType=activeSendType.reverse(activeSendType);

        wa.newInstructionPage.setActiveSendType(newSendType);

        assertThat(wa.newInstructionPage.getActiveSendType(), equalTo(newSendType));
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @AfterClass
    private void afterCurrentClass(){
        wa.newInstructionPage.exitWithoutSaving();
        user.getUserApi().makeHomeAsLastUrl();

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}