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

import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class ModuleDiscoveryTests extends BaseTest {

    @Test
    @DisplayName("01 - Mapear Dashboard")
    void shouldMapDashboard() {
        authenticate();
        printModuleReport("Dashboard");
        assertFalse(page.url().isBlank());
    }

    @Test
    @DisplayName("02 - Mapear Veiculos")
    void shouldMapVehicles() {
        DashboardPage dashboard = authenticate();
        dashboard.sidebar().accessVehicles();
        printModuleReport("Veiculos");
        assertFalse(page.url().isBlank());
    }

    @Test
    @DisplayName("03 - Mapear Manutencao")
    void shouldMapMaintenance() {
        DashboardPage dashboard = authenticate();
        dashboard.sidebar().accessMaintenance();
        printModuleReport("Manutencao");
        assertFalse(page.url().isBlank());
    }

    @Test
    @DisplayName("04 - Mapear Viagens")
    void shouldMapTrips() {
        DashboardPage dashboard = authenticate();
        dashboard.sidebar().accessTrips();
        printModuleReport("Viagens");
        assertFalse(page.url().isBlank());
    }

    private DashboardPage authenticate() {
        Assumptions.assumeTrue(
                ConfigManager.hasCredentials(),
                "Configure email e password no comando Maven."
        );

        return loginPage()
                .open()
                .loginAs(
                        ConfigManager.getEmail(),
                        ConfigManager.getPassword()
                );
    }

    private void printModuleReport(String moduleName) {
        page.waitForTimeout(1500);

        System.out.println();
        System.out.println("==================================================");
        System.out.println("MODULO: " + moduleName);
        System.out.println("URL: " + page.url());
        System.out.println("PAGE TITLE: " + page.title());
        System.out.println("HEADINGS: " + visibleTexts("h1, h2, h3"));
        System.out.println("BUTTONS: " + visibleTexts("button:visible"));
        System.out.println("LINKS: " + visibleTexts("a:visible"));
        System.out.println("LABELS: " + visibleTexts("label:visible"));
        System.out.println("TABLE HEADERS: " + visibleTexts("th:visible"));
        System.out.println("FIELDS: " + describeFields());
        System.out.println("DIALOGS: " + visibleTexts("[role='dialog']:visible"));
        System.out.println("==================================================");
        System.out.println();
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
        Locator fields = page.locator(
                "input:visible, select:visible, textarea:visible"
        );

        Object result = fields.evaluateAll(
                """
                elements => elements.map((element, index) => {
                    const tag = element.tagName.toLowerCase();
                    const type = element.getAttribute('type') || '';
                    const name = element.getAttribute('name') || '';
                    const id = element.getAttribute('id') || '';
                    const placeholder = element.getAttribute('placeholder') || '';
                    const ariaLabel = element.getAttribute('aria-label') || '';

                    return [
                        `index=${index}`,
                        `tag=${tag}`,
                        `type=${type}`,
                        `name=${name}`,
                        `id=${id}`,
                        `placeholder=${placeholder}`,
                        `aria-label=${ariaLabel}`
                    ].join(' | ');
                })
                """
        );

        return (List<String>) result;
    }
}