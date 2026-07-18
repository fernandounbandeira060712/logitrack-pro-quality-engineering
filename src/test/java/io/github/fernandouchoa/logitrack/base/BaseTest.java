package io.github.fernandouchoa.logitrack.base;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import io.github.fernandouchoa.logitrack.core.PlaywrightManager;
import io.github.fernandouchoa.logitrack.pages.LoginPage;
import io.github.fernandouchoa.logitrack.utils.ScreenshotUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.io.ByteArrayInputStream;

public abstract class BaseTest {

    protected Page page;
    private PlaywrightManager manager;

    @BeforeEach
    void setUp() {
        manager = new PlaywrightManager();
        manager.start();
        page = manager.getPage();
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        try {
            if (page != null && !page.isClosed()) {
                byte[] screenshot = ScreenshotUtils.capture(
                        page,
                        testInfo.getDisplayName()
                );

                Allure.addAttachment(
                        "EvidÃªncia final",
                        "image/png",
                        new ByteArrayInputStream(screenshot),
                        ".png"
                );
            }

            if (manager != null) {
                manager.saveTrace(testInfo.getDisplayName());
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }

    protected LoginPage loginPage() {
        return new LoginPage(page);
    }
}