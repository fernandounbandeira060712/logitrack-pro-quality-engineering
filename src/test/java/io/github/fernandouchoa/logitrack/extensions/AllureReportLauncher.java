package io.github.fernandouchoa.logitrack.extensions;

import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.LauncherSessionListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public final class AllureReportLauncher
        implements LauncherSessionListener {

    private static final AtomicBoolean OPENING_SCHEDULED =
            new AtomicBoolean(false);

    @Override
    public void launcherSessionClosed(
            LauncherSession session
    ) {
        if (!shouldOpenReport()) {
            return;
        }

        if (!hasAllureResults()) {
            System.out.println(
                    "Allure: nenhum resultado encontrado. "
                            + "O relatorio nao sera aberto."
            );
            return;
        }

        if (!OPENING_SCHEDULED.compareAndSet(
                false,
                true
        )) {
            return;
        }

        try {
            scheduleReportOpening();

            System.out.println(
                    "Allure: abertura automatica agendada "
                            + "para o fim da execucao."
            );
        } catch (IOException exception) {
            System.err.println(
                    "Allure: nao foi possivel agendar "
                            + "a abertura do relatorio: "
                            + exception.getMessage()
            );
        }
    }

    private boolean shouldOpenReport() {
        if (isCiEnvironment()) {
            return false;
        }

        return Boolean.parseBoolean(
                System.getProperty(
                        "openAllure",
                        "true"
                )
        );
    }

    private boolean isCiEnvironment() {
        return "true".equalsIgnoreCase(
                System.getenv("CI")
        ) || "true".equalsIgnoreCase(
                System.getenv("GITHUB_ACTIONS")
        );
    }

    private boolean hasAllureResults() {
        Path resultsDirectory = Paths.get(
                "target",
                "allure-results"
        );

        if (!Files.isDirectory(resultsDirectory)) {
            return false;
        }

        try (Stream<Path> files =
                     Files.list(resultsDirectory)) {

            return files.anyMatch(path ->
                    path.getFileName()
                            .toString()
                            .endsWith("-result.json")
            );
        } catch (IOException exception) {
            return false;
        }
    }

    private void scheduleReportOpening()
            throws IOException {

        Path projectDirectory = Paths.get(
                System.getProperty("user.dir")
        ).toAbsolutePath();

        long currentProcessId =
                ProcessHandle.current().pid();

        if (isWindows()) {
            openOnWindows(
                    projectDirectory,
                    currentProcessId
            );
            return;
        }

        openOnUnix(
                projectDirectory,
                currentProcessId
        );
    }

    private void openOnWindows(
            Path projectDirectory,
            long processId
    ) throws IOException {

        String escapedDirectory =
                projectDirectory.toString()
                        .replace("'", "''");

        String command =
                "$processIdToWait = "
                        + processId
                        + "; "
                        + "while (Get-Process "
                        + "-Id $processIdToWait "
                        + "-ErrorAction SilentlyContinue) { "
                        + "Start-Sleep -Milliseconds 500 "
                        + "}; "
                        + "Start-Sleep -Seconds 2; "
                        + "Set-Location -LiteralPath '"
                        + escapedDirectory
                        + "'; "
                        + "mvn -q allure:serve";

        new ProcessBuilder(
                "powershell.exe",
                "-NoExit",
                "-ExecutionPolicy",
                "Bypass",
                "-Command",
                command
        )
                .directory(
                        projectDirectory.toFile()
                )
                .start();
    }

    private void openOnUnix(
            Path projectDirectory,
            long processId
    ) throws IOException {

        String escapedDirectory =
                projectDirectory.toString()
                        .replace("'", "'\\''");

        String command =
                "while kill -0 "
                        + processId
                        + " 2>/dev/null; "
                        + "do sleep 1; done; "
                        + "sleep 2; "
                        + "cd '"
                        + escapedDirectory
                        + "' && "
                        + "mvn -q allure:serve";

        new ProcessBuilder(
                "sh",
                "-c",
                command
        )
                .directory(
                        projectDirectory.toFile()
                )
                .start();
    }

    private boolean isWindows() {
        return System.getProperty("os.name")
                .toLowerCase()
                .contains("win");
    }
}