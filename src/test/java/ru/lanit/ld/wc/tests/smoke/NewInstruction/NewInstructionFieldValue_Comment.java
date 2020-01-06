package ru.lanit.ld.wc.tests.smoke.NewInstruction;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.enums.InstructionTopFolders;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.pages.WorkArea;
import ru.lanit.ld.wc.tests.TestBase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class NewInstructionFieldValue_Comment extends TestBase {

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
                new Object[]{""},
                new Object[]{RandomStringUtils.randomAlphanumeric(8)},
                new Object[]{RandomStringUtils.randomAlphabetic(5)+"\n"+RandomStringUtils.randomAlphabetic(8)}
               };

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Epic(value = "Раздел Сообщения")
    @Feature(value = "Новое сообщение")
    @Story(value = "Комментарий")
    @Test(  dataProvider = "textGenerator",
            groups="NewInstructionTests",
            description ="Проверка текста в сообщении при разной длине" )
    public void commentInInstruction(String textToType)
    {
        wa.createMessage(new Instruction(app).withComment(textToType),
                "send",app);
        Instruction createdInstruction =
                app.focusedUser.getUserApi().getLastInstruction(InstructionTopFolders.OUTCOMING.getId());

        assertThat(createdInstruction.getComment(), equalTo(textToType));
     }


}
