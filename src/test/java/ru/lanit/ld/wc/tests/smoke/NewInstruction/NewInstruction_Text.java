package ru.lanit.ld.wc.tests.smoke.NewInstruction;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.InstructionType;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.WorkArea;
import ru.lanit.ld.wc.tests.TestBase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class NewInstruction_Text extends TestBase {

    private WorkArea wa;

    @BeforeClass
    public void before() {

        LoginPage lp = new LoginPage();
        wa = lp.open().loginAs(app.focusedUser);

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @DataProvider
    public Object[][] textGenerator() {

        return new Object[][]{
                new Object[]{"",app.focusedUser.getUserTypes().getAnyWithDefaultSubjectNonEmpty()},
                new Object[]{RandomStringUtils.randomAlphanumeric(10),app.focusedUser.getUserTypes().getAnyType()},
                new Object[]{RandomStringUtils.randomAlphabetic(10)+"\n"+RandomStringUtils.randomAlphabetic(20),app.focusedUser.getUserTypes().getAnyType()}
               };

    }

    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Teкст")
    @Test(  dataProvider = "textGenerator",
            groups="NewInstructionTests",
            description ="Проверка текста в сообщении при разной длине" )
    public void textInInstruction(String textToType,InstructionType instructionType)
    {
        wa.createMessage(new Instruction().withInstructionType(instructionType).withText(textToType),
                "send",app);
        Instruction createdInstruction =
                app.focusedUser.getUserApi().getLastInstruction(2101);

        assertThat(createdInstruction.getText(), equalTo(textToType));
     }


}
