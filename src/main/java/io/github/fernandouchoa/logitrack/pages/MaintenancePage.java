package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.components.ToastComponent;
import io.github.fernandouchoa.logitrack.models.MaintenanceData;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class MaintenancePage extends BasePage {

    private final ToastComponent toast;

    public MaintenancePage(Page page) {
        super(page);
        toast = new ToastComponent(page);
    }

    public MaintenancePage open() {
        navigate(Routes.MAINTENANCE);
        return this;
    }

    public boolean isLoaded() {
        return page.url().toLowerCase().contains("maintenance")
                || page.getByText("ManutenÃ§Ãµes").count() > 0;
    }

    public MaintenancePage clickNewMaintenance() {
        clickFirstButton("Nova", "Cadastrar", "Adicionar");
        return this;
    }

    public MaintenancePage fillMaintenanceForm(MaintenanceData data) {
        fillFirstVisible(data.vehiclePlate(), "VeÃ­culo", "Placa");
        fillFirstVisible(data.maintenanceType(), "Tipo");
        fillFirstVisible(data.scheduledDate(), "Data");
        fillFirstVisible(data.description(), "DescriÃ§Ã£o", "ObservaÃ§Ã£o");
        fillFirstVisible(data.cost(), "Custo", "Valor");
        return this;
    }

    public MaintenancePage save() {
        clickFirstButton("Salvar", "Cadastrar", "Adicionar");
        return this;
    }

    public MaintenancePage searchByVehicle(String plate) {
        page.locator(
                "input[type='search'], input[placeholder*='Buscar'], " +
                "input[placeholder*='Pesquisar']"
        ).first().fill(plate);
        return this;
    }

    public boolean containsMaintenance(String plate) {
        return page.getByText(plate).count() > 0;
    }

    public String getFeedbackMessage() {
        return toast.getMessage();
    }

    private void fillFirstVisible(String value, String... labels) {
        for (String label : labels) {
            Locator field = page.getByLabel(label);
            if (field.count() > 0 && field.first().isVisible()) {
                field.first().fill(value);
                return;
            }
        }
    }

    private void clickFirstButton(String... texts) {
        for (String text : texts) {
            Locator button = page.locator("button")
                    .filter(new Locator.FilterOptions().setHasText(text));
            if (button.count() > 0 && button.first().isVisible()) {
                button.first().click();
                return;
            }
        }
        throw new IllegalStateException("BotÃ£o esperado nÃ£o encontrado.");
    }
}