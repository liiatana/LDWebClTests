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

public class NewInstructionElements_Comment extends TestBase {

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
    @Story(value = "Комментарий")
    @Test(  dataProvider = "newInstructionType",
            groups="NewInstructionTests",
            description ="Проверка наличия поля Комментарий на форме" )
    public void commentElementExists(InstructionType instructionType)
    {
        wa.newInstructionPage= wa.openNew(instructionType);

        wa.newInstructionPage.comment.shouldHave(Condition.type("textarea"));
     }


    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Комментарий")
    @Test(  dataProvider = "newInstructionType",
            groups="NewInstructionTests",
            priority = 1,
            description ="Проверка значения по умолчанию для поля Комментарий" )
    public void defaultComment(InstructionType instructionType)
    {

        wa.newInstructionPage= wa.openNew(instructionType);

        wa.newInstructionPage.comment.shouldHave(Condition.value(""));
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @AfterMethod
    private void afterEachMethod() {
        wa.newInstructionPage.exitWithoutSaving();
    }

}
