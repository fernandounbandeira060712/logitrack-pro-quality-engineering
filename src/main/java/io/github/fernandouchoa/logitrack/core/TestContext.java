package io.github.fernandouchoa.logitrack.core;

import com.microsoft.playwright.Page;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class TestContext {

    private final Page page;
    private final Map<String, Object> values = new HashMap<>();

    public TestContext(Page page) {
        this.page = page;
    }

    public Page page() {
        return page;
    }

    public void put(String key, Object value) {
        values.put(key, value);
    }

    public <T> Optional<T> get(String key, Class<T> type) {
        Object value = values.get(key);
        return value == null
                ? Optional.empty()
                : Optional.of(type.cast(value));
    }

    public void clear() {
        values.clear();
    }
}