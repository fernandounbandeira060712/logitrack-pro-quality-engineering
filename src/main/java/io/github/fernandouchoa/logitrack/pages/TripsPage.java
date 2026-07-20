package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.components.RadixSelectComponent;
import io.github.fernandouchoa.logitrack.components.ToastComponent;
import io.github.fernandouchoa.logitrack.models.TripData;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class TripsPage extends BasePage {

    private final ToastComponent toast;
    private final RadixSelectComponent select;

    private final Locator searchInput;
    private final Locator newTripButton;

    public TripsPage(Page page) {
        super(page);

        toast = new ToastComponent(page);
        select = new RadixSelectComponent(page);

        searchInput = page.locator(
                "input[placeholder='Buscar por origem ou destino...']"
        );

        newTripButton = page.locator(
                "button:visible"
        ).filter(
                new Locator.FilterOptions()
                        .setHasText("Nova Viagem")
        ).first();
    }

    public TripsPage open() {
        navigate(Routes.TRIPS);
        return this;
    }

    public boolean isLoaded() {
        return page.url().contains("/viagens");
    }

    public TripsPage clickNewTrip() {
        click(newTripButton);
        visibleDialog();
        return this;
    }

    public TripsPage fillTripForm(TripData trip) {
        Locator dialog = visibleDialog();

        select.selectByText(
                dialog.locator(
                        "button[role='combobox']"
                ).first(),
                trip.vehiclePlate()
        );

        fill(dialog.locator("#origem"), trip.origin());
        fill(
                dialog.locator("#destino"),
                trip.destination()
        );
        fill(
                dialog.locator("#dataSaida"),
                trip.departureDate()
        );
        fill(
                dialog.locator("#dataChegada"),
                trip.arrivalDate()
        );
        fill(
                dialog.locator("#kmPercorrida"),
                trip.distanceKm()
        );

        return this;
    }

    public TripsPage save() {
        clickDialogButton("Adicionar");
        return this;
    }

    public TripsPage createTrip(TripData trip) {
        return clickNewTrip()
                .fillTripForm(trip)
                .save();
    }

    public TripsPage search(String value) {
        fill(searchInput, value);
        return this;
    }

    public boolean containsTrip(String value) {
        return rowByText(value).count() > 0;
    }

    public TripsPage editByText(String value) {
        Locator button = rowByText(value).locator(
                "button:has(svg.lucide-pencil)"
        ).first();

        click(button);
        visibleDialog();
        return this;
    }

    public TripsPage deleteByText(String value) {
        Locator button = rowByText(value).locator(
                "button:has(svg.lucide-trash-2)"
        ).first();

        click(button);
        return this;
    }

    public TripsPage cancel() {
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
                    "Dialogo de viagem nao encontrado."
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