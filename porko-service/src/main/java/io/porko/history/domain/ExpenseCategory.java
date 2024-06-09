package io.porko.history.domain;

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

    private final int code;
    private final String description;

    ExpenseCategory(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ExpenseCategory fromCode(String targetCategory) {
        for (ExpenseCategory category : ExpenseCategory.values()) {
            if (category.description.equals(targetCategory)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + targetCategory);
    }
}
