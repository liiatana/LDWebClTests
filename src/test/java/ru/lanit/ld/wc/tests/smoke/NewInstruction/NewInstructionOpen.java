package ru.lanit.ld.wc.tests.smoke.NewInstruction;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.*;
import ru.lanit.ld.wc.model.InstructionType;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.WorkArea;
import ru.lanit.ld.wc.tests.TestBase;

import static com.codeborne.selenide.Selenide.sleep;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class NewInstructionOpen extends TestBase {

    private WorkArea wa;

    @BeforeClass
    public void before() {

        // авторизация
        LoginPage lp = new LoginPage();
        wa = lp.open().loginAs(app.focusedUser);

    }


    @DataProvider
    public Object[][] newInstructionType() {
        InstructionType instructionType=app.focusedUser.getUserTypes().getAnyType();
        return new Object[][]{
                new Object[]{instructionType,String.format("%smessage/new/%s",app.baseUrl,instructionType.getId())}};
    }


    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Открытие формы")
    @Test(  dataProvider = "newInstructionType",
            groups="NewInstructionTests",
            description ="Открытие формы Новое сообщение через кнопку +" )
    public void openNewByPlusButton(InstructionType instructionType, String expectedURL)
    {
        wa.newInstructionPage= wa.actionPanel.PlusButtonClick( instructionType);
        sleep(1000);
        assertThat(com.codeborne.selenide.WebDriverRunner.url(),equalTo(expectedURL ));

    }

    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Открытие формы")
    @Test(  dataProvider = "newInstructionType",
            groups="NewInstructionTests",
            description ="Открытие формы Новое сообщение через кнопку Создать" )
    public void openNewByCreateButton(InstructionType instructionType,String expectedURL)
    {
        wa.newInstructionPage=wa.header.CreateButtonClick(instructionType);
        sleep(1000);
        assertThat(com.codeborne.selenide.WebDriverRunner.url(),equalTo(expectedURL ));

    }


    @AfterMethod
    private void afterEachMethod() {
        wa.newInstructionPage.exitWithoutSaving();
    }
}
