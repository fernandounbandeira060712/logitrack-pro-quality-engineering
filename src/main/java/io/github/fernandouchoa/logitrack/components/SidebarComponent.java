package io.github.fernandouchoa.logitrack.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import io.github.fernandouchoa.logitrack.pages.IndicatorsPage;
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
        clickItem("Dashboard");
        return new DashboardPage(page);
    }

    public VehiclesPage accessVehicles() {
        clickItem("VeÃ­culos");
        return new VehiclesPage(page);
    }

    public TripsPage accessTrips() {
        clickItem("Viagens");
        return new TripsPage(page);
    }

    public MaintenancePage accessMaintenance() {
        clickItem("ManutenÃ§Ãµes");
        return new MaintenancePage(page);
    }

    public IndicatorsPage accessIndicators() {
        clickItem("Indicadores");
        return new IndicatorsPage(page);
    }

    public List<String> getVisibleNavigationItems() {
        return page.locator("aside a:visible, nav a:visible")
                .allInnerTexts()
                .stream()
                .map(String::trim)
                .filter(text -> !text.isBlank())
                .toList();
    }

    private void clickItem(String text) {
        Locator item = page.locator("aside a, nav a, aside button, nav button")
                .filter(new Locator.FilterOptions().setHasText(text));

        if (item.count() == 0) {
            item = page.getByText(
                    text,
                    new Page.GetByTextOptions().setExact(true)
            );
        }

        item.first().click();
    }
}