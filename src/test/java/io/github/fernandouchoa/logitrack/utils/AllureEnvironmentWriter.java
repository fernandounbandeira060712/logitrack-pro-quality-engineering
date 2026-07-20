package io.github.fernandouchoa.logitrack.utils;

import io.github.fernandouchoa.logitrack.config.ConfigManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class AllureEnvironmentWriter {

    private static boolean written;

    private AllureEnvironmentWriter() {
    }

    public static synchronized void write() {
        if (written) {
            return;
        }

        Path resultsDirectory = Paths.get(
                "target",
                "allure-results"
        );

        Path environmentFile = resultsDirectory.resolve(
                "environment.properties"
        );

        List<String> content = List.of(
                "Aplicacao=LogiTrack Pro",
                "Base_URL=" + ConfigManager.getBaseUrl(),
                "Browser=" + ConfigManager.getBrowser(),
                "Headless=" + ConfigManager.isHeadless(),
                "Java=" + System.getProperty(
                        "java.version"
                ),
                "Sistema_Operacional="
                        + System.getProperty("os.name")
                        + " "
                        + System.getProperty("os.version")
        );

        try {
            Files.createDirectories(resultsDirectory);

            Files.write(
                    environmentFile,
                    content,
                    StandardCharsets.UTF_8
            );

            written = true;
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Nao foi possivel criar o arquivo "
                            + "de ambiente do Allure.",
                    exception
            );
        }
    }
}