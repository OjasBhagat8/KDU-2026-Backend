-- librarian user
INSERT IGNORE INTO users (id, username, password, user_role, enabled, created_at)
VALUES (
  UUID_TO_BIN(UUID()),
  'librarian',
  '$2a$12$uSunzW9qNxQwCaaAJH/rZOB1V/xTLVIS5WRx1wYJAZpdKXhIdbClK',
  'LIBRARIAN',
  true,
  NOW()
);

-- member user
INSERT IGNORE INTO users (id, username, password, user_role, enabled, created_at)
VALUES (
  UUID_TO_BIN(UUID()),
  'member',
  '$2a$12$uSunzW9qNxQwCaaAJH/rZOB1V/xTLVIS5WRx1wYJAZpdKXhIdbClK',
  'MEMBER',
  true,
  NOW()
);
