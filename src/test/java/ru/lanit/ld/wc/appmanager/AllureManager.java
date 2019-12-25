package ru.lanit.ld.wc.appmanager;

import com.codeborne.selenide.Configuration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AllureManager {

    private String allureProrertyFilePath;
    private File allureEnvFile;

    public AllureManager(String allureProrertyFilePath) throws IOException {
        this.allureProrertyFilePath=allureProrertyFilePath;

        //this.cleanTestDirectoryResults(allureProrertyFilePath);
    }

   /* private void cleanTestDirectoryResults(String allureProrertyFilePath) throws IOException {
        String path = this.allureEnvFile.getParent();
        File dir = new File(path);

        FileUtils.cleanDirectory(  new File(this.allureEnvFile.getParent()));

    }*/

    public void exportEnviromentInfornationToFile(ApplicationManager app) throws IOException {


        this.allureEnvFile = new File(this.allureProrertyFilePath);

        FileUtils.cleanDirectory(  new File(this.allureEnvFile.getParent()));

            if (allureEnvFile.exists()) {
                allureEnvFile.delete();
            }

            allureEnvFile.createNewFile();

            try (FileWriter writer = new FileWriter(allureEnvFile, false);) {

                writer.write("URL=" + app.baseUrl);
                writer.write(System.lineSeparator());

                writer.write("Browser=" + app.browser);
                writer.write(System.lineSeparator());

                writer.write("BrowserSize=" + Configuration.browserSize);



        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addEnviromentInfo(String parameter, String value) {

         try (FileWriter writer = new FileWriter(allureEnvFile, true);) {

            writer.write(System.lineSeparator());
            writer.write( parameter +"=" + value);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }



}
