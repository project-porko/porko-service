package io.porko.widget.fixture;

import static io.porko.config.fixture.FixtureCommon.dtoType;
import static io.porko.widget.domain.OrderedMemberWidgets.ORDERED_WIDGET_COUNT;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import io.porko.config.base.TestBase;
import io.porko.widget.controller.model.ModifyMemberWidgetOrderDto;
import io.porko.widget.controller.model.ReorderWidgetRequest;
import java.util.List;

public class MemberWidgetFixture extends TestBase {
    public static final ArbitraryBuilder<ModifyMemberWidgetOrderDto> givenBuilder = dtoType()
        .giveMeBuilder(ModifyMemberWidgetOrderDto.class);

    public static List<ModifyMemberWidgetOrderDto> validReorderWidgets = validReorderTargetWidgets();
    public static ReorderWidgetRequest validReorderWidgetRequest = new ReorderWidgetRequest(validReorderWidgets);

    private static List<ModifyMemberWidgetOrderDto> validReorderTargetWidgets() {
        return dtoType()
            .giveMeBuilder(ModifyMemberWidgetOrderDto.class)
            .setLazy("widgetId", TestBase::nextLong)
            .setLazy("sequence", TestBase::nextInt)
            .sampleList(ORDERED_WIDGET_COUNT);
    }
}
