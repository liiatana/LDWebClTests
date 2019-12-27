package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

public class _BasePage {
    private ElementsCollection loadingCircles = $$(By.xpath("//*[@class=\"loading-mask loader\" and @style=\"z-index: 1;\"]"));

    public boolean loadingEnded(){
        int watingCounts=0;

        while(loadingCircles.size()!=0 ) {
            Selenide.sleep(1000); // на секунду "засыпаем"
            //System.out.println("size1=" + loadingCircles.size());
            watingCounts++;
            //System.out.println("watingCounts=" + watingCounts);
            if (watingCounts >= 100) { //ждем максимум 100 секунд
                return false;
            }
        }
        return true;
    }

}
