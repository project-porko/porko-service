package io.porko.config.base;

import static io.porko.config.base.TestBase.TEST;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@ActiveProfiles(TEST)
@TestConstructor(autowireMode = AutowireMode.ALL)
public abstract class TestBase {
    static final String TEST = "test";
}
