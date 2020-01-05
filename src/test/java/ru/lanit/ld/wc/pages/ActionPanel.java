package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.Instruction;
import ru.lanit.ld.wc.model.InstructionType;

import static com.codeborne.selenide.Selenide.*;

public class ActionPanel extends _BasePage {

    private SelenideElement area=$(By.xpath("//div[@id=\"wsHeaderWrapper\"]"));
    private SelenideElement PlusButton = area.$(By.xpath("/*//div[@id=\"msgAddMenu\"]"));

    private ElementsCollection plusButtonMenu =
            $$(By.xpath("//div[@class=\"v-menu__content theme--light menuable__content__active\"]/div/div"));



    private SelenideElement PreviewOn = $(By.xpath("(//div[@class=\"layout row justify-end mb-0 ml-3\"]//button)[3]"));
    private SelenideElement PreviewOff = $(By.xpath("(//div[@class=\"layout row justify-end mb-0 ml-3\"]//button)[4]"));

    //private SelenideElement PlusButton = $(By.xpath("(//div[@class=\"v-menu__activator\"]/button)[1]"));

    //private ElementsCollection plusButtonMenu = $$(By.xpath("//div[@class=\"v-list create-global-menu theme--light\"]/div"));


    private ElementsCollection InstructionTypes = //$$(By.xpath("//div[@class=\"v-menu__content theme--light menuable__content__active\"]/div[@role=\"list\"]"));
            //$$(By.xpath("//div[@class=\"v-menu__content theme--light menuable__content__active\"]/div[@role=\"list\"]/*/a"));
            //$$(By.xpath("//div[@class=\"v-card v-sheet theme--light\"]/*//ul/li"));
    $$(By.xpath("//ul[@class=\"pl-0\"]/li"));


    private SelenideElement refreshButton = $(By.xpath("(//span[@class=\"v-tooltip v-tooltip--bottom\"])[2]"));

    private SelenideElement onlyNew = $(By.xpath("//input[@aria-label=\"Только непрочитанные\"]"));

    private SelenideElement sortButton = $(By.xpath("//button[@class=\"my-0 grey--border lighten-2 small-button v-btn v-btn--outline v-btn--depressed theme--light\"]"));
    private ElementsCollection sortOptions = $$(By.xpath("//div[@class=\"v-menu__content theme--light menuable__content__active\"]/*//div[@role=\"listitem\"]"));

    private SelenideElement viewObjectTreeButton=$(By.xpath("//nav/*/button"));

    public void PreviewIs(String state) {

        switch (state) {
            case "On":

                PreviewOn.click();
                sleep(1000);
                break;

            case "Off":

                PreviewOff.click();
                sleep(3000);
                break;
        }
    }


    public NewInstructionPage PlusButtonClick(InstructionType type) {
        PlusButton.click();
        waitLoading();
        plusButtonMenu.filterBy(Condition.exactText(type.getName())).first().click();
        waitLoading();
        return page(NewInstructionPage.class);
    }

    public void viewOnlyNew(boolean state) {
        Boolean currentState = Boolean.valueOf(onlyNew.getAttribute("aria-checked"));
        if (state != currentState) {
            onlyNew.parent().click();
        }
    }

    public void sortBy(String field, boolean isDesc) {
        String currentSortFeild = sortButton.$x("div").$x("span").$$x("span").get(0).getText();
        if (sortButton.$x("div").$x("span").$$x("span").get(1).getText().length() != 0) {
            currentSortFeild = currentSortFeild.concat(" ").concat(sortButton.$x("div").$x("span").$$x("span").get(1).getText());
        }

        if (!field.equals(currentSortFeild)) {
            sortButton.click();
            sortOptions.filter(Condition.text(field)).get(0).click();
            sortButton.click();
        }
        ;

        Boolean currentSortDirection = sortButton.$x("div").$x("span").$x("i").getText().equals("arrow_downward"); //true=asc, иначе=false

        if (currentSortDirection != isDesc) {
            sortButton.click();
            sortOptions.filter(Condition.text(field)).get(0).click();
            sortButton.click();
        }
        ;

        sleep(2000);
    }

    public void setViewState(String PreviewState, boolean viewOnlyNew, String sortingField, boolean isDescSorting) {
        PreviewIs(PreviewState);
        viewOnlyNew(viewOnlyNew);
        sortBy(sortingField, isDescSorting);
    }

    public void openObjectTree (){
        viewObjectTreeButton.click();
        sleep(1000);
    }

    public void refreshList(){
        refreshButton.click();
        sleep(5000);
    }

}
