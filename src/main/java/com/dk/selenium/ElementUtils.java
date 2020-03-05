package com.dk.selenium;

import com.dk.base.DriverManager;
import com.dk.utils.ProjectProperties;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * @author Vivek Lande
 */
public class ElementUtils {
    private WebDriver driver;
    private JavascriptExecutor js;

    public ElementUtils(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }

    public void clickElementAfterFocus(WebElement element) {
        int retryCount = 5;
        while (retryCount > 0) {
            try {
                String jsExec = null;

                js.executeScript("arguments[0].scrollIntoView();", element);
                jsExec = "arguments[0].focus();";

                js.executeScript(jsExec, element);
                waitForWebElementIsEnabled(element);
                element.click();
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale elements exception while waiting clicking on elements: " + element.toString());
                retryCount--;
                if (retryCount != 0) {
                    try {
                        Thread.sleep(1000);
                        continue;
                    } catch (Exception e1) {
                    }
                    System.out.println("handling stale elements reference: retry count-" + retryCount);
                }

                Assert.fail("StaleElementException while clicking selenium elements" + element.toString(), e);
            } catch (Exception ex) {
                ex.printStackTrace();
                Assert.fail("exception while clicking selenium elements!" + element.toString(), ex);
            }
        }
    }

    public void waitForWebElementIsEnabled(WebElement webElement) {
        int retryCount = 5;
        while (retryCount > 0) {
            try {
                new WebDriverWait(DriverManager.getWebDriver(), Long.parseLong(ProjectProperties.getRequestTimeOut())).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return webElement.isEnabled();
                    }
                });
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale elements exception while verifying on elements: " + webElement.toString());
                retryCount--;
                if (retryCount != 0) {
                    try {
                        Thread.sleep(1000);
                        continue;
                    } catch (Exception e1) {
                    }
                    System.out.println("handling stale elements reference: retry count-" + retryCount);
                }

                Assert.fail("StaleElementException while verifying selenium elements" + webElement.toString(), e);
            } catch (Exception ex) {
                ex.printStackTrace();
                Assert.fail("exception while verifying selenium elements!" + webElement.toString(), ex);
            }
        }
    }

    public void waitForWebElementIsDisplay(WebElement webElement) {
        int retryCount = 5;
        while (retryCount > 0) {
            try {
                new WebDriverWait(DriverManager.getWebDriver(), Long.parseLong(ProjectProperties.getRequestTimeOut())).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return webElement.isDisplayed();
                    }
                });
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale elements exception while verifying on elements: " + webElement.toString());
                retryCount--;
                if (retryCount != 0) {
                    try {
                        Thread.sleep(1000);
                        continue;
                    } catch (Exception e1) {
                    }
                    System.out.println("handling stale elements reference: retry count-" + retryCount);
                }

                Assert.fail("StaleElementException while verifying selenium elements" + webElement.toString(), e);
            } catch (Exception ex) {
                ex.printStackTrace();
                Assert.fail("exception while verifying selenium elements!" + webElement.toString(), ex);
            }
        }
    }

    public void sendKeys(WebElement element, String value, boolean isToBeforeClear) {
        waitForWebElementIsEnabled(element);
        clickElementAfterFocus(element);
        if (isToBeforeClear)
            element.clear();
        //element.sendKeys(value);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click();
        actions.sendKeys(value);
        actions.build().perform();
    }
}
