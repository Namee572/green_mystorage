INSERT INTO member (`birth`, `create_date`, `modified_date`, `name`, `email`, `nick_name`, `password`)
VALUES ('970819', '2024-03-28 17:59:57.000000', '2024-03-28 17:59:59.000000', '송하린', 'ssmm1003@naver.com', 'Harin',
        'Harin1997!');

INSERT INTO member (`birth`, `create_date`, `modified_date`, `name`, `email`, `nick_name`, `password`)
VALUES ('930425', '2024-03-28 17:59:57.000000', '2024-03-28 17:59:59.000000', '강현욱', 'aaa@naver.com', '가',
        'qwer1234!');

INSERT INTO member (`birth`, `create_date`, `modified_date`, `name`, `email`, `nick_name`, `password`)
VALUES ('991109', '2024-03-28 17:59:57.000000', '2024-03-28 17:59:59.000000', '박준재', 'bbb@naver.com', '나',
        'qwer1234!');

INSERT INTO member (`birth`, `create_date`, `modified_date`, `name`, `email`, `nick_name`, `password`)
VALUES ('961203', '2024-03-28 17:59:57.000000', '2024-03-28 17:59:59.000000', '최은재', 'ccc@naver.com', '다',
        'qwer1234!');

INSERT INTO member (`birth`, `create_date`, `modified_date`, `name`, `email`, `nick_name`, `password`)
VALUES ('980218', '2024-03-28 17:59:57.000000', '2024-03-28 17:59:59.000000', '신지훈', 'ddd@naver.com', '라',
        'qwer1234!');

INSERT INTO member (`birth`, `create_date`, `modified_date`, `name`, `email`, `nick_name`, `password`)
VALUES ('980510', '2024-03-28 17:59:57.000000', '2024-03-28 17:59:59.000000', '정은희', 'eee@naver.com', '마',
        'qwer1234!');

INSERT INTO foodinfo (tip, foodname)
VALUES ('우유는 냉장고의 가장 추운 부분에 보관하세요. 보관 온도는 4°C(39°F) 정도가 적당합니다.', '우유');

INSERT INTO foodinfo (tip, foodname)
VALUES ('당근을 냉장고에서 보관할 때 비닐 봉지에 넣어서 공기와 접촉을 최소화하세요.', '당근');

INSERT INTO foodinfo (tip, foodname)
VALUES ('토마토는 직사광선이 닿지 않는 어두운 곳에 보관하세요.', '토마토');

INSERT INTO foodinfo (tip, foodname)
VALUES ('햄은 개봉한 후에는 냉장고에 보관하세요', '햄');


INSERT INTO foodinfo (tip, foodname)
VALUES ('닭고기를 구매한 후에는 가능한 한 빨리 소비하거나 냉장고나 냉동고에 보관하세요.', '닭고기');

INSERT INTO foodinfo (tip, foodname)
VALUES ('돼지고기를 구매한 후에는 가능한 한 빨리 소비하거나 냉장고나 냉동고에 보관하세요.', '돼지고기');

INSERT INTO foodinfo (tip, foodname)
VALUES ('해산물은 신선도가 중요하므로 냉동하기 전에 신선한 상태를 유지하는 것이 중요합니다. 신선도를 유지하기 위해 냉동 전에 잘 씻어서 깨끗하게 해주고, 필요하다면 효과적인 냉동용 포장재를 사용합니다.', '오징어');

INSERT INTO foodinfo (tip, foodname)
VALUES ('빵을 오랫동안 보관해야 할 경우에는 냉동실에 보관하는 것이 좋습니다. 냉동실은 공기 중의 수분을 걷어내는 역할을 하기 때문에 빵의 신선도를 오랫동안 유지할 수 있습니다.', '빵');

insert into foodstorage (quantity, regdate, expdate, storage_type, member_id, foodinfo_id)
VALUES (1, now(), '2024-04-01', 'Cold', 1, 1);

insert into foodstorage (quantity, regdate, expdate, storage_type, member_id, foodinfo_id)
VALUES (1, now(), '2024-04-27', 'Forzen', 2, 5);

