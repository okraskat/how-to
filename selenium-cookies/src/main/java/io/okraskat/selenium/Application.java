package io.okraskat.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

class Application {
    private static final String CHROME_DRIVER_NAME = "chromedriver.exe";
    private static final String WEB_DRIVER_PROPERTY = "webdriver.chrome.driver";
    private static final String ORACLE_PATCH_UPDATE_SITE = "https://www.oracle.com/technetwork/topics/security/alerts-086861.html";

    public static void main(String[] args) {
        WebDriver webDriver = prepareWebDriver();
        LocalDate from = LocalDate.of(2016, 1, 1);
        LocalDate to = LocalDate.now();
        new OraclePatchSite(ORACLE_PATCH_UPDATE_SITE)
                .searchPatchesForPeriod(webDriver, from, to)
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(entry -> System.out.println(entry.getValue()));
        webDriver.close();
    }

    private static WebDriver prepareWebDriver() {
        URL driverUrl = Application.class.getClassLoader().getResource(CHROME_DRIVER_NAME);
        if (Objects.isNull(driverUrl)) {
            throw new IllegalStateException(String.format("Could not load %s from resource directory.", CHROME_DRIVER_NAME));
        }
        System.setProperty(WEB_DRIVER_PROPERTY, driverUrl.getFile());
        return new ChromeDriver();
    }
}
