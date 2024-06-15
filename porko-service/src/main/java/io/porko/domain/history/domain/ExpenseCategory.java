package io.porko.domain.history.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ExpenseCategory {
    FOOD(1, "식비"),
    CAFE_SNACKS(1, "카페/간식"),
    LIVING_SUBSCRIPTION(2, "생활"),
    ONLINE_SHOPPING(2, "온라인쇼핑"),
    BEAUTY(3, "뷰티/미용"),
    FASHION_SHOPPING(3, "패션/쇼핑"),
    TRANSPORTATION(4, "교통"),
    CAR(4, "자동차"),
    CULTURE_LEISURE(5, "문화/여가"),
    ALCOHOL_ENTERTAINMENT(6, "술/유흥"),
    TRAVEL_ACCOMMODATION(7, "여행/숙박"),
    EDUCATION_LEARNING(8, "교육/학습"),
    EVENTS_GIFTS(9, "경조/선물"),
    MEDICAL_HEALTH(10, "의료/건강"),
    HOUSING_COMMUNICATION(11, "주거/통신"),
    FINANCE(12, "금융"),
    PETS(13, "반려 동물"),
    UNCATEGORIZED(14, "미분류"),
    TRANSFER_CARD_PAYMENT(15, "이체/카드 대금");

    private final int imageNo;
    private final String description;

    ExpenseCategory(int imageNo, String description) {
        this.imageNo = imageNo;
        this.description = description;
    }

    public static ExpenseCategory valueOfCategoryDetail(String targetCategory) {
        return Arrays.stream(values())
            .filter(category -> category.description.equals(targetCategory))
            .findFirst()
            .orElse(ExpenseCategory.UNCATEGORIZED)
            ;
    }
}
