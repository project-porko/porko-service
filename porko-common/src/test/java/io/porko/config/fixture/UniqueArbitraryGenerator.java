package io.porko.config.fixture;

import com.navercorp.fixturemonkey.api.arbitrary.CombinableArbitrary;
import com.navercorp.fixturemonkey.api.generator.ArbitraryGenerator;
import com.navercorp.fixturemonkey.api.generator.ArbitraryGeneratorContext;
import java.util.HashSet;
import java.util.Set;

public class UniqueArbitraryGenerator implements ArbitraryGenerator {
    private static final Set<Object> UNIQUE = new HashSet<>();

    private final ArbitraryGenerator delegate;

    public UniqueArbitraryGenerator(ArbitraryGenerator delegate) {
        this.delegate = delegate;
    }

    @Override
    public CombinableArbitrary generate(ArbitraryGeneratorContext context) {
        return delegate.generate(context)
            .filter(
                obj -> {
                    if (!UNIQUE.contains(obj)) {
                        UNIQUE.add(obj);
                        return true;
                    }
                    return false;
                }
            );
    }
}
