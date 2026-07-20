package io.github.fernandouchoa.logitrack.tests;

import com.microsoft.playwright.Locator;
import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class FormDiscoveryTests extends BaseTest {

    private static final int REGEX_FLAGS =
            Pattern.CASE_INSENSITIVE;

    @Test
    @DisplayName("01 - Mapear formulario de veiculos")
    void shouldMapVehicleForm() {
        DashboardPage dashboard = authenticate();

        dashboard.sidebar().accessVehicles();

        clickActionButton(
                Pattern.compile(
                        "adicionar.*ve",
                        REGEX_FLAGS
                )
        );

        printFormReport("FORMULARIO DE VEICULOS");

        assertTrue(hasVisibleFormOrDialog());
    }

    @Test
    @DisplayName("02 - Mapear formulario de manutencao")
    void shouldMapMaintenanceForm() {
        DashboardPage dashboard = authenticate();

        dashboard.sidebar().accessMaintenance();

        System.out.println(
                "BOTOES DA TELA DE MANUTENCAO: "
                        + page.locator("button:visible").allInnerTexts()
        );

        clickActionButton(
                Pattern.compile(
                        "(nova|novo|adicionar|agendar).*manuten",
                        REGEX_FLAGS
                )
        );

        printFormReport("FORMULARIO DE MANUTENCAO");

        assertTrue(hasVisibleFormOrDialog());
    }

    @Test
    @DisplayName("03 - Mapear formulario de viagens")
    void shouldMapTripForm() {
        DashboardPage dashboard = authenticate();

        dashboard.sidebar().accessTrips();

        clickActionButton(
                Pattern.compile(
                        "nova.*viagem",
                        REGEX_FLAGS
                )
        );

        printFormReport("FORMULARIO DE VIAGENS");

        assertTrue(hasVisibleFormOrDialog());
    }

    private DashboardPage authenticate() {
        Assumptions.assumeTrue(
                ConfigManager.hasCredentials(),
                "Informe email e password no comando Maven."
        );

        return loginPage()
                .open()
                .loginAs(
                        ConfigManager.getEmail(),
                        ConfigManager.getPassword()
                );
    }

    private void clickActionButton(Pattern namePattern) {
        Locator button = page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new com.microsoft.playwright.Page.GetByRoleOptions()
                        .setName(namePattern)
        );

        if (button.count() == 0) {
            throw new IllegalStateException(
                    "Botao de abertura do formulario nao encontrado. "
                            + "Botoes visiveis: "
                            + page.locator("button:visible").allInnerTexts()
            );
        }

        button.first().click();
        page.waitForTimeout(1200);
    }

    private void printFormReport(String title) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println(title);
        System.out.println("URL: " + page.url());
        System.out.println("HEADINGS: " + visibleTexts("h1, h2, h3"));
        System.out.println("BUTTONS: " + visibleTexts("button:visible"));
        System.out.println("LABELS: " + visibleTexts("label:visible"));
        System.out.println("FIELDS: " + describeFields());
        System.out.println("SELECT OPTIONS: " + describeSelectOptions());
        System.out.println("DIALOGS: " + visibleTexts("[role='dialog']:visible"));
        System.out.println("FORM TEXT: " + visibleTexts("form:visible"));
        System.out.println("==================================================");
        System.out.println();
    }

    private boolean hasVisibleFormOrDialog() {
        return page.locator(
                "form:visible, [role='dialog']:visible, "
                        + "input:visible, select:visible, textarea:visible"
        ).count() > 0;
    }

    private List<String> visibleTexts(String selector) {
        return page.locator(selector)
                .allInnerTexts()
                .stream()
                .map(String::trim)
                .filter(text -> !text.isBlank())
                .distinct()
                .toList();
    }

    @SuppressWarnings("unchecked")
    private List<String> describeFields() {
        Object result = page.locator(
                "input:visible, select:visible, textarea:visible"
        ).evaluateAll(
                """
                elements => elements.map((element, index) => {
                    const tag = element.tagName.toLowerCase();
                    const type = element.getAttribute('type') || '';
                    const name = element.getAttribute('name') || '';
                    const id = element.getAttribute('id') || '';
                    const placeholder =
                        element.getAttribute('placeholder') || '';
                    const ariaLabel =
                        element.getAttribute('aria-label') || '';
                    const required = element.required || false;
                    const min = element.getAttribute('min') || '';
                    const max = element.getAttribute('max') || '';
                    const minLength =
                        element.getAttribute('minlength') || '';
                    const maxLength =
                        element.getAttribute('maxlength') || '';

                    return [
                        `index=${index}`,
                        `tag=${tag}`,
                        `type=${type}`,
                        `name=${name}`,
                        `id=${id}`,
                        `placeholder=${placeholder}`,
                        `aria-label=${ariaLabel}`,
                        `required=${required}`,
                        `min=${min}`,
                        `max=${max}`,
                        `minlength=${minLength}`,
                        `maxlength=${maxLength}`
                    ].join(' | ');
                })
                """
        );

        return (List<String>) result;
    }

    @SuppressWarnings("unchecked")
    private List<String> describeSelectOptions() {
        Object result = page.locator(
                "select:visible"
        ).evaluateAll(
                """
                elements => elements.map((element, index) => ({
                    selectIndex: index,
                    options: Array.from(element.options)
                        .map(option => ({
                            text: option.textContent.trim(),
                            value: option.value
                        }))
                })).map(item =>
                    `selectIndex=${item.selectIndex} | options=` +
                    JSON.stringify(item.options)
                )
                """
        );

        return (List<String>) result;
    }
}