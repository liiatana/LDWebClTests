package ru.lanit.ld.wc.tests.smoke.NewInstruction;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.InstructionType;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.WorkArea;
import ru.lanit.ld.wc.tests.TestBase;

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

        wa.newInstructionPage.sendTypes.shouldHaveSize(2);

        wa.newInstructionPage.sendTypes.first().$x("div").shouldHave(Condition.text("view_week")); //название иконки
        wa.newInstructionPage.sendTypes.first().parent().$x("span").shouldHave(Condition.text("Параллельная")); //название рассылки

        wa.newInstructionPage.sendTypes.last().$x("div").shouldHave(Condition.text("reorder")); //название иконки
        wa.newInstructionPage.sendTypes.last().parent().$x("span").shouldHave(Condition.text("Последовательная")); //название рассылки

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

        wa.newInstructionPage.sendTypes.shouldHaveSize(1);
        wa.newInstructionPage.sendTypes.first().$x("div").shouldHave(Condition.text("view_week")); //название иконки
        wa.newInstructionPage.sendTypes.first().parent().$x("span").shouldHave(Condition.text("Параллельная")); //название рассылки
        wa.newInstructionPage.sendTypes.first().has(Condition.attribute("class",
                "toggle-button square-sm-button elevation-0 v-btn v-btn--active theme--light white")); //активность кнопки

    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @AfterMethod
    private void afterEachMethod() {
        wa.newInstructionPage.exitWithoutSaving();
    }

}
