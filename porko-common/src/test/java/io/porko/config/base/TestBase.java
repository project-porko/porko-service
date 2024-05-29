package io.porko.config.base;

import static io.porko.config.base.TestBase.TEST;

import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@ActiveProfiles(TEST)
@TestConstructor(autowireMode = AutowireMode.ALL)
public abstract class TestBase {
    static final String TEST = "test";
    protected static AtomicReference<Integer> intVariable;
    protected static AtomicReference<Long> lonVariable;

    @BeforeEach
    void setUp() {
        intVariable = new AtomicReference<>(1);
        lonVariable = new AtomicReference<>(1L);
    }

    public static Integer nextIndex() {
        return intVariable.getAndSet(intVariable.get() + 1);
    }

    public Long nextId() {
        return lonVariable.getAndSet(lonVariable.get() + 1);
    }

    public static Long nextId(AtomicReference<Long> lonVariable) {
        return lonVariable.getAndSet(lonVariable.get() + 1);
    }
}
