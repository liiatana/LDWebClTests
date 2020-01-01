package ru.lanit.ld.wc.tests.smoke.NewInstruction;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
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

public class NewInstructionElements_SendType_ActiveState extends TestBase {

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
                new Object[]{app.focusedUser.getUserTypes().getAnyNoticeType()}
        };
    }

    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Тип рассылки")
    @Test(  dataProvider = "taskType",
            groups="NewInstructionTests",
            description ="Проверка активного типа рассылки на форме уведомления" )
    public void activeSendTypeNotice(InstructionType instructionType)
    {
        wa.newInstructionPage= wa.openNew(instructionType);

        assertThat(wa.newInstructionPage.getActiveSendType(), equalTo(SendTypes.PARALLEL));
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider
    public Object[][] instructionType() {

        return new Object[][]{
                new Object[]{app.focusedUser.getUserTypes().getAnyType(),SendTypes.PARALLEL},
                new Object[]{app.focusedUser.getUserTypes().getAnyTaskType(),SendTypes.CHAIN},
        };

    }

    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Тип рассылки")
    @Test(  dataProvider = "instructionType",
            groups="NewInstructionTests",
            description ="Проверка текста и иконки для типа рассылки" )
    public void checkSendTypeIconAndText(InstructionType instructionType,SendTypes sType)
    {
        wa.newInstructionPage= wa.openNew(instructionType);

        wa.newInstructionPage.sendTypes.get(sType.getId()).$x("div").shouldHave(Condition.text(sType.getIcon())); //название иконки
        wa.newInstructionPage.sendTypes.get(sType.getId()).parent().$x("span").shouldHave(Condition.text(sType.getName())); //название рассылки
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @AfterMethod
    private void afterEachMethod() {
        wa.newInstructionPage.exitWithoutSaving();
    }

}
