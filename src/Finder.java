import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import sun.rmi.runtime.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Finder extends Thread {
    WebDriver driver;
    String base_url;
    int p = 99;
    File file = new File("result.txt");

    public Finder(String word) {
        System.setProperty("webdriver.chrome.driver", "/home/ubuntu/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.setCapability("ignoreProtectedModeSettings", true);
        driver = new ChromeDriver(options);

        base_url = "https://search.naver.com/search.naver?where=post&query=" + word + "&st=sim&sm=tab_opt&date_from=20030501&date_to=20121231&date_option=8&srchby=all&dup_remove=1&post_blogurl=tistory.com&post_blogurl_without=&nso=so%3Ar%2Ca%3Aall%2Cp%3Afrom20070101to20121231";
    }

    public void Write(String url) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(url);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void craw(int page) {
        driver.get(base_url);
        /*
        driver.findElement(By.id("_search_option_btn")).click();

        WebElement during = driver.findElement(By.xpath("//ul[@class='option_menu']//li[@class='menu']//a[text()='기간']"));
        during.click();
        driver.findElement(By.id("blog_input_period_begin")).sendKeys("2007.01.01");
        driver.findElement(By.id("blog_input_period_end")).sendKeys("2012.12.31");
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@class='_btn_submit']")).click();

        driver.findElement(By.xpath("//ul[@class='option_menu']//li[@class='menu']//a[text()='출처']")).click();
        driver.findElement(By.id("src_input")).sendKeys("tistory.com");
        driver.findElements(By.xpath("//span[@class='btn_inp']//button")).get(1).click();

         */

        System.out.println(Thread.currentThread().getName() + " 크롤링 시작");
        // 페이지 지정
        for (int i = 0; i < page; i++) {
            List<WebElement> elements = driver.findElements(By.xpath("//li[@class='sh_blog_top']//dl//dt//a"));
            for (WebElement e : elements) {
                String href = e.getAttribute("href");
                // 티스토리 주소 검색
                String name = driver.getWindowHandle();
                System.out.println("url: " + href);
                e.click();
                boolean link = false;
                try {
                    sleep(1500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                try {
                    List<WebElement> errors = driver.findElements(By.className("desc_error"));
                    if (errors != null && errors.size() != 0) {
                        System.out.println("found: " + href);
                        link = true;
                        Write(href);
//                        System.exit(0);
                    }
                } catch (Exception ex) {

                }

                Set<String> handles = driver.getWindowHandles();
                if (!link) {
                    for (String s : handles) {
                        if (!s.equals(name)) {
                            driver.switchTo().window(s);
                            driver.close();
                        }
                    }
                }
                driver.switchTo().window(name);

            }
            try {
                driver.findElement(By.xpath("//a[@class='next']")).click();
            } catch (Exception e) {
                driver.quit();
                break;
            }

        }
        //driver.quit();
    }

    public void quit() {
        try {
            driver.quit();
        } catch (Exception e) {

        }
    }

    @Override
    public void run() {
        craw(p);
    }
}
