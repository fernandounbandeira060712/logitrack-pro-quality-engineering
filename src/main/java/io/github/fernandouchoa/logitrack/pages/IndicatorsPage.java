package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class IndicatorsPage extends BasePage {

    public IndicatorsPage(Page page) {
        super(page);
    }

    public IndicatorsPage open() {
        navigate(Routes.INDICATORS);
        return this;
    }

    public boolean isLoaded() {
        return page.url().toLowerCase().contains("indicator")
                || page.getByText("Indicadores").count() > 0;
    }

    public int getVisibleIndicatorCount() {
        return page.locator(
                "[class*='card']:visible, [class*='indicator']:visible"
        ).count();
    }

    public boolean hasVisibleChart() {
        return page.locator(
                "canvas:visible, svg[class*='chart']:visible, " +
                "[class*='recharts']:visible"
        ).count() > 0;
    }
}