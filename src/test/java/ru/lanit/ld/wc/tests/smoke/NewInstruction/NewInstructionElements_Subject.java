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

public class NewInstructionElements_Subject extends TestBase {

    private WorkArea wa;

    @BeforeClass
    public void before() {
        LoginPage lp = new LoginPage();
        wa = lp.open().loginAs(app.focusedUser);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] newInstructionType() {

        return new Object[][]{
                new Object[]{app.focusedUser.getUserTypes().getAnyNoticeType()},
                new Object[]{app.focusedUser.getUserTypes().getAnyTaskType()}
        };
    }

    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Тема")
    @Test(  dataProvider = "newInstructionType",
            groups="NewInstructionTests",
            description ="Проверка наличия текстового поля ТЕМА(тип=text) на форме" )
    public void subjectElementExists(InstructionType instructionType)
    {
        wa.newInstructionPage= wa.openNew(instructionType);

        wa.newInstructionPage.subject.should(Condition.exist);
        wa.newInstructionPage.subject.shouldHave(Condition.type("text"));

     }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] defaultTypeText() {

        return new Object[][]{
                new Object[]{app.focusedUser.getUserTypes().getAnyType()}

        };
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Тема")
    @Test(  dataProvider = "defaultTypeText",
            groups="NewInstructionTests",
            priority = 1,
            description ="Проверка значения по умолчанию для поля ТЕМА" )
    public void defaultSubject(InstructionType instructionType)
    {
        wa.newInstructionPage= wa.openNew(instructionType);
        wa.newInstructionPage.subject.shouldHave(Condition.value(instructionType.getName()));
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @AfterMethod
    private void afterEachMethod() {
        wa.newInstructionPage.exitWithoutSaving();
    }

}
