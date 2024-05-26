insert into member(detail, road_name, created_at, email, gender, name, password, phone_number, updated_at) values
    ('반포동','서울시 서초구','2024-05-21T03:29:30.044','project.log.062@gmail.com','MALE','최용석','{bcrypt}$2a$10$8VSoF7WnqGiFDp0XPA93IuKtMLR17Tte6ROVBS8ORVX8nQnJjbJYS','01011112222',NULL);

---
-- widget
insert into widget (widget_code) values
    ('TODAY_CONSUMPTION_WEATHER'),
    ('REMAINING_BUDGET'),
    ('UPCOMING_EXPENSES'),
    ('LAST_MONTH_EXPENSES'),
    ('CURRENT_MONTH_EXPENSES'),
    ('CURRENT_MONTH_CARD_USAGE'),
    ('MY_CHALLENGE'),
    ('DAILY_EXPENSES'),
    ('CREDIT_SCORE'),
    ('INVESTMENT_RANKING'),
    ('STEP_COUNT'),
    ('DAILY_HOROSCOPE');
