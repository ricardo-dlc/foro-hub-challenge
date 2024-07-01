ALTER TABLE topics ADD deleted TINYINT;

UPDATE topics SET deleted = 0;

ALTER TABLE courses ADD deleted TINYINT;

UPDATE courses SET deleted = 0;

ALTER TABLE categories ADD deleted TINYINT;

UPDATE categories SET deleted = 0;

ALTER TABLE users ADD deleted TINYINT;

UPDATE users SET deleted = 0;