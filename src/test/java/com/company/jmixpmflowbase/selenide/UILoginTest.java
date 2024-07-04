package com.company.jmixpmflowbase.selenide;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.company.jmixpmflowbase.test_support.selenide_pages.SelenideLoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.editable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class UILoginTest {

    @Test
    public void test_username_field(){
        sleep(3000);

        Selenide.open("http://localhost:8080/login");
        SelenideElement usernameField = $(By.name("username"));
        usernameField.shouldBe(visible);
        usernameField.shouldBe(editable);
        Assertions.assertEquals(("admin"), usernameField.getValue());

    }

    @Test
    public void test_password_field(){
        Selenide.open("http://localhost:8080/login");
        SelenideElement passwordField = $(By.name("password"));
        passwordField.shouldBe(visible);
        passwordField.shouldBe(editable);

        Assertions.assertEquals("password", passwordField.getAttribute("type"));
    }

    @Test
    public void testLoginPage() {
        sleep(3000);
        SelenideLoginPage loginPage = new SelenideLoginPage();

        loginPage.open();
        loginPage.enterUsername("dev1");
        loginPage.enterPassword("admin");
        loginPage.clickLoginBtn();
    }

}
