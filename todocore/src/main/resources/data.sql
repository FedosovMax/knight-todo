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