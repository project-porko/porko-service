package io.porko.widget.domain;

import static io.porko.widget.domain.WidgetType.DEFAULT;
import static io.porko.widget.domain.WidgetType.OPTIONAL;

import lombok.Getter;

@Getter
public enum WidgetCode {
    REMAINING_BUDGET(DEFAULT, "이번달 남은 예산", 1),
    UPCOMING_EXPENSES(DEFAULT, "다가오는 지출", 2),
    LAST_MONTH_EXPENSES(DEFAULT, "저번 달에 쓴 돈", 3),
    CURRENT_MONTH_EXPENSES(DEFAULT, "이번달 현재 소비", 4),
    CURRENT_MONTH_CARD_USAGE(DEFAULT, "이번달 카드 실적", 5),
    MY_CHALLENGE(DEFAULT, "나의 챌린지", 6),

    CREDIT_SCORE(OPTIONAL, "나의 신용 점수", -1),
    MONTHLY_EXPENSES(OPTIONAL, "매월 나가는 돈", -1);

    private final WidgetType type;
    private final String description;
    private final int initialSequence;

    WidgetCode(WidgetType type, String description, int initialSequence) {
        this.type = type;
        this.description = description;
        this.initialSequence = initialSequence;
    }

    public boolean isDefault() {
        return type == DEFAULT;
    }
}
