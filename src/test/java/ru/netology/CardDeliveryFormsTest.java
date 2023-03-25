package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryFormsTest {

    @Test
    void shouldCheckFormsAuto() {

        Configuration.holdBrowserOpen = true;

        String dateWeek = LocalDate.now().plusWeeks(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Ко");
        $x("//*[contains(text(), 'Кострома')]").click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        $("[data-test-id='date'] .icon-button__content").click();
        $$x("//*[contains(@class, 'calendar__arrow calendar__arrow_direction_right')]").filter(visible).get(1).click();
        $("[data-test-id=date] input").setValue(dateWeek);
        $("[data-test-id=name] input").setValue("Олег Петров");
        $("[data-test-id=phone] input").setValue("+79998887755");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(13));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + dateWeek));
    }
}
