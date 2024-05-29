package io.porko.config.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.customizer.InnerSpec;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.arbitraries.StringArbitrary;

public class FixtureCommon {
    public static FixtureMonkey entityType() {
        return FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .build();
    }

    public static FixtureMonkey dtoType() {
        return FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .plugin(new JakartaValidationPlugin())
            .build();
    }

    public static FixtureMonkey withValidated() {
        return FixtureMonkey.builder()
            .defaultArbitraryGenerator(UniqueArbitraryGenerator::new)
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .defaultNotNull(true)
            .plugin(new JakartaValidationPlugin())
            .build();
    }

    public static FixtureMonkey builderType() {
        return FixtureMonkey.builder()
            .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
            .build();
    }

    public static InnerSpec setProperty(String property, Object value) {
        return new InnerSpec().property(property, value);
    }

    public static StringArbitrary randomString() {
        return Arbitraries.strings();
    }

    public static StringArbitrary randomAlpha() {
        return Arbitraries.strings().alpha();
    }
}
