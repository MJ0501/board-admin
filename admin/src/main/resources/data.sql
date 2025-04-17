-- 테스트 계정
-- TODO: 테스트용이지만 비밀번호가 노출된 데이터 세팅. 개선하는 것이 좋을 지 고민해 보자.
insert into user_account (user_id, user_password, role_types, nickname, email, memo, created_at, created_by, modified_at, modified_by) values
  ('MJ', '{noop}m0501', 'ADMIN', 'MJ', 'MJ@mail.com', 'I am MJ.', now(), 'MJ', now(), 'MJ'),
  ('wook', '{noop}asdf1234', 'MANAGER', 'wook', 'wook@mail.com', 'I am wooki.', now(), 'MJ', now(), 'MJ'),
  ('susan', '{noop}asdf1234', 'MANAGER,DEVELOPER', 'Susan', 'Susan@mail.com', 'I am Susan.', now(), 'MJ', now(), 'MJ'),
  ('jim', '{noop}asdf1234', 'USER', 'Jim', 'jim@mail.com', 'I am Jim.', now(), 'MJ', now(), 'MJ')
;
