package io.porko.config.base.persistence;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import io.porko.config.base.TestBase;
import io.porko.config.jpa.TestJpaConfiguration;
import io.porko.config.p6spy.P6spySqlFormatConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Import;

@Import({
    TestJpaConfiguration.class,
    P6spySqlFormatConfiguration.class
})
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
public abstract class DatasourceTestConfig extends TestBase {
}
