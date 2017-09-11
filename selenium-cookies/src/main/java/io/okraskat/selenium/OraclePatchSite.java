package io.okraskat.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class OraclePatchSite {
    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{1,2}.*\\d{4})");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.US);
    private static final int TIMEOUT_IN_SECONDS = 20;
    private static final String DOCUMENT_READY_STATE = "return document.readyState";
    private static final String COMPLETE = "complete";
    private static final String I_FRAME = "iframe";
    private static final String TD = "td";
    private static final String COMMA = ",";
    private static final String SPACE = " ";
    private static final String ACCEPT_COOKIES_BUTTON_X_PATH = "//a[@class='call' and @role='button']";
    private static final String PATCH_UPDATE_TABLE_ROWS_X_PATH = "//*[@id=\"Wrapper_FixedWidth_Centercontent\"]/div/div[2]/div/table[1]/tbody/tr";

    private final String url;

    OraclePatchSite(String url) {
        this.url = url;
    }

    Map<LocalDate, String> searchPatchesForPeriod(WebDriver webDriver, LocalDate from, LocalDate to) {
        webDriver.get(url);
        waitForPageLoad(webDriver);
        waitForAcceptCookiesFrame(webDriver);
        acceptCookiesPolicy(webDriver);
        return searchForPatchUpdates(webDriver, from, to);
    }

    private void waitForPageLoad(WebDriver webDriver) {
        Wait<WebDriver> wait = new WebDriverWait(webDriver, TIMEOUT_IN_SECONDS);
        wait.until(webDriver1 -> ((JavascriptExecutor) webDriver).executeScript(DOCUMENT_READY_STATE).equals(COMPLETE));
    }

    private void waitForAcceptCookiesFrame(WebDriver webDriver) {
        new WebDriverWait(webDriver, TIMEOUT_IN_SECONDS)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName(I_FRAME)));

        new WebDriverWait(webDriver, TIMEOUT_IN_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(ACCEPT_COOKIES_BUTTON_X_PATH)));
    }

    private void acceptCookiesPolicy(WebDriver webDriver) {
        webDriver.findElement(By.xpath(ACCEPT_COOKIES_BUTTON_X_PATH)).click();
        webDriver.switchTo().defaultContent();
    }

    private Map<LocalDate, String> searchForPatchUpdates(WebDriver webDriver, LocalDate from, LocalDate to) {
        Map<LocalDate, String> foundUpdates = new HashMap<>();
        List<WebElement> patchUpdates = webDriver.findElements(By.xpath(PATCH_UPDATE_TABLE_ROWS_X_PATH));
        patchUpdates.stream()
                .skip(1)
                .forEach(patchUpdate -> {
                    List<WebElement> patchUpdateColumns = patchUpdate.findElements(By.tagName(TD));
                    WebElement columnWithDate = patchUpdateColumns.get(1);
                    String dateText = columnWithDate.getText().split(COMMA)[1].substring(1);
                    Optional<LocalDate> patchDate = parseDate(dateText);

                    if (patchDate.isPresent() && isDateBetween(from, to, patchDate.get())) {
                        foundUpdates.put(patchDate.get(), String.format("%s %s", patchUpdateColumns.get(0).getText(), columnWithDate.getText()));
                    }
                });
        return foundUpdates;
    }

    private Optional<LocalDate> parseDate(String dateText) {
        return Optional.ofNullable(dateText)
                .map(this::mapToDate);
    }

    private LocalDate mapToDate(String textWithDate) {
        Matcher matcher = DATE_PATTERN.matcher(textWithDate);
        if (matcher.matches()) {
            String[] dateParts = matcher.group(1).split(SPACE);
            String date = String.format("%02d %s %s", Integer.parseInt(dateParts[0]), dateParts[1].substring(0, 3), dateParts[2]);
            return LocalDate.parse(date, DATE_TIME_FORMATTER);
        }
        return null;
    }

    private boolean isDateBetween(LocalDate from, LocalDate to, LocalDate dateToCheck) {
        return from.isBefore(dateToCheck) && to.isAfter(dateToCheck);
    }
}
