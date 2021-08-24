select * from board order by num desc;

-- 이미지 파일명 저장용 필드 추가
alter table board add image varchar2(30);