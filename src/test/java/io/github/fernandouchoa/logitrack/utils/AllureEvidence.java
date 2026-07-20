package io.github.fernandouchoa.logitrack.utils;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class AllureEvidence {

    private AllureEvidence() {
    }

    public static void attachScreenshot(
            String name,
            byte[] screenshot
    ) {
        if (screenshot == null
                || screenshot.length == 0) {
            return;
        }

        Allure.addAttachment(
                name,
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );
    }

    public static void attachText(
            String name,
            String content
    ) {
        String safeContent =
                content == null ? "" : content;

        Allure.addAttachment(
                name,
                "text/plain",
                new ByteArrayInputStream(
                        safeContent.getBytes(
                                StandardCharsets.UTF_8
                        )
                ),
                ".txt"
        );
    }

    public static void attachFile(
            String name,
            String contentType,
            Path file,
            String extension
    ) {
        if (file == null || !Files.exists(file)) {
            return;
        }

        try (InputStream inputStream =
                     Files.newInputStream(file)) {

            Allure.addAttachment(
                    name,
                    contentType,
                    inputStream,
                    extension
            );
        } catch (IOException exception) {
            attachText(
                    "Erro ao anexar " + name,
                    exception.toString()
            );
        }
    }
}