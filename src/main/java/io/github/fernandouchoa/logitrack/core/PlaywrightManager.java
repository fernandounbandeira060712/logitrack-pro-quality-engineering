package io.github.fernandouchoa.logitrack.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import io.github.fernandouchoa.logitrack.config.ConfigManager;

import java.nio.file.Files;
import java.nio.file.Paths;

public final class PlaywrightManager implements AutoCloseable {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    public void start() {
        createDirectories();

        playwright = Playwright.create();

        BrowserType browserType = switch (
                ConfigManager.getBrowser().toLowerCase()
        ) {
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            case "chromium" -> playwright.chromium();
            default -> throw new IllegalArgumentException(
                    "Navegador nÃ£o suportado: "
                            + ConfigManager.getBrowser()
            );
        };

        browser = browserType.launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(ConfigManager.isHeadless())
        );

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1440, 900)
                        .setLocale("pt-BR")
        );

        context.setDefaultTimeout(ConfigManager.getTimeout());

        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );

        page = context.newPage();
    }

    public Page getPage() {
        if (page == null) {
            throw new IllegalStateException(
                    "O Playwright ainda nÃ£o foi inicializado."
            );
        }

        return page;
    }

    public void saveTrace(String testName) {
        if (context == null) {
            return;
        }

        String safeName = testName
                .replaceAll("[^a-zA-Z0-9-_]", "_")
                .replaceAll("_+", "_");

        context.tracing().stop(
                new Tracing.StopOptions()
                        .setPath(Paths.get(
                                "target",
                                "traces",
                                safeName + ".zip"
                        ))
        );
    }

    @Override
    public void close() {
        if (context != null) {
            context.close();
        }

        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }

    private void createDirectories() {
        try {
            Files.createDirectories(
                    Paths.get("target", "screenshots")
            );
            Files.createDirectories(
                    Paths.get("target", "traces")
            );
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "NÃ£o foi possÃ­vel criar as pastas de evidÃªncias.",
                    exception
            );
        }
    }
}