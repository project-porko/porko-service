insert into member(detail, road_name, created_at, email, gender, name, password, phone_number, updated_at, profile_image_url)
values ('반포동', '서울시 서초구', '2024-05-21T03:29:30.044', 'project.log.062@gmail.com', 'MALE', '최용석',
        '{bcrypt}$2a$10$8VSoF7WnqGiFDp0XPA93IuKtMLR17Tte6ROVBS8ORVX8nQnJjbJYS', '01011112222', null, '/images/default-profile/profile_2.jpg');

---
-- widget
insert into widget (widget_code) values
       ('REMAINING_BUDGET'),
       ('UPCOMING_EXPENSES'),
       ('LAST_MONTH_EXPENSES'),
       ('CURRENT_MONTH_EXPENSES'),
       ('CURRENT_MONTH_CARD_USAGE'),
       ('MY_CHALLENGE'),
       ('CREDIT_SCORE'),
       ('DAILY_EXPENSES');

---
-- history
insert into history (cost, regret, place, pay_type, used_at, category_id, memo, member_id)
values (-10000, true, '올리브영', '신용카드', '2024-04-28T16:46:30.033', 1, '아이브로우', 1),
       (-5000, true, '바나프레소', '신용카드', '2024-04-28T12:50:30.033', 1, '녹차프라페', 1),
       (-100000, true, '용용선생', '신용카드', '2024-05-28T02:00:30.033', 1, '과음', 1),
       (50000, false, '용용선생', '계좌이체', '2024-05-28T10:00:30.033', 1, '친구A', 1),
       (-5000, true, '빽다방', '신용카드', '2024-05-29T08:30:33.012', 1, '모닝커피', 1),
       (-10000, true, '백암순대', '신용카드', '2024-05-29T12:50:50.033', 1, '점심', 1),
       (1700000, false, '월급', '계좌이체', '2024-06-01T09:00:30.033', 1, '급여', 1),
       (-15000, true, '영화관', '신용카드', '2024-06-02T14:30:30.033', 1, '영화 티켓', 1),
       (400000, false, '알바', '계좌이체', '2024-06-03T17:00:30.033', 1, '알바비', 1),
       (-8000, true, 'GS25', '신용카드', '2024-06-04T19:45:30.033', 1, '편의점', 1),
       (600000, false, '투자수익', '계좌이체', '2024-06-05T13:00:30.033', 1, '투자 이익', 1),
       (-25000, true, '서점', '신용카드', '2024-06-06T16:10:30.033', 1, '책 구매', 1),
       (35000, false, '디자인 작업', '계좌이체', '2024-06-07T15:20:30.033', 1, '프리랜서 작업', 1),
       (-45000, true, '마트', '신용카드', '2024-06-08T11:00:30.033', 1, '식료품', 1),
       (-5000, true, '편의점', '신용카드', '2024-06-09T22:00:30.033', 1, '야식', 1);
