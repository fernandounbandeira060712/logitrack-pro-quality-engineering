package io.github.fernandouchoa.logitrack.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import io.github.fernandouchoa.logitrack.pages.MaintenancePage;
import io.github.fernandouchoa.logitrack.pages.TripsPage;
import io.github.fernandouchoa.logitrack.pages.VehiclesPage;

import java.util.List;

public final class SidebarComponent {

    private final Page page;

    public SidebarComponent(Page page) {
        this.page = page;
    }

    public DashboardPage accessDashboard() {
        clickLinkByHref("/dashboard");
        return new DashboardPage(page);
    }

    public VehiclesPage accessVehicles() {
        clickLinkByHref("/veiculos");
        return new VehiclesPage(page);
    }

    public MaintenancePage accessMaintenance() {
        clickLinkContainingHref("manuten");
        return new MaintenancePage(page);
    }

    public TripsPage accessTrips() {
        clickLinkByHref("/viagens");
        return new TripsPage(page);
    }

    public List<String> getVisibleNavigationItems() {
        return page.locator(
                "aside a:visible, nav a:visible"
        ).allInnerTexts()
                .stream()
                .map(String::trim)
                .filter(text -> !text.isBlank())
                .toList();
    }

    private void clickLinkByHref(String href) {
        Locator link = page.locator(
                "a[href='" + href + "']"
        );

        if (link.count() == 0) {
            link = page.locator(
                    "a[href$='" + href + "']"
            );
        }

        if (link.count() == 0) {
            throw new IllegalStateException(
                    "Link nao encontrado para a rota: " + href
            );
        }

        link.first().click();
        page.waitForTimeout(1200);
    }

    private void clickLinkContainingHref(String fragment) {
        Locator link = page.locator(
                "a[href*='" + fragment + "']"
        );

        if (link.count() == 0) {
            throw new IllegalStateException(
                    "Link nao encontrado contendo: " + fragment
            );
        }

        link.first().click();
        page.waitForTimeout(1200);
    }
}