insert into foodstorage (quantity, regdate, expdate, storage_type, member_id, foodinfo_id)
VALUES (1, now(), '2024-04-02', 'Cold', 3, 2);

insert into foodstorage (quantity, regdate, expdate, storage_type, member_id, foodinfo_id)
VALUES (1, now(), '2024-04-20', 'Forzen', 4, 6);

insert into foodstorage (quantity, regdate, expdate, storage_type, member_id, foodinfo_id)
VALUES (1, now(), '2024-04-03', 'Cold', 5, 3);

insert into foodstorage (quantity, regdate, expdate, storage_type, member_id, foodinfo_id)
VALUES (1, now(), '2024-04-30', 'Forzen', 6, 7);

INSERT INTO recipeboard (`view_count`, `create_date`, `modified_date`, `user_id`, `content`, `title`)
VALUES (0, '2024-03-28 17:59:57', '2024-03-29 15:59:57', 2,
        '\r\n[재료]\r\n\r\n돼지고기(목살) 1컵(130g)\r\n신김치 3컵(390g)\r\n물 3컵(540ml)\r\n청양고추 2개(20g)\r\n대파 약 2/3대(70g)\r\n간 마늘 1큰술(20g)\r\n굵은 고춧가루 1큰술(8g)\r\n고운 고춧가루 ½큰술(3g)\r\n국간장 1큰술(10g)\r\n새우젓 1큰술(20g)\r\n\r\n[만드는 법]\r\n\r\n1. 돼지고기는 1.5cm 정도 두께로 먹기 좋게 자른다. \r\n2. 냄비에 돼지고기, 정수물, 새우젓을 넣고 강 불로 끓인다. \r\n3. 찌개가 끓어오르면 중 약불로 줄인 후 고기가 무를 때 까지 최소 10분 정도 끓인다.\r\n4. 대파는 두께 0.5cm로 송송썬다.\r\n5. 청양고추는 두께 0.5cm로 어슷썬다.\r\n6. 10분 후 물이 졸아들면 졸아든 만큼의 물을 보충한 후 신김치, 국간장, 굵은 고춧가루, 고운 고춧가루, 간 마늘을 넣는다. \r\n7. 찌개가 팔팔 끓으면 대파, 청양고추를 넣는다.  \r\n8. 완성 직전에 고춧가루를 뿌려 완성한다.',
        '돼지고기김치찌개');

INSERT INTO recipeboard (`view_count`, `create_date`, `modified_date`, `user_id`, `content`, `title`)
VALUES (0, '2024-01-20 17:59:57', '2024-03-28 17:59:57', 1,
        '\r\n[재료]\r\n\r\n삶은 고구마 2개(개당 약200g)\r\n스틱버터 1/2개(40g)\r\n물엿 약1과1/2큰술(24g)\r\n시나몬파우더 약간\r\n\r\n[ 만드는 법 ]\r\n\r\n1. 전자레인지용 그릇에 버터, 물엿을 넣고 1000W전자레인지 기준 약10초간 돌린다.\r\n   *버터의 상태와 전자레인지 세기에 따라 돌리는 시간을 조절한다.\r\n   *물엿이나 꿀이 없다면 설탕1큰술,물1/2큰술을 섞은 후 전자레인지에 약 10초 돌려 시럽으로 만들어 사용한다.\r\n2. 부드러워진 버터는 숟가락을 이용해 물엿과 잘 섞는다.\r\n   *허니버터가 너무 묽으면 시원한 곳에서 굳힌다.\r\n3. 삶은 고구마를  2/3 깊이까지 가른다.\r\n   *고구마가 차가울 경우 전자레인지에 돌려 따뜻하게 사용한다.\r\n   *삶은고구마 대신 군고구마, 찐고구마를 사용해도 좋다.\r\n4. 고구마 속을 숟가락으로 푼 후 살짝 으깨어 가운데 갈라진 곳을 채워 넣고 버터 올릴 공간을 만든다.\r\n5. 고구마 중앙에 허니버터를 올린 후 시나몬파우더를 뿌려 완성한다.\r\n   *시나몬파우더를 허니버터 만들 때 섞어서 사용해도 좋다.\r\n   *시나몬파우더가 없다면 계피가루를 사용해도 좋다.',
        '허니버터고구마');

