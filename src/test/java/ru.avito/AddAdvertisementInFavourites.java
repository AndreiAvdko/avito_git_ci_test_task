package ru.avito;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class AddAdvertisementInFavourites {
    private static final String baseURL = "https://www.avito.ru";
    private static final String cardURL = "/nikel/knigi_i_zhurnaly/domain-driven_design_distilled_vaughn_vernon_2639542363";
    @Attachment(value="Screenchot", type="image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @BeforeAll
    static void beforeAll() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "normal";
        Configuration.pageLoadTimeout = 50000;
    }
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Добавление объявления в избранное")
    @Feature("Секция избранное для незарегистрированного пользователя")
    @DisplayName("Проверка добавления в избранное объявления с карточки объявления")
    public void checkAddingAdvertisementsToFavorites() {
        SelenideLogger.addListener("allure", new AllureSelenide().screenshots(true));
        step("Открываем страницу объявления", ()-> {
            open(baseURL + cardURL);
            takeScreenshot();
        });

        step("Кликаем на кнопку < Добавить > в избранное, проверяем всплывающее уведомление", ()-> {
            $(byText("Добавить в избранное")).click();
            $("a[href=\"/favorites/knigi_i_zhurnaly\"]").should(Condition.visible);
            takeScreenshot();
        });

        step("Кликаем на кнопку < Избранное > в шапке сайта", ()-> {
            $(".index-counter-UxPCj").click();
            takeScreenshot();
        });

        step("Проверяем наличие элемента с названием и то, что он выбран (наличие закрашенного сердечка справа)", ()-> {
            $(withText("Domain-Driven Design Distilled Vaughn Vernon")).should(exist);
            $("div[data-marker=item-2639542363] div.withFavorites-heart_fill-InZcS").should(exist);
            $("div[data-marker=item-2639542363] a[href=\"" + cardURL + "\"]").should(exist);
            takeScreenshot();
        });
    }
}
