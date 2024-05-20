package io.porko.config.base.persistence;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import io.porko.config.base.TestBase;
import io.porko.config.jpa.TestJpaConfig;
import io.porko.config.p6spy.P6spySqlFormatConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Import;

@Import({
    TestJpaConfig.class,
    P6spySqlFormatConfig.class
})
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
public abstract class DatasourceTestConfig extends TestBase {
}
