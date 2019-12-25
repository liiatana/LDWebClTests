package ru.lanit.ld.wc.pages;

import io.qameta.allure.Step;
import org.testng.annotations.Test;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.InstructionType;

import static com.codeborne.selenide.Selenide.sleep;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class WorkArea {
    //public SideBar SideBar;
    public Header header;
    public ActionPanel actionPanel;
    public CancelOK_Dialog cancelOK_dialog;
    //public SmallReportForm smallReport;
    //public InstructionCardView cardView;
    public ObjectTypes_Dialog objectTypes_Dialog;
    //public ToolTips toolTips;
    public InstructionListPage instructionListPage;
    public NewInstructionPage newInstructionPage;


    public WorkArea() {
        //SideBar= new SideBar();
        header = new Header();
        actionPanel = new ActionPanel();
        cancelOK_dialog = new CancelOK_Dialog();
        //SmallReport=new SmallReportForm();
        //cardView=new InstructionCardView();
        //toolTips = new ToolTips();
        objectTypes_Dialog = new ObjectTypes_Dialog();
        instructionListPage = new InstructionListPage();
        newInstructionPage = new NewInstructionPage();

    }

    @Step("Открыть форму Новое соообщение")
    public NewInstructionPage openNew(InstructionType instructionType) {

        if ((int) (Math.random() * 2) != 0) {
            header.CreateButtonClick(instructionType);
        } else {
            actionPanel.PlusButtonClick(instructionType);
        }
        ;

        return newInstructionPage;
    }

    @Step("Открыть форму Новое соообщение, заполнить поля и выполнить действие")
    public void createMessage(Instruction instruction, String action, ApplicationManager app) {

        NewInstructionPage ip = newInstructionPage;


        instruction=app.instructionHelper.OutcommingMessage(instruction);

        if ((int) (Math.random() * 2) != 0) {
            ip = header.CreateButtonClick(instruction.getInstructionType());
        } else {
            ip = actionPanel.PlusButtonClick(instruction.getInstructionType());
        }


        ip.fillForm(instruction, app, true);

        switch (action) {
            case "cancel":

                ip.instructionPanel.cancelButton.click();
                break;

            case "send":
                ip.instructionPanel.sendButton.click();
                break;

            case "saveProject":
                ip.instructionPanel.saveProjectButton.click();
                break;
        }
        sleep(4000);

    }
}
