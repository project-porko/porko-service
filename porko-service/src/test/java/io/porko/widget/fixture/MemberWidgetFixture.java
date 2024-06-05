package io.porko.widget.fixture;

import static io.porko.config.fixture.FixtureCommon.dtoType;
import static io.porko.widget.domain.OrderedMemberWidgets.ORDERED_WIDGET_COUNT;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import io.porko.config.base.TestBase;
import io.porko.widget.controller.model.ModifyMemberWidgetOrderDto;
import io.porko.widget.controller.model.ReorderWidgetRequest;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MemberWidgetFixture extends TestBase {
    public static final ArbitraryBuilder<ModifyMemberWidgetOrderDto> givenBuilder = dtoType()
        .giveMeBuilder(ModifyMemberWidgetOrderDto.class);

    public static List<ModifyMemberWidgetOrderDto> validReorderWidgets = validReorderTargetWidgets();
    public static ReorderWidgetRequest valiedReorderWidgetRequest = new ReorderWidgetRequest(validReorderWidgets);

    private static List<ModifyMemberWidgetOrderDto> validReorderTargetWidgets() {
        AtomicReference<Long> longAtomicReference = new AtomicReference<>(2L);

        return dtoType()
            .giveMeBuilder(ModifyMemberWidgetOrderDto.class)
            .setLazy("widgetId", () -> nextLong(longAtomicReference))
            .setLazy("sequence", TestBase::nextInt)
            .sampleList(ORDERED_WIDGET_COUNT);
    }
}
