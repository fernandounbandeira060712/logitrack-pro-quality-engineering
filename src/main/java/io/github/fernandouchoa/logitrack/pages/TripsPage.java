package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.components.ToastComponent;
import io.github.fernandouchoa.logitrack.models.TripData;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class TripsPage extends BasePage {

    private final ToastComponent toast;

    public TripsPage(Page page) {
        super(page);
        toast = new ToastComponent(page);
    }

    public TripsPage open() {
        navigate(Routes.TRIPS);
        return this;
    }

    public boolean isLoaded() {
        return page.url().toLowerCase().contains("trip")
                || page.getByText("Viagens").count() > 0;
    }

    public TripsPage clickNewTrip() {
        clickFirstButton("Nova", "Cadastrar", "Adicionar");
        return this;
    }

    public TripsPage fillTripForm(TripData trip) {
        fillFirstVisible(trip.origin(), "Origem");
        fillFirstVisible(trip.destination(), "Destino");
        fillFirstVisible(trip.departureDate(), "Data de saÃ­da", "Partida");
        fillFirstVisible(trip.expectedArrivalDate(), "PrevisÃ£o", "Chegada");
        fillFirstVisible(trip.vehiclePlate(), "VeÃ­culo", "Placa");
        fillFirstVisible(trip.driver(), "Motorista");
        return this;
    }

    public TripsPage save() {
        clickFirstButton("Salvar", "Cadastrar", "Adicionar");
        return this;
    }

    public TripsPage search(String value) {
        page.locator(
                "input[type='search'], input[placeholder*='Buscar'], " +
                "input[placeholder*='Pesquisar']"
        ).first().fill(value);
        return this;
    }

    public boolean containsTrip(String value) {
        return page.getByText(value).count() > 0;
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