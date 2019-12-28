package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.UserInfo;

import static com.codeborne.selenide.Selenide.*;

public class NewInstructionPage  extends  _BasePage {


    public CancelOK_Dialog dialog;
    public ChoosePersonForm choosePersonForm;
    public InstructionPanel instructionPanel;

    public SelenideElement subject = $(By.xpath("//input[@id=\"msgTheme\"]"));
    public SelenideElement text = $(By.xpath("//textarea[@name=\"input-10-1\"]"));
    public SelenideElement comment = $(By.xpath("//textarea[@name=\"input-2-1\"]"));
    private SelenideElement receiver = $(By.xpath("//input[@id=\"multiselect\"]"));
    private ElementsCollection receiversList = $$(By.xpath("//div[@class=\"multiselect__content-wrapper\"]/ul/li"));

    // //*[@id="msgParams"] - xpath для параметров сообщения
    public ElementsCollection sendTypes = $$(By.xpath("//div[@class=\"flex pt-2 pl-1\"]/*//button"));

    private SelenideElement withExecutive = $(By.xpath("(//div[@class=\"v-input--selection-controls__input\"])[1]"));
    private SelenideElement reportToExecutive = $(By.xpath("(//div[@class=\"v-input--selection-controls__input\"])[2]"));


    private SelenideElement hasAuditor = $(By.xpath("(//input[@id=\"withExecutive\"])[2]"));
    private SelenideElement auditorArea = $(By.xpath("//div[@class=\"flex xl12 lg12 md12 sm12 xs12 pr-2 pl-1\"]"));

    private SelenideElement initiatorArea = $(By.xpath("//div[@class=\"flex pr-2 pl-1 xl12 lg12 md12 sm12 xs12\"]"));
    // private SelenideElement initiatorInput=$(By.xpath("//input[@id=\"initiator\"]"));


    private SelenideElement reportReceiverArea = $(By.xpath("//div[@id=\"use-control\"]/div"));
    //private SelenideElement reportReceiverInput=$(By.xpath("//input[@id=\"receiver\"]"));

    private SelenideElement choosePerson = $(By.xpath("//button[@id=\"addReceiver\"]"));


    public NewInstructionPage() {

        dialog = new CancelOK_Dialog();
        instructionPanel = new InstructionPanel();
        //choosePersonForm=new ChoosePersonForm();
    }

    public void fillForm(Instruction newInstruction, ApplicationManager app, boolean useChoosePersonForm) {

        //sleep(2000);
        waitLoading();

        //текстовые поля
        if (subject.getValue() != null && newInstruction.getSubject() != null) {
            setValueToTextFeild(subject, newInstruction.getSubject());
        } else clearTextFeild(subject);

        if (text.getValue() != null && newInstruction.getText() != null) {
            setValueToTextArea(text, newInstruction.getText());
        } else clearTextFeild(text);

        if (comment.getValue() != null && newInstruction.getComment() != null) {
            setValueToTextFeild(comment, newInstruction.getComment());
        } else clearTextFeild(comment);



        if(newInstruction.isControl()==true)

    {
        //тип рассылки
        sendTypes.get(newInstruction.getSendType()).click();

        //ответ.исполнитель и отчеты ответственному
        setExecutiveOptions(newInstruction.isWithExecutive(), newInstruction.isReportToExecutive());

        //контролер
        if (setControl(newInstruction.getExecAuditorID())) {
            setEmployerToField(app.userList.getUserById(newInstruction.getExecAuditorID()), auditorArea);
        }

        //получатель отчета
        setEmployerToField(app.userList.getUserById(newInstruction.getReportReceiverID()), reportReceiverArea);

        //задание выдал
        setEmployerToField(app.userList.getUserById(newInstruction.getInitiatorID()), initiatorArea);

    }

    //получатель
    //receiver.sendKeys(app.userList.getUserById(newInstruction.getReceiverID()[0]).getLastName());
        if(!useChoosePersonForm)

    {
        setValueToTextFeild(receiver, app.userList.getUserById(newInstruction.getReceiverID()[0]).getLastName());
        receiversList.findBy(Condition.text(app.userList.getUserById(newInstruction.getReceiverID()[0]).getUserName())).click();
    } else

    {
        //openChoosePersonForm(newInstruction, app);
        choosePersonForm = openChoosePerson();
        choosePersonForm.insertPersons(newInstruction.getReceiverID(), app);
    }

}


