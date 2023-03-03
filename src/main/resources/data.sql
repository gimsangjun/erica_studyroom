-- 유저
INSERT INTO users(username, password, nickname, created_at, role, age, grade, email, university, department, is_enable, update_at)
VALUES ('test','$2a$10$dM/GwObkJcqYlGPAVMGEiuUYXq.aAh1XBeYr4QXOpuPBr1PiH4Sdm', '홍길동', '2023-03-03 22:11:52.583876', 'ROLE_USER', 25, 4, '홍길동@naver.com', '소프트웨어융합대학', '소프트웨어전공', true, '2023-03-03 22:11:52.583876');

-- 스터디룸
INSERT INTO study_room(study_room_id, name, university, department, location, capacity)
VALUES (1, '팀플실0', '소프트웨어융합대학', '소프트웨어학부', '3공학관', 10);

-- 스터디룸 태그
INSERT INTO study_room_descriptions(study_room_id, descriptions)
VALUES (1, '투명한 벽으로 구분된 팀플실');

INSERT INTO study_room_drinks(study_room_id, drinks)
VALUES (1, '커피'),
       (1, '물');

INSERT INTO study_room_cautions(study_room_id, cautions)
VALUES (1, '음식물 반입금지'),
       (1, '시끄럽게 떠드는 행위 금지');

INSERT INTO study_room_tags(study_room_id, tags)
VALUES (1, '태그1'),
       (1, '태그2');

