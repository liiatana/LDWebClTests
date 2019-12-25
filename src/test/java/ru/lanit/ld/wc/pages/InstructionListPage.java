package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.FolderList;
import ru.lanit.ld.wc.model.Instruction;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.sleep;

public class InstructionListPage {

    public ElementsCollection InstructionListWithPreview = $$(By.xpath("//div[@class=\"data-iteraror marginless-list\"]/*"));

    //public ElementsCollection InstructionListWithoutPreview=$$(By.xpath("//div[@class=\"layout list-item\"]"));

    public String threePoints= "div.v-menu.menu-vert.v-menu--inline > div > button > div > i";

    /*public WorkArea goToFolder(int Folder_ID){
        Selenide.open(String.format("instructions/%s",Folder_ID));

        sleep(10000);
        return this;
    }*/



   /* public int clickOnReportButton(Instruction focusedInstruction, boolean reportType, ApplicationManager app) {
        //обновить список папки
        this.actionPanel.refreshList();

        int num = getFolderNumInList(focusedInstruction, app);

        //нажать кнопку Отчитаться/Отказать для сообщения
        this.cardView.quickReport(reportType, num);
        //в диалоговом окне нажать кнопку ОК
        this.cancelOK_dialog.buttonOK.click();

       // this.toolTips.getToolTips();

        sleep(4000);

        return num;
    }*/


}
