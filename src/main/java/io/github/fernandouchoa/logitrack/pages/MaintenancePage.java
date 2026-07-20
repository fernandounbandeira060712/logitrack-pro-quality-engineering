package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.components.RadixSelectComponent;
import io.github.fernandouchoa.logitrack.components.ToastComponent;
import io.github.fernandouchoa.logitrack.models.MaintenanceData;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class MaintenancePage extends BasePage {

    private final ToastComponent toast;
    private final RadixSelectComponent select;

    private final Locator searchInput;
    private final Locator newMaintenanceButton;

    public MaintenancePage(Page page) {
        super(page);

        toast = new ToastComponent(page);
        select = new RadixSelectComponent(page);

        searchInput = page.locator(
                "input[placeholder='Buscar por placa, modelo, serviÃ§o...'], "
                        + "input[placeholder*='Buscar por placa, modelo']"
        ).first();

        newMaintenanceButton = page.locator(
                "button:visible"
        ).filter(
                new Locator.FilterOptions()
                        .setHasText("Nova Manuten")
        ).first();
    }

    public MaintenancePage open() {
        navigate(Routes.MAINTENANCE);
        return this;
    }

    public boolean isLoaded() {
        return page.url().contains("/manutencao");
    }

    public MaintenancePage clickNewMaintenance() {
        click(newMaintenanceButton);
        visibleDialog();
        return this;
    }

    public MaintenancePage fillMaintenanceForm(
            MaintenanceData data
    ) {
        Locator dialog = visibleDialog();

        select.selectByText(
                dialog.locator(
                        "button[role='combobox']"
                ).first(),
                data.vehiclePlate()
        );

        fill(
                dialog.locator("#tipoServico"),
                data.serviceType()
        );
        fill(
                dialog.locator("#dataInicio"),
                data.startDate()
        );
        fill(
                dialog.locator("#dataFinalizacao"),
                data.endDate()
        );
        fill(
                dialog.locator("#custoEstimado"),
                data.estimatedCost()
        );

        return this;
    }

    public MaintenancePage save() {
        clickDialogButton("Agendar");
        return this;
    }

    public MaintenancePage schedule(
            MaintenanceData data
    ) {
        return clickNewMaintenance()
                .fillMaintenanceForm(data)
                .save();
    }

    public MaintenancePage searchByVehicle(
            String plate
    ) {
        fill(searchInput, plate);
        return this;
    }

    public boolean containsMaintenance(String value) {
        return rowByText(value).count() > 0;
    }

    public MaintenancePage editByText(String value) {
        Locator button = rowByText(value).locator(
                "button:has(svg.lucide-pencil)"
        ).first();

        click(button);
        visibleDialog();
        return this;
    }

    public MaintenancePage deleteByText(String value) {
        Locator button = rowByText(value).locator(
                "button:has(svg.lucide-trash-2)"
        ).first();

        click(button);
        return this;
    }

    public MaintenancePage cancel() {
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
                    "Dialogo de manutencao nao encontrado."
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