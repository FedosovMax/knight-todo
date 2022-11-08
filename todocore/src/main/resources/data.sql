CREATE TABLE armor
(
    id         VARCHAR(255) NOT NULL,
    armor_type VARCHAR(255),
    defence    INTEGER,
    CONSTRAINT pk_armor PRIMARY KEY (id)
);

CREATE TABLE bonus_item
(
    bonus_id VARCHAR(255) NOT NULL,
    item_id  VARCHAR(255) NOT NULL
);

ALTER TABLE armor
    ADD CONSTRAINT FK_ARMOR_ON_ID FOREIGN KEY (id) REFERENCES item (id);

ALTER TABLE bonus_item
    ADD CONSTRAINT fk_bonite_on_bonus FOREIGN KEY (bonus_id) REFERENCES bonus (id);

ALTER TABLE bonus_item
    ADD CONSTRAINT fk_bonite_on_weapon FOREIGN KEY (item_id) REFERENCES weapon (id);
CREATE TABLE bonus
(
    id                VARCHAR(255) NOT NULL,
    name              VARCHAR(255),
    rarity            VARCHAR(255),
    damage_boost      INTEGER,
    crit_chance_boost INTEGER,
    crit_damage_boost INTEGER,
    skill_boost       INTEGER,
    CONSTRAINT pk_bonus PRIMARY KEY (id)
);
CREATE TABLE character
(
    id         VARCHAR(255) NOT NULL,
    name       VARCHAR(255),
    experience BIGINT       NOT NULL,
    user_id    VARCHAR(255),
    CONSTRAINT pk_character PRIMARY KEY (id)
);
CREATE TABLE day
(
    id       UUID    NOT NULL,
    day_name VARCHAR(255),
    removed  BOOLEAN NOT NULL,
    CONSTRAINT pk_day PRIMARY KEY (id)
);
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
CREATE TABLE bonus_item
(
    bonus_id VARCHAR(255) NOT NULL,
    item_id  VARCHAR(255) NOT NULL
);

CREATE TABLE item
(
    id                    VARCHAR(255) NOT NULL,
    name                  VARCHAR(255),
    description           VARCHAR(255),
    required_level        INTEGER,
    required_strength     INTEGER,
    required_agility      INTEGER,
    required_intelligence INTEGER,
    rarity                VARCHAR(255),
    CONSTRAINT pk_item PRIMARY KEY (id)
);

ALTER TABLE bonus_item
    ADD CONSTRAINT fk_bonite_on_bonus FOREIGN KEY (bonus_id) REFERENCES bonus (id);

ALTER TABLE bonus_item
    ADD CONSTRAINT fk_bonite_on_weapon FOREIGN KEY (item_id) REFERENCES weapon (id);
CREATE TABLE todo_role
(
    id   UUID NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_todo_role PRIMARY KEY (id)
);

ALTER TABLE todo_role
    ADD CONSTRAINT uc_todo_role_name UNIQUE (name);
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
CREATE TABLE bonus_skill
(
    bonus_id VARCHAR(255) NOT NULL,
    skill_id VARCHAR(255) NOT NULL
);

CREATE TABLE skill
(
    id          VARCHAR(255) NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_skill PRIMARY KEY (id)
);

ALTER TABLE bonus_skill
    ADD CONSTRAINT fk_bonski_on_bonus FOREIGN KEY (bonus_id) REFERENCES bonus (id);

ALTER TABLE bonus_skill
    ADD CONSTRAINT fk_bonski_on_skill FOREIGN KEY (skill_id) REFERENCES skill (id);
CREATE TABLE todo_user
(
    id       UUID NOT NULL,
    login    VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT pk_todo_user PRIMARY KEY (id)
);

CREATE TABLE todo_user_todo_roles
(
    todo_role_id UUID NOT NULL,
    todo_user_id UUID NOT NULL
);

ALTER TABLE todo_user_todo_roles
    ADD CONSTRAINT fk_todusetodrol_on_role FOREIGN KEY (todo_role_id) REFERENCES todo_role (id);

ALTER TABLE todo_user_todo_roles
    ADD CONSTRAINT fk_todusetodrol_on_user FOREIGN KEY (todo_user_id) REFERENCES todo_user (id);
CREATE TABLE bonus_item
(
    bonus_id VARCHAR(255) NOT NULL,
    item_id  VARCHAR(255) NOT NULL
);

CREATE TABLE weapon
(
    id          VARCHAR(255) NOT NULL,
    weapon_type VARCHAR(255),
    damage      INTEGER,
    CONSTRAINT pk_weapon PRIMARY KEY (id)
);

ALTER TABLE weapon
    ADD CONSTRAINT FK_WEAPON_ON_ID FOREIGN KEY (id) REFERENCES item (id);

ALTER TABLE bonus_item
    ADD CONSTRAINT fk_bonite_on_bonus FOREIGN KEY (bonus_id) REFERENCES bonus (id);

ALTER TABLE bonus_item
    ADD CONSTRAINT fk_bonite_on_weapon FOREIGN KEY (item_id) REFERENCES weapon (id);