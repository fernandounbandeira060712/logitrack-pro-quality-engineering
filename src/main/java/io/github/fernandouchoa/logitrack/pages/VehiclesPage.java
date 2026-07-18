package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.components.ToastComponent;
import io.github.fernandouchoa.logitrack.models.VehicleData;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class VehiclesPage extends BasePage {

    private final ToastComponent toast;

    public VehiclesPage(Page page) {
        super(page);
        toast = new ToastComponent(page);
    }

    public VehiclesPage open() {
        navigate(Routes.VEHICLES);
        return this;
    }

    public boolean isLoaded() {
        return page.url().toLowerCase().contains("vehicle")
                || page.getByText("VeÃ­culos").count() > 0;
    }

    public VehiclesPage clickNewVehicle() {
        clickFirstButton("Novo", "Cadastrar", "Adicionar");
        return this;
    }

    public VehiclesPage fillVehicleForm(VehicleData vehicle) {
        fillFirstVisible(vehicle.plate(), "Placa");
        fillFirstVisible(vehicle.model(), "Modelo");
        fillFirstVisible(vehicle.brand(), "Marca");
        fillFirstVisible(vehicle.year(), "Ano");
        fillFirstVisible(vehicle.status(), "Status", "SituaÃ§Ã£o");
        return this;
    }

    public VehiclesPage save() {
        clickFirstButton("Salvar", "Cadastrar", "Adicionar");
        return this;
    }

    public VehiclesPage searchByPlate(String plate) {
        searchInput().fill(plate);
        return this;
    }

    public boolean containsVehicle(String plate) {
        return page.getByText(plate).count() > 0;
    }

    public String getFeedbackMessage() {
        return toast.getMessage();
    }

    private Locator searchInput() {
        return page.locator(
                "input[type='search'], input[placeholder*='Buscar'], " +
                "input[placeholder*='Pesquisar']"
        ).first();
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