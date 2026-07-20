package io.github.fernandouchoa.logitrack.utils;

import java.text.Normalizer;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MessageAssertions {

    private MessageAssertions() {
    }

    public static void assertContainsAll(
            String actualMessage,
            String... expectedFragments
    ) {
        assertFalse(
                actualMessage == null
                        || actualMessage.isBlank(),
                "Nenhuma mensagem foi exibida no topo da pÃ¡gina."
        );

        String normalizedActual = normalize(
                actualMessage
        );

        for (String fragment : expectedFragments) {
            String normalizedFragment = normalize(fragment);

            assertTrue(
                    normalizedActual.contains(
                            normalizedFragment
                    ),
                    "Mensagem inesperada. Esperado conter: '"
                            + fragment
                            + "'. Mensagem exibida: '"
                            + actualMessage
                            + "'."
            );
        }
    }

    private static String normalize(String value) {
        return Normalizer.normalize(
                        value,
                        Normalizer.Form.NFD
                )
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .trim()
                .replaceAll("\\s+", " ");
    }
}