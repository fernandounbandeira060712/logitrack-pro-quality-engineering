package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.components.RadixSelectComponent;
import io.github.fernandouchoa.logitrack.components.ToastComponent;
import io.github.fernandouchoa.logitrack.models.VehicleData;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class VehiclesPage extends BasePage {

    private final ToastComponent toast;
    private final RadixSelectComponent select;

    private final Locator searchInput;
    private final Locator addVehicleButton;

    public VehiclesPage(Page page) {
        super(page);

        toast = new ToastComponent(page);
        select = new RadixSelectComponent(page);

        searchInput = page.locator(
                "input[placeholder='Buscar por placa, modelo...']"
        );

        addVehicleButton = page.locator(
                "button:visible"
        ).filter(
                new Locator.FilterOptions()
                        .setHasText("Adicionar Ve")
        ).first();
    }

    public VehiclesPage open() {
        navigate(Routes.VEHICLES);
        return this;
    }

    public boolean isLoaded() {
        return page.url().contains("/veiculos")
                && page.locator("h1")
                        .filter(
                                new Locator.FilterOptions()
                                        .setHasText("Ve")
                        )
                        .count() > 0;
    }

    public VehiclesPage clickNewVehicle() {
        click(addVehicleButton);
        visibleDialog();
        return this;
    }

    public VehiclesPage fillVehicleForm(
            VehicleData vehicle
    ) {
        Locator dialog = visibleDialog();

        fill(dialog.locator("#placa"), vehicle.plate());
        fill(dialog.locator("#modelo"), vehicle.model());
        select.selectByText(
                dialog.locator(
                        "button[role='combobox']"
                ).first(),
                vehicle.type()
        );
        fill(dialog.locator("#ano"), vehicle.year());

        return this;
    }

    public VehiclesPage save() {
        clickDialogButton("Criar");
        return this;
    }

    public VehiclesPage createVehicle(
            VehicleData vehicle
    ) {
        return clickNewVehicle()
                .fillVehicleForm(vehicle)
                .save();
    }

    public VehiclesPage searchByPlate(String plate) {
        fill(searchInput, plate);
        return this;
    }

    public boolean containsVehicle(String plate) {
        return rowByText(plate).count() > 0;
    }

    public VehiclesPage editByPlate(String plate) {
        Locator button = rowByText(plate).locator(
                "button:has(svg.lucide-pencil)"
        ).first();

        click(button);
        visibleDialog();
        return this;
    }

    public VehiclesPage deleteByPlate(String plate) {
        Locator button = rowByText(plate).locator(
                "button:has(svg.lucide-trash-2)"
        ).first();

        click(button);
        return this;
    }

    public VehiclesPage cancel() {
        clickDialogButton("Cancelar");
        return this;
    }

    public String getFeedbackMessage() {
        return toast.getMessage();
    }

    private Locator rowByText(String text) {
        return page.locator("tbody tr")
                .filter(
                        new Locator.FilterOptions()
                                .setHasText(text)
                )
                .first();
    }

    private Locator visibleDialog() {
        Locator dialog = page.locator(
                "[role='dialog']:visible"
        ).first();

        if (dialog.count() == 0) {
            throw new IllegalStateException(
                    "Dialogo de veiculo nao encontrado."
            );
        }

        return dialog;
    }

    private void clickDialogButton(String text) {
        Locator button = visibleDialog()
                .locator("button:visible")
                .filter(
                        new Locator.FilterOptions()
                                .setHasText(text)
                )
                .first();

        if (button.count() == 0) {
            throw new IllegalStateException(
                    "Botao do dialogo nao encontrado: "
                            + text
            );
        }

        click(button);
    }
}