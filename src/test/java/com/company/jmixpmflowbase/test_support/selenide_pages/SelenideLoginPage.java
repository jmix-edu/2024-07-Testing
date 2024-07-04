package com.company.jmixpmflowbase.test_support.selenide_pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class SelenideLoginPage {

    public void open(){
        Selenide.open("http://localhost:8080/");
    }

    public void enterUsername(String username){
        SelenideElement usernameField = $(By.name("username"));
        usernameField.click();
        usernameField.sendKeys(Keys.CONTROL + "A");
        usernameField.sendKeys(Keys.BACK_SPACE);
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password){
        SelenideElement passwordField = $(By.name("password"));
        passwordField.click();
        passwordField.sendKeys(Keys.CONTROL + "A");
        passwordField.sendKeys(Keys.BACK_SPACE);
        passwordField.sendKeys(password);
    }

    public void clickLoginBtn(){
        SelenideElement loginBtn = $(By.tagName("vaadin-button"));
        loginBtn.click();
    }
}
