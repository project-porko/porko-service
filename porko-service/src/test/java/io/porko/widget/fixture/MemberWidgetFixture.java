package io.porko.widget.fixture;

import static io.porko.config.base.TestBase.nextId;
import static io.porko.config.base.TestBase.nextIndex;
import static io.porko.config.fixture.FixtureCommon.dtoType;
import static io.porko.widget.controller.model.ModifyMemberWidgetsOrderRequest.ORDERED_WIDGET_COUNT;

import io.porko.widget.controller.model.ModifyMemberWidgetOrderDto;
import io.porko.widget.controller.model.ModifyMemberWidgetsOrderRequest;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MemberWidgetFixture {
    public static ModifyMemberWidgetsOrderRequest modifyModifyMemberWidgetsOrderRequest() {
        return new ModifyMemberWidgetsOrderRequest(targetWidgets());
    }

    public static List<ModifyMemberWidgetOrderDto> targetWidgets() {
        AtomicReference<Long> longAtomicReference = new AtomicReference<>(2L);

        return dtoType()
            .giveMeBuilder(ModifyMemberWidgetOrderDto.class)
            .setLazy("widgetId", () -> nextId(longAtomicReference))
            .setLazy("sequence", () -> nextIndex())
            .sampleList(ORDERED_WIDGET_COUNT);
    }
}
