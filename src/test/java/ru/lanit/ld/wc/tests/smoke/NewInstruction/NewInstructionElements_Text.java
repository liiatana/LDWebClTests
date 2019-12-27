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

public class NewInstructionElements_Text extends TestBase {

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
    @Story(value = "Teкст")
    @Test(  dataProvider = "newInstructionType",
            groups="NewInstructionTests",
            description ="Проверка наличия поля для Текста на форме" )
    public void subjectElementExists(InstructionType instructionType)
    {
        wa.newInstructionPage= wa.openNew(instructionType);

        wa.newInstructionPage.text.should(Condition.exist);
        wa.newInstructionPage.text.shouldHave(Condition.type("textarea"));
        wa.newInstructionPage.text.shouldHave(Condition.attribute("maxlength","4000"));
     }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] defaultTypeText() {

        return new Object[][]{
                new Object[]{app.focusedUser.getUserTypes().getAnyWithDefaultSubjectEmpty()},
                new Object[]{app.focusedUser.getUserTypes().getAnyWithDefaultSubjectNonEmpty()}
        };
    }


    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Teкст")
    @Test(  dataProvider = "defaultTypeText",
            groups="NewInstructionTests",
            priority = 1,
            description ="Проверка значения по умолчанию для поля Текст" )
    public void defaultSubject(InstructionType instructionType)
    {
        wa.newInstructionPage= wa.openNew(instructionType);

        wa.newInstructionPage.text.shouldHave(Condition.value(instructionType.getTemplateTextE()));
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @AfterMethod
    private void afterEachMethod() {
        wa.newInstructionPage.exitWithoutSaving();
    }

}
