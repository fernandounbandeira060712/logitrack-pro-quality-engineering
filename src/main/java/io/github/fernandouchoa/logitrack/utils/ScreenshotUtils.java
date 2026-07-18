package io.github.fernandouchoa.logitrack.utils;

import com.microsoft.playwright.Page;

import java.nio.file.Paths;

public final class ScreenshotUtils {

    private ScreenshotUtils() {
    }

    public static byte[] capture(Page page, String testName) {
        String safeName = testName
                .replaceAll("[^a-zA-Z0-9-_]", "_")
                .replaceAll("_+", "_");

        return page.screenshot(
                new Page.ScreenshotOptions()
                        .setPath(Paths.get(
                                "target",
                                "screenshots",
                                safeName + ".png"
                        ))
                        .setFullPage(true)
        );
    }
}