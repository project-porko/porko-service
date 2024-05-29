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

---
-- history
insert into history (cost, regret, place, pay_type, used_at, category_id, type, memo, member_id) values
     ('10000',true,'올리브영','신용카드','2024-04-28T16:46:30.033',1,'지출','아이브로우',1),
     ('5000',true,'바나프레소','신용카드','2024-04-28T12:50:30.033',1,'지출','녹차프라페',1),
     ('100000',true,'용용선생','신용카드','2024-05-28T02:00:30.033',1,'지출','과음',1),
     ('50000',false,'용용선생','계좌이체','2024-05-28T10:00:30.033',1,'수입','친구A',1),
     ('5000',true,'빽다방','신용카드','2024-05-29T08:30:33.012',1,'지출','모닝커피',1),
     ('10000',true,'백암순대','신용카드','2024-05-29T12:50:50.033',1,'지출','점심',1);
