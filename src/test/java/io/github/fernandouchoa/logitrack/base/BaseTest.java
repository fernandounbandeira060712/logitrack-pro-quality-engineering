package io.github.fernandouchoa.logitrack.base;

import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.core.PlaywrightManager;
import io.github.fernandouchoa.logitrack.pages.LoginPage;
import io.github.fernandouchoa.logitrack.utils.AllureEnvironmentWriter;
import io.github.fernandouchoa.logitrack.utils.AllureEvidence;
import io.github.fernandouchoa.logitrack.utils.ScreenshotUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.nio.file.Path;
import java.time.Instant;

public abstract class BaseTest {

    protected Page page;

    private PlaywrightManager manager;

    @BeforeEach
    void setUp() {
        AllureEnvironmentWriter.write();

        manager = new PlaywrightManager();
        manager.start();

        page = manager.getPage();
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        String className = testInfo.getTestClass()
                .map(Class::getSimpleName)
                .orElse("UnknownTest");

        String executionName =
                className
                        + "_"
                        + testInfo.getDisplayName()
                        + "_"
                        + Instant.now().toEpochMilli()
                        + "_thread-"
                        + Thread.currentThread().getId();

        Path trace = null;
        Path logs = null;
        Path video = null;

        try {
            captureFinalScreenshot(executionName);
            attachPageInformation();

            if (manager != null) {
                trace = manager.saveTrace(
                        executionName
                );

                logs = manager.saveLogs(
                        executionName
                );

                manager.closeContext();

                video = manager.saveVideo(
                        executionName
                );
            }
        } catch (Exception exception) {
            AllureEvidence.attachText(
                    "Erro durante a coleta de evidencias",
                    exception.toString()
            );
        } finally {
            AllureEvidence.attachFile(
                    "Playwright Trace",
                    "application/zip",
                    trace,
                    ".zip"
            );

            AllureEvidence.attachFile(
                    "Logs da execucao",
                    "text/plain",
                    logs,
                    ".log"
            );

            AllureEvidence.attachFile(
                    "Video da execucao",
                    "video/webm",
                    video,
                    ".webm"
            );

            if (manager != null) {
                manager.close();
            }
        }
    }

    protected LoginPage loginPage() {
        return new LoginPage(page);
    }

    private void captureFinalScreenshot(
            String executionName
    ) {
        if (page == null || page.isClosed()) {
            return;
        }

        byte[] screenshot = ScreenshotUtils.capture(
                page,
                executionName
        );

        AllureEvidence.attachScreenshot(
                "Evidencia final",
                screenshot
        );
    }

    private void attachPageInformation() {
        if (page == null || page.isClosed()) {
            return;
        }

        String title;

        try {
            title = page.title();
        } catch (Exception exception) {
            title = "Titulo indisponivel";
        }

        AllureEvidence.attachText(
                "Pagina final",
                "URL: "
                        + page.url()
                        + System.lineSeparator()
                        + "Titulo: "
                        + title
        );
    }
}