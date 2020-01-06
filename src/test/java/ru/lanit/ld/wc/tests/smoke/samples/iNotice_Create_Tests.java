package ru.lanit.ld.wc.tests.smoke.samples;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;
import ru.lanit.ld.wc.model.Users;
import ru.lanit.ld.wc.pages.WorkArea;
import ru.lanit.ld.wc.pages.LoginPage;
import ru.lanit.ld.wc.tests.TestBase;

public class iNotice_Create_Tests extends TestBase {

    WorkArea instSection;
    UserInfo instructionInitiator ; // app.userList.anyUser(1); //или любой
    Users instructionReceivers ;// получатель = любые пользователи (число = кол-во получателей)(обязательный)
    Instruction sendedInstruction,newInstruction;

    @BeforeClass
    public void before() {
    // создаем фронтом, проверяем бэком


        instructionInitiator = app.focusedUser; // app.userList.anyUser(1); //или любой

        //lastURL=Сообщения/Входящая для инициатора сообщения
        instructionInitiator.getUserApi().makeHomeAsLastUrl();
        //вид по умолчанию для инициатора сообщения
        instructionInitiator.getUserApi().setViewState(app.defaultViewState, "Instruction", 1999);


        //instructionReceivers =app.userList.anyUser(1);// получатель = любые пользователи (число = кол-во получателей)(обязательный)
        //instructionReceivers =app.userList.anyUserExcept(1,instructionInitiator); //.anyUser(1);// получатель = любые пользователи (число = кол-во получателей)(обязательный)

        LoginPage lp = new LoginPage();
        instSection = lp.open().loginAs(instructionInitiator);


        /*newInstruction = new Instruction(instructionInitiator.getUserTypes().getAnyNoticeType());
        newInstruction
                .withInitiatorID(new int[]{instructionInitiator.getId()}) //отправитель=focusedUser (обязательный)
                .withText("Пример уведомления. Создано в UI: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))) // текст сообщения + тек.время. Если не задано по умолчанию = текст из типа сообщения.
                .withComment("Без комментариев...") // комментарий. Если не задано по умолчанию = не заполнено
                .withSubject("UI автотест") // тема сообщения. Если не задано по умолчанию = тема из типа сообщения
                .withReceiverID(instructionReceivers.Ids());// получатель = любые пользователи (число = кол-во получателей)(обязательный)


        //NewInstructionPage newInstructionPage = instSection.actionPanel.createNewByPlusButton(newInstruction, app);
        sleep(5000);

        newInstructionPage.fillForm(newInstruction, app, true);
        //newInstructionPage.sendButton.click();
        sleep(5000);

        FolderList fl=instructionInitiator.getUserApi().getFolderList(2101,1);
        sendedInstruction=fl.items.get(0);

        sendedInstruction=instructionInitiator.getUserApi().getInstruction(sendedInstruction.getInstructionId());
        logger.info("instruction : " + newInstruction.toString());
*/
    }

    @DataProvider
    public Object[][] Notice() {

       // return new Object[][]{new Object[]{newInstruction}};
        return new Object[][] {
                {newInstruction, sendedInstruction}
        };
    }

@Ignore
    @Test(dataProvider = "Notice", priority = 3,invocationCount = 1, description = "Проверить текст сообщения")
    public void apiNoticeText(Instruction newInstruction,Instruction sendedInstruction) {

        Assert.assertEquals(sendedInstruction.getText(),newInstruction.getText());
    }

    @Ignore
    @Test(dataProvider = "Notice", invocationCount = 1, priority = 3,description = "Проверить комментарий")
    public void apiNoticeComment(Instruction newInstruction,Instruction sendedInstruction) {

        Assert.assertEquals(sendedInstruction.getComment(),newInstruction.getComment());
    }
    @Ignore
    @Test(dataProvider = "Notice", invocationCount = 1,priority = 3, description = "Проверить тему")
    public void apiNoticeSubject(Instruction newInstruction,Instruction sendedInstruction) {

        Assert.assertEquals(sendedInstruction.getSubject(),newInstruction.getSubject());
    }
    @Ignore
    @Test(dataProvider = "Notice", invocationCount = 1,priority = 3, description = "Проверить инициатора")
    public void apiInitiator(Instruction newInstruction,Instruction sendedInstruction) {

        Assert.assertEquals(sendedInstruction.getInitiatorID(),newInstruction.getInitiatorID());
    }
    @Ignore
    @Test(dataProvider = "Notice", invocationCount = 1, priority = 3,description = "Проверить получателя")
    public void apiReceiver(Instruction newInstruction,Instruction sendedInstruction) {

        Assert.assertEquals(sendedInstruction.getReceiverID(),newInstruction.getReceiverID());
    }
    @Ignore
    @Test(dataProvider = "Notice", invocationCount = 1,priority = 3, description = "Проверить ID тип уведомления")
    public void apiInstructionTypeId(Instruction newInstruction,Instruction sendedInstruction) {

        Assert.assertEquals(sendedInstruction.getInstructionTypeId(),newInstruction.getInstructionTypeId());
    }
    @Ignore
    @Test(dataProvider = "Notice", invocationCount = 1, priority = 3,description = "Проверить ID статуса")
    public void apiInstructionState(Instruction newInstruction,Instruction sendedInstruction) {

        Assert.assertEquals(sendedInstruction.getState(),2);
    }
    @Ignore
    @Test(dataProvider = "Notice", invocationCount = 1, priority = 3,description = "Проверить название статуса")
    public void apiInstructionStateName(Instruction newInstruction,Instruction sendedInstruction) {

        Assert.assertEquals(sendedInstruction.getStateName(),"Активно");
    }


}
