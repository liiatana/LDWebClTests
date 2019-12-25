package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.lanit.ld.wc.appmanager.ApplicationManager;
import ru.lanit.ld.wc.model.UserInfo;

import static com.codeborne.selenide.Selenide.*;

public class ChoosePersonForm {
    private SelenideElement searchField = $(By.xpath("//input[@id=\"search\"]"));
    private ElementsCollection persons = $$(By.xpath("//div[@id=\"selector\"]/div/div"));

    private ElementsCollection choosenPersons = $$(By.xpath("//div[@class=\"layout ml-2 column\"]/div/*/span"));


    //private SelenideElement buttonOk = $(By.xpath("//div[@class=\"small-button rounded-button elevation-0 v-btn theme--light primary\"]/*//button[1]"));
    //button[@class="small-button rounded-button elevation-0 v-btn theme--light primary"]
    private SelenideElement buttonOk = $(By.xpath("//button[@class=\"small-button rounded-button elevation-0 v-btn theme--light primary\"]"));

    //private SelenideElement buttonCancel = $(By.xpath("//div[@class=\"v-dialog modal v-dialog--active v-dialog--persistent\"]/*//button[2]"));
    private SelenideElement buttonCancel = $(By.xpath("//button[@class=\"small-button rounded-button v-btn v-btn--outline v-btn--depressed theme--light primary--text\"]"));

    public void insertPersons(int[] userIds, ApplicationManager app) {

        sleep(10000);

        if (choosenPersons.size() > 0) {
            clearAllPersons();
        }

        UserInfo currentUser;

        for (int i = 0; i <= userIds.length - 1; i++) {
            currentUser = app.userList.getUserById(userIds[i]);
            searchField.sendKeys(Keys.CONTROL + "a");
            searchField.sendKeys(currentUser.getLastName());

            persons.findBy(Condition.text(currentUser.getUserName())).click();

        }

        buttonOk.click();

    }

    public void clearAllPersons() {

        while(choosenPersons.size()!=0){
            choosenPersons.last().lastChild().lastChild().lastChild().click();
        }
        buttonOk.click();
    }


}
