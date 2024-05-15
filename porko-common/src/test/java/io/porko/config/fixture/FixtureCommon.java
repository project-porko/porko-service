package io.porko.config.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.FixtureMonkeyBuilder;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.arbitraries.StringArbitrary;

public class FixtureCommon {
    private static final FixtureMonkeyBuilder builder = FixtureMonkey.builder();

    public static FixtureMonkey entityType() {
        return builder
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .build();
    }

    public static FixtureMonkey dtoType() {
        return builder
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .build();
    }

    public static FixtureMonkey withValidated() {
        return builder
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .plugin(new JakartaValidationPlugin())
            .build();
    }

    public static FixtureMonkey builderType() {
        return builder
            .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
            .build();
    }

    public static StringArbitrary randomString() {
        return Arbitraries.strings();
    }

    public static StringArbitrary randomAlpha() {
        return Arbitraries.strings().alpha();
    }
}