INSERT INTO recipeboard (`view_count`, `create_date`, `modified_date`, `user_id`, `content`, `title`)
VALUES (0, '2024-04-03 08:54:57', '2024-04-03 12:54:57', 2,
        '\r\n[재료]\r\n\r\n배추전\r\n - 배추\r\n - 부침가루\r\n - 정수물\r\n - 식용유\r\n\r\n양념장\r\n - 청양고추\r\n - 대파\r\n - 진간장\r\n - 식초\r\n\r\n[ 만드는 법 ]\r\n\r\n  배추전\r\n  1. 배추는 깨끗하게 세척한 후 밑동으로 부터 1cm 정도 위쪽을 잘라 배춧잎이 낱장으로 떨어지게 한다.\r\n  2. 배추의 줄기 부분을 칼 옆면 또는 단단한 도구를 이용하여 두드려 편다.\r\n  3. 배추는 그릇에 담은 후 물기가 있는 상태 그대로 부침가루를 뿌려 골고루 묻힌다.\r\n    *부침가루가 골고루 입혀져야 반죽물을 입혀 부쳤을 때 반죽 옷이 떨어지지 않는다.\r\n  4. 깊은 그릇에 부침가루와 물을 1:1 비율로 넣어 물반죽을 만든다.\r\n    *배추전은 반죽옷이 얇게 묻어야 맛있기 때문에 농도를 묽게 만드는게 중요하다.\r\n    *배추 1장당 부침가루는 약 4큰술 사용한다.\r\n  5. 프라이팬을 강 불에 올려 예열한다.\r\n  6. 예열이 된 프라이팬에 식용유를 넉넉히 두른다.\r\n  7. 부침가루 묻혀둔 배추에 물반죽을 골고루 입힌 후 배추를 들어 반죽물이 흘러 얇게 묻혀진 상태로 준비한다.\r\n  8. 예열된 프라이팬에 반죽물 입힌 배추를 올린 후 중 불로 줄여 앞뒤로 노릇하게 구워 완성한다.\r\n\r\n  양념장\r\n  1. 대파와 청양고추는 두께 0.3cm로 송송 썰어 종지에 담는다.\r\n  2. 간장과 식초를 1:2 비율로 넣고 양념장을 만들어 배추전에 곁들인다.',
        '3분 배추전');

INSERT INTO recipeboard (`view_count`, `create_date`, `modified_date`, `user_id`, `content`, `title`)
VALUES (0, '2023-07-10 10:54:57', '2023-07-11 08:54:57', 1,
        '[재료]\r\n\r\n떡볶이\r\n\r\n쌀떡 4컵(400g)\r\n밀가루떡 4컵(400g)\r\n사각어묵 4장(160g)\r\n양배추 2컵(160g)\r\n대파 3컵(240g)\r\n물 1L\r\n삶은달걀 3개\r\n\r\n양념장\r\n\r\n고추장 1/3컵(80g)\r\n진간장 1/3컵(50g)\r\n고운고춧가루 1/3컵(30g)\r\n굵은고춧가루 1/3컵(20g)\r\n황설탕 1/3컵(70g)\r\nMSG 1/2큰술(5g)\r\n\r\n\r\n[만드는 법]\r\n\r\n1. 대파는 어슷 썰거나 반으로 갈라 길게 썰어 준비한다.\r\n2. 양배추, 어묵은 먹기 좋은 크기로 썰어 준비한다.\r\n3. 냄비에 물, 진간장, 황설탕, 고추장, 굵은고춧가루, 고운고춧가루, 대파, 양배추를 넣어 끓인다.\r\n4. 떡볶이떡은 흐르는 물에 가볍게 세척한다.\r\n5. 육수가 끓으면 삶은달걀, 떡을 넣고 함께 끓여준다.\r\n6. 기호에 맞게 MSG를 넣는다.\r\n7. 떡을 넣고 육수가 끓어오르면 어묵을 넣어준다.\r\n8. 양념장이 걸쭉하게 졸아들 때까지 끓여 완성한다.',
        '분식집 떡볶이');

