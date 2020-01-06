package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.InstructionType;

import static com.codeborne.selenide.Selenide.*;

public class Header extends  _BasePage {

    private SelenideElement FIO =
            //$(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Настройки пользователя'])[1]/following::button[4]"));
    $(By.xpath("//button[@class=\"deputy-btn v-btn v-btn--flat theme--light\"]"));

    private ElementsCollection FIOmenu=$$(By.xpath("//div[@class=\"v-menu__content theme--light v-menu__content--fixed menuable__content__active\"]/*"));
    //class="v-menu__content theme--light v-menu__content--fixed menuable__content__active "

    //private SelenideElement createButton = $(By.xpath("(//div[@class=\"v-menu v-menu--inline\"])[1]"));

    //private ElementsCollection menu = $$(By.xpath("//div[@class=\"v-list create-global-menu theme--light\"]/div"));
    //div[@class="v-list create-global-menu theme--light"] - это сама область с выпадающим списокм сообщение+Документ

    //------------------------------------------------------------------

    private ObjectTypes_Dialog objectTypes_dialog;

    private SelenideElement area=$(By.xpath("//div[@id=\"ws-header\"]"));
    private SelenideElement createButton = area.$(By.xpath("/*//i[text()=\"add\"]"));
    private ElementsCollection menu = $$(By.xpath("//div[@class=\"v-list create-global-menu theme--light\"]/div"));




    public Header() {
        objectTypes_dialog = new ObjectTypes_Dialog();
    }

    public String getLastName() {
        //FIO.getText().split("\n")[1].split(" ")[0] - фамилия
        //button[@class="deputy-btn v-btn v-btn--flat theme--light"]/*/span
        return FIO.$x("div/span").getText().split(" ")[0];
        //return FIO.getText().split("\n")[1].split(" ")[0];
    }

    public void exit() {
        FIO.click();
        FIOmenu.filter(Condition.text("Выход")).get(0).click();
    }


    public NewInstructionPage CreateButtonClick(InstructionType type) {
        createButton.click();
        menu.filterBy(Condition.text("Сообщение")).get(0).click();

        objectTypes_dialog.availableTypes.findBy(
                Condition.exactText(type.getName())).click();

        waitLoading();
        return page(NewInstructionPage.class);
    }
}
