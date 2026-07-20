package io.github.fernandouchoa.logitrack.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.Video;
import io.github.fernandouchoa.logitrack.config.ConfigManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public final class PlaywrightManager implements AutoCloseable {

    private static final Path SCREENSHOTS_DIRECTORY =
            Paths.get("target", "screenshots");

    private static final Path TRACES_DIRECTORY =
            Paths.get("target", "traces");

    private static final Path VIDEOS_DIRECTORY =
            Paths.get("target", "videos");

    private static final Path RAW_VIDEOS_DIRECTORY =
            VIDEOS_DIRECTORY.resolve("raw");

    private static final Path LOGS_DIRECTORY =
            Paths.get("target", "logs");

    private final List<String> consoleMessages =
            new ArrayList<>();

    private final List<String> pageErrors =
            new ArrayList<>();

    private final List<String> failedRequests =
            new ArrayList<>();

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private Video video;

    private boolean traceStopped;
    private boolean contextClosed;

    public void start() {
        createDirectories();

        playwright = Playwright.create();

        BrowserType browserType = switch (
                ConfigManager.getBrowser()
                        .toLowerCase()
        ) {
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            case "chromium" -> playwright.chromium();
            default -> throw new IllegalArgumentException(
                    "Navegador nao suportado: "
                            + ConfigManager.getBrowser()
            );
        };

        browser = browserType.launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(
                                ConfigManager.isHeadless()
                        )
        );

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1440, 900)
                        .setLocale("pt-BR")
                        .setRecordVideoDir(
                                RAW_VIDEOS_DIRECTORY
                        )
                        .setRecordVideoSize(
                                1280,
                                720
                        )
        );

        context.setDefaultTimeout(
                ConfigManager.getTimeout()
        );

        context.setDefaultNavigationTimeout(
                ConfigManager.getTimeout()
        );

        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );

        page = context.newPage();
        video = page.video();

        page.onConsoleMessage(message ->
                consoleMessages.add(
                        OffsetDateTime.now()
                                + " ["
                                + message.type()
                                + "] "
                                + message.text()
                )
        );

        page.onPageError(error ->
                pageErrors.add(
                        OffsetDateTime.now()
                                + " "
                                + error
                )
        );

        page.onRequestFailed(request ->
                failedRequests.add(
                        OffsetDateTime.now()
                                + " "
                                + request.method()
                                + " "
                                + request.url()
                                + " | "
                                + request.failure()
                )
        );
    }

    public Page getPage() {
        if (page == null) {
            throw new IllegalStateException(
                    "O Playwright ainda nao foi inicializado."
            );
        }

        return page;
    }

    public Path saveTrace(String testName) {
        if (context == null || traceStopped) {
            return null;
        }

        Path tracePath = TRACES_DIRECTORY.resolve(
                safeName(testName) + ".zip"
        );

        context.tracing().stop(
                new Tracing.StopOptions()
                        .setPath(tracePath)
        );

        traceStopped = true;

        return tracePath;
    }

    public Path saveLogs(String testName) {
        Path logPath = LOGS_DIRECTORY.resolve(
                safeName(testName) + ".log"
        );

        List<String> content = new ArrayList<>();

        content.add("=== INFORMACOES DA EXECUCAO ===");
        content.add(
                "Data/hora: " + OffsetDateTime.now()
        );
        content.add(
                "Browser: " + ConfigManager.getBrowser()
        );
        content.add(
                "Headless: " + ConfigManager.isHeadless()
        );

        if (page != null && !page.isClosed()) {
            content.add("URL final: " + page.url());

            try {
                content.add(
                        "Titulo final: " + page.title()
                );
            } catch (Exception exception) {
                content.add(
                        "Titulo final indisponivel: "
                                + exception.getMessage()
                );
            }
        }

        content.add("");
        content.add("=== CONSOLE DO NAVEGADOR ===");

        if (consoleMessages.isEmpty()) {
            content.add(
                    "Nenhuma mensagem de console registrada."
            );
        } else {
            content.addAll(consoleMessages);
        }

        content.add("");
        content.add("=== ERROS JAVASCRIPT ===");

        if (pageErrors.isEmpty()) {
            content.add(
                    "Nenhum erro JavaScript registrado."
            );
        } else {
            content.addAll(pageErrors);
        }

        content.add("");
        content.add("=== REQUISICOES COM FALHA ===");

        if (failedRequests.isEmpty()) {
            content.add(
                    "Nenhuma requisicao com falha registrada."
            );
        } else {
            content.addAll(failedRequests);
        }

        try {
            Files.write(
                    logPath,
                    content,
                    StandardCharsets.UTF_8
            );
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Nao foi possivel salvar os logs.",
                    exception
            );
        }

        return logPath;
    }

    public void closeContext() {
        if (context != null && !contextClosed) {
            context.close();
            contextClosed = true;
        }
    }

    public Path saveVideo(String testName) {
        if (video == null) {
            return null;
        }

        Path destination = VIDEOS_DIRECTORY.resolve(
                safeName(testName) + ".webm"
        );

        try {
            Path rawVideo = video.path();

            Files.move(
                    rawVideo,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return destination;
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Nao foi possivel salvar o video.",
                    exception
            );
        }
    }

    @Override
    public void close() {
        try {
            closeContext();
        } finally {
            if (browser != null) {
                browser.close();
            }

            if (playwright != null) {
                playwright.close();
            }
        }
    }

    private void createDirectories() {
        try {
            Files.createDirectories(
                    SCREENSHOTS_DIRECTORY
            );

            Files.createDirectories(
                    TRACES_DIRECTORY
            );

            Files.createDirectories(
                    VIDEOS_DIRECTORY
            );

            Files.createDirectories(
                    RAW_VIDEOS_DIRECTORY
            );

            Files.createDirectories(
                    LOGS_DIRECTORY
            );

            Files.createDirectories(
                    Paths.get(
                            "target",
                            "allure-results"
                    )
            );
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Nao foi possivel criar as pastas "
                            + "de evidencias.",
                    exception
            );
        }
    }

    private String safeName(String value) {
        return value
                .replaceAll(
                        "[^a-zA-Z0-9-_]",
                        "_"
                )
                .replaceAll("_+", "_");
    }
}