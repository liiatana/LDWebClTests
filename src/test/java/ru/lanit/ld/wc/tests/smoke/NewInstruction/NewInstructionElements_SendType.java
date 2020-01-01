package ru.lanit.ld.wc.tests.smoke.NewInstruction;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.enums.SendTypes;
import ru.lanit.ld.wc.model.InstructionType;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.WorkArea;
import ru.lanit.ld.wc.tests.TestBase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class NewInstructionElements_SendType extends TestBase {

    private WorkArea wa;

    @BeforeClass
    public void before() {
        LoginPage lp = new LoginPage();
        wa = lp.open().loginAs(app.focusedUser);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @DataProvider
    public Object[][] taskType() {

        return new Object[][]{
                new Object[]{app.focusedUser.getUserTypes().getAnyTaskType()}
        };
    }

    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Тип рассылки")
    @Test(  dataProvider = "taskType",
            groups="NewInstructionTests",
            description ="Проверка типа рассылок на форме задания" )
    public void sendTypesElementsTaskExists(InstructionType instructionType)
    {
        wa.newInstructionPage= wa.openNew(instructionType);

        assertThat(wa.newInstructionPage.getVisibleSendTypes(), equalTo(SendTypes.BOTH));

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] noticeType() {

        return new Object[][]{
                new Object[]{app.focusedUser.getUserTypes().getAnyNoticeType()}
        };
    }

    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Тип рассылки")
    @Test(  dataProvider = "noticeType",
            groups="NewInstructionTests",
            description ="Проверка типа рассылок на форме уведомления" )
    public void sendTypesElementsNoticeExists(InstructionType instructionType)
    {
        wa.newInstructionPage= wa.openNew(instructionType);

        assertThat(wa.newInstructionPage.getVisibleSendTypes(), equalTo(SendTypes.PARALLEL));
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @AfterMethod
    private void afterEachMethod() {
        wa.newInstructionPage.exitWithoutSaving();
    }

}
