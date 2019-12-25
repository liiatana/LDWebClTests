package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class InstructionPanel {

    private SelenideElement area=$(By.xpath("(//div[@class=\"instruction-panel\"])"));

    private SelenideElement navigateBack=area.$(By.xpath("/*//i[text()=\"navigate_before\""));

    public SelenideElement cancelButton=area.$(By.xpath("/*//button/*//i[text()=\"clear\"]"));

    public SelenideElement saveProjectButton=area.$(By.xpath("/*//button/*//i[text()=\"save\"]"));

    public SelenideElement sendButton=area.$(By.xpath("/*//button/*//i[text()=\"send\"]"));




}
