package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class CancelOK_Dialog {
    //div[@class="v-card v-sheet theme--light"]

    public SelenideElement buttonOK=//$(By.xpath("(//div[@class=\"v-card v-sheet theme--light\"]/*/button)[1]"));
                                    $(By.xpath("(//div[@class=\"card v-card v-sheet theme--light no-grid\"]//button)[1]"));

    public SelenideElement buttonCancel=//$(By.xpath("(//div[@class=\"v-card v-sheet theme--light\"]/*/button)[2]"));
            $(By.xpath("(//div[@class=\"card v-card v-sheet theme--light no-grid\"]//button)[2]"));


    public SelenideElement caption=$(By.xpath("(//div[@class=\"v-card v-sheet theme--light\"]/div)[1]"));

    public SelenideElement msg=$(By.xpath("(//div[@class=\"v-card v-sheet theme--light\"]/div)[2]"));



}
