package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.lanit.ld.wc.model.InstructionType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class ObjectTypes_Dialog {

    private SelenideElement area = //$(By.xpath("//div[@class=\"v-dialog modal v-dialog--active v-dialog--persistent\"]"));
    $(By.xpath("//div[@class=\"card v-card v-sheet theme--light\"]"));

    public ElementsCollection availableTypes = area.$$(By.xpath("/*//ul[@class=\"pl-0\"]/li"));


    public ObjectTypes_Dialog() {
    }

    public Set<String> getTypesList() {

        sleep(1000);
        Set<String> collect1 = new HashSet<>();

        collect1 = Arrays.stream(availableTypes.texts().toString().substring(1, availableTypes.texts().toString().length() - 1).toUpperCase().split(", "))
                .collect(Collectors.toSet());

        return collect1;
    }

    public void close() {
        area.$x("./*//i").click();
    }


    public NewInstructionPage chooseType(InstructionType type) {

        availableTypes.filter(Condition.exactText(type.getName())).first().click();
        sleep(2000);
        return new NewInstructionPage();
    }
}
