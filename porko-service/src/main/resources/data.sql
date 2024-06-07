insert into member(detail, road_name, created_at, email, gender, name, password, phone_number, updated_at, profile_image_url)
values ('반포동', '서울시 서초구', '2024-05-21T03:29:30.044', 'project.log.062@gmail.com', 'MALE', '최용석',
        '{bcrypt}$2a$10$8VSoF7WnqGiFDp0XPA93IuKtMLR17Tte6ROVBS8ORVX8nQnJjbJYS', '01011112222', null, '/images/default-profile/profile_2.jpg');

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

-- member_widget
insert into member_widget (widget_id, member_id, sequence, created_at, created_by, updated_at, updated_by) values
    (1, 1,  1, '2024-06-07T19:43:25.357', 1, null, null),
    (2, 1,  2, '2024-06-07T19:43:25.368', 1, null, null),
    (3, 1,  3, '2024-06-07T19:43:25.371', 1, null, null),
    (4, 1,  4, '2024-06-07T19:43:25.376', 1, null, null),
    (5, 1,  5, '2024-06-07T19:43:25.379', 1, null, null),
    (6, 1,  6, '2024-06-07T19:43:25.385', 1, null, null),
    (7, 1, -1, '2024-06-07T19:43:25.390', 1, null, null),
    (8, 1, -1, '2024-06-07T19:43:25.394', 1, null, null);

-- history
insert into history (cost, is_regret, place, pay_type, used_at, image_url_type_no, type, name, memo, member_id)
values (-45000, true, '올리브영', '신용카드', '2024-04-28T16:46:30.033', 1, '지출', '화장품', '아이브로우', 1),
       (-5000, true, '바나프레소', '신용카드', '2024-04-28T12:50:30.033', 2, '지출', '커피', '녹차프라페', 1),
       (-100000, true, '용용선생', '신용카드', '2024-05-28T02:00:30.033', 3, '지출', '술자리', '과음', 1),
       (50000, false, '용용선생', '계좌이체', '2024-05-28T10:00:30.033', 4, '지출', '친구', '친구A', 1),
       (-5000, true, '빽다방', '신용카드', '2024-05-29T08:30:33.012', 2, '지출', '커피', '모닝커피', 1),
       (-10000, true, '백암순대', '신용카드', '2024-05-29T12:50:50.033', 1, '지출', '점심', '점심', 1),
       (1700000, false, '월급', '계좌이체', '2024-06-01T09:00:30.033', 5, '수입', '급여', '급여', 1),
       (-15000, true, '영화관', '신용카드', '2024-06-02T14:30:30.033', 6, '지출', '영화', '영화 티켓', 1),
       (400000, false, '알바', '계좌이체', '2024-06-03T17:00:30.033', 5, '수입', '알바', '알바비', 1),
       (-8000, true, 'GS25', '신용카드', '2024-06-04T19:45:30.033', 1, '지출', '편의점', '편의점', 1),
       (600000, false, '투자수익', '계좌이체', '2024-06-05T13:00:30.033', 5, '수입', '투자', '투자 이익', 1),
       (-25000, true, '서점', '신용카드', '2024-06-06T16:10:30.033', 6, '지출', '책', '책 구매', 1),
       (35000, false, '디자인 작업', '계좌이체', '2024-06-07T15:20:30.033', 5, '수입', '프리랜서', '프리랜서 작업', 1),
       (-45000, true, '마트', '신용카드', '2024-06-08T11:00:30.033', 1, '지출', '마트', '식료품', 1),
       (-5000, true, '편의점', '신용카드', '2024-06-09T22:00:30.033', 1, '지출', '편의점', '야식', 1);

---
-- budget
insert into budget (member_id, goal_cost, goal_year, goal_month)
values (1, 4500000, 2024, 4),
       (1, 5000000, 2024, 5),
       (1, 5000000, 2024, 6);