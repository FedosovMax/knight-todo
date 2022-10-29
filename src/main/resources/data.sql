-- insert into day (day_name) values ('firstDay');
--
-- insert into day (day_name) values ('secondDay');
CREATE TABLE day_todo
(
    id            UUID    NOT NULL,
    day_todo_name VARCHAR(255),
    scaryness     VARCHAR(255),
    hardness      VARCHAR(255),
    ready         BOOLEAN NOT NULL,
    removed       BOOLEAN NOT NULL,
    day_id        UUID,
    CONSTRAINT pk_day_todo PRIMARY KEY (id)
);

ALTER TABLE day_todo
    ADD CONSTRAINT FK_DAY_TODO_ON_DAY FOREIGN KEY (day_id) REFERENCES day (id);
CREATE TABLE day
(
    id       UUID    NOT NULL,
    day_name VARCHAR(255),
    removed  BOOLEAN NOT NULL,
    CONSTRAINT pk_day PRIMARY KEY (id)
);
CREATE TABLE routine
(
    id        UUID    NOT NULL,
    name      VARCHAR(255),
    scaryness VARCHAR(255),
    hardness  VARCHAR(255),
    removed   BOOLEAN NOT NULL,
    CONSTRAINT pk_routine PRIMARY KEY (id)
);
CREATE TABLE routine_instance
(
    id         UUID    NOT NULL,
    name       VARCHAR(255),
    scariness  VARCHAR(255),
    hardness   VARCHAR(255),
    ready      BOOLEAN NOT NULL,
    removed    BOOLEAN NOT NULL,
    routine_id UUID,
    CONSTRAINT pk_routine_instance PRIMARY KEY (id)
);

ALTER TABLE routine_instance
    ADD CONSTRAINT FK_ROUTINE_INSTANCE_ON_ROUTINE FOREIGN KEY (routine_id) REFERENCES routine (id);
CREATE TABLE routine_todo
(
    id                UUID    NOT NULL,
    routine_todo_name VARCHAR(255),
    scaryness         VARCHAR(255),
    hardness          VARCHAR(255),
    ready             BOOLEAN NOT NULL,
    removed           BOOLEAN NOT NULL,
    routine_id        UUID,
    CONSTRAINT pk_routine_todo PRIMARY KEY (id)
);

ALTER TABLE routine_todo
    ADD CONSTRAINT FK_ROUTINE_TODO_ON_ROUTINE FOREIGN KEY (routine_id) REFERENCES routine (id);
CREATE TABLE routine_todo_instance
(
    id                         UUID    NOT NULL,
    routine_todo_instance_name VARCHAR(255),
    scaryness                  VARCHAR(255),
    hardness                   VARCHAR(255),
    ready                      BOOLEAN NOT NULL,
    removed                    BOOLEAN NOT NULL,
    routine_instance_id        UUID,
    routine_todo_id            UUID,
    CONSTRAINT pk_routine_todo_instance PRIMARY KEY (id)
);

ALTER TABLE routine_todo_instance
    ADD CONSTRAINT FK_ROUTINE_TODO_INSTANCE_ON_ROUTINEINSTANCE FOREIGN KEY (routine_instance_id) REFERENCES routine_instance (id);

ALTER TABLE routine_todo_instance
    ADD CONSTRAINT FK_ROUTINE_TODO_INSTANCE_ON_ROUTINETODO FOREIGN KEY (routine_todo_id) REFERENCES routine_todo (id);
