package io.porko.domain.history.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Domain:ExpenseCategory")
class ExpenseCategoryTest {
    @ParameterizedTest
    @MethodSource
    @DisplayName("금융 거래 기록의 소분류를 ExpenseCategory로 변환")
    void valueOf(final String given, final ExpenseCategory expected) {
        // When
        ExpenseCategory actual = ExpenseCategory.valueOfCategoryDetail(given);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> valueOf() {
        return Stream.of(
            Arguments.of("식비", ExpenseCategory.FOOD),
            Arguments.of("카페/간식", ExpenseCategory.CAFE_SNACKS),
            Arguments.of("생활", ExpenseCategory.LIVING_SUBSCRIPTION),
            Arguments.of("온라인쇼핑", ExpenseCategory.ONLINE_SHOPPING),
            Arguments.of("뷰티/미용", ExpenseCategory.BEAUTY),
            Arguments.of("패션/쇼핑", ExpenseCategory.FASHION_SHOPPING),
            Arguments.of("교통", ExpenseCategory.TRANSPORTATION),
            Arguments.of("자동차", ExpenseCategory.CAR),
            Arguments.of("문화/여가", ExpenseCategory.CULTURE_LEISURE),
            Arguments.of("술/유흥", ExpenseCategory.ALCOHOL_ENTERTAINMENT),
            Arguments.of("여행/숙박", ExpenseCategory.TRAVEL_ACCOMMODATION),
            Arguments.of("교육/학습", ExpenseCategory.EDUCATION_LEARNING),
            Arguments.of("경조/선물", ExpenseCategory.EVENTS_GIFTS),
            Arguments.of("의료/건강", ExpenseCategory.MEDICAL_HEALTH),
            Arguments.of("주거/통신", ExpenseCategory.HOUSING_COMMUNICATION),
            Arguments.of("금융", ExpenseCategory.FINANCE),
            Arguments.of("반려 동물", ExpenseCategory.PETS),
            Arguments.of("미분류", ExpenseCategory.UNCATEGORIZED),
            Arguments.of("이체/카드 대금", ExpenseCategory.TRANSFER_CARD_PAYMENT)
        );
    }
}