    private void setExecutiveOptions(boolean isExecutive, boolean isReportToExecutive) {

        boolean isExecutiveCurrentState, isReportToExecutiveCurrentState;

//        если чекбокс стоит
//                <i aria-hidden="true" class="v-icon material-icons theme--light primary--text">check_box</i>
//                если нет
//                <i aria-hidden="true" class="v-icon material-icons theme--light">check_box_outline_blank</i>
        if (withExecutive.lastChild().text().equals("check_box")) {
            isExecutiveCurrentState = true;
        } else {
            isExecutiveCurrentState = false;
        }

        if (reportToExecutive.lastChild().text().equals("check_box")) {
            isReportToExecutiveCurrentState = true;
        } else {
            isReportToExecutiveCurrentState = false;
        }

        if (isExecutive != isExecutiveCurrentState) {
            withExecutive.click();
            if (isReportToExecutive != isReportToExecutiveCurrentState) {
                reportToExecutive.click();
            }
        }

    }

    // ввести значение в текстовое поле
    private void setValueToTextFeild(SelenideElement fieldName, String textToBeTyped) {
        fieldName.sendKeys(Keys.CONTROL + "a");
        fieldName.sendKeys(textToBeTyped);
    }
    // ввести значение в textarea
    private void setValueToTextArea(SelenideElement fieldName, String textToBeTyped) {

        fieldName.sendKeys(Keys.CONTROL + "a");
        fieldName.sendKeys(Keys.DELETE);

        char[] result = textToBeTyped.toCharArray();

        for (int i = 0; i < result.length; i++){
            if(result[i]=='\n') fieldName.sendKeys(Keys.ENTER);
            else fieldName.sendKeys(String.valueOf( result[i]));
        }

    }

    //очистить текстовое поле
    private void clearTextFeild(SelenideElement fieldName) {
        fieldName.sendKeys(Keys.CONTROL + "a");
        fieldName.sendKeys(Keys.DELETE);
    }


    private boolean setControl(int AuditorId) {

        boolean hasAuditorValue;
        if (AuditorId != 0) {
            hasAuditorValue = true;
        } else {
            hasAuditorValue = false;
        }

        Boolean state = Boolean.valueOf(hasAuditor.getAttribute("checked"));
        if (state != hasAuditorValue) {
            hasAuditor.doubleClick();
        }
        if (hasAuditorValue) {
            return true;
        } else {
            return false;
        }

    }

    private void setEmployerToField(UserInfo user, SelenideElement field) {

       /* if (field.lastChild().text().length() > 0) {
            field.lastChild().lastChild().lastChild().click();
        }

        field.lastChild().sendKeys(user.getLastName());*/


        if (field.$$x(".//span").filter(Condition.text("person")).size() > 0) {
            field.$x(".//span/*/div[@class=\"v-chip__close\"]").click();
        }

        //sleep(3000);
        waitLoading();

        field.$x(".//input").parent().parent().sendKeys(user.getLastName());
        receiversList.findBy(Condition.text(user.getUserName())).click();
    }

    public ChoosePersonForm openChoosePerson() {
        choosePerson.click();
        return page(ChoosePersonForm.class);
    }


    public WorkArea exitWithoutSaving()
    {
        instructionPanel.cancelButton.click();
        if (dialog.buttonOK.exists()) {
            dialog.buttonOK.click();
        }
        return new WorkArea();
    }



}
