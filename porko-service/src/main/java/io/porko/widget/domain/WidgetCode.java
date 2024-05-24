package io.porko.widget.domain;

import static io.porko.widget.domain.WidgetType.DEFAULT;
import static io.porko.widget.domain.WidgetType.FIXED;
import static io.porko.widget.domain.WidgetType.OPTIONAL;

import lombok.Getter;

@Getter
public enum WidgetCode {
    TODAY_CONSUMPTION_WEATHER(FIXED, "오늘 소비 날씨"),

    REMAINING_BUDGET(DEFAULT, "이번달 남은 예산"),
    UPCOMING_EXPENSES(DEFAULT, "다가오는 지출"),
    LAST_MONTH_EXPENSES(DEFAULT, "저번 달에 쓴 돈"),
    CURRENT_MONTH_EXPENSES(DEFAULT, "이번달 현재 소비"),
    CURRENT_MONTH_CARD_USAGE(DEFAULT, "이번달 카드 실적"),
    MY_CHALLENGE(DEFAULT, "나의 챌린지"),

    DAILY_EXPENSES(OPTIONAL, "매일 나가는 돈"),
    CREDIT_SCORE(OPTIONAL, "나의 신용 점수"),
    INVESTMENT_RANKING(OPTIONAL, "투자 랭킹"),
    STEP_COUNT(OPTIONAL, "나의 걸음 수"),
    DAILY_HOROSCOPE(OPTIONAL, "오늘의 운세");

    private final WidgetType type;
    private final String description;

    WidgetCode(WidgetType type, String description) {
        this.type = type;
        this.description = description;
    }
}
