package ru.lanit.ld.wc.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

public class _BasePage {
    private ElementsCollection loadingCircles = $$(By.xpath("//*[@class=\"loading-mask loader\" and @style=\"z-index: 1;\"]"));

    public boolean waitLoading(){
        int watingCounts=0;

        while(loadingCircles.size()!=0 ) {
            Selenide.sleep(1000); // на секунду "засыпаем"
            //System.out.println("size1=" + loadingCircles.size());
            watingCounts++;
            //System.out.println("watingCounts=" + watingCounts);
            if (watingCounts >= 120) { //ждем максимум 2 минуты
                return false;
            }
        }
        return true;
    }



}
