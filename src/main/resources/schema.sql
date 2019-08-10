DROP TABLE IF EXISTS doctype_fields;

DROP TABLE IF EXISTS doc_fields;

DROP TABLE IF EXISTS doc;

DROP TABLE IF EXISTS doctype;

DROP TABLE IF EXISTS field;

DROP TABLE IF EXISTS groupedfield;

DROP TABLE IF EXISTS varselectedvalue;

DROP TABLE IF EXISTS varelem;

DROP TABLE IF EXISTS var;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE doctype
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                 NOT NULL
);

CREATE TABLE doc
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    id_doctype       INTEGER                 NOT NULL,
    reg_num          VARCHAR                 NOT NULL,
    reg_date         TIMESTAMP               NOT NULL,
    insert_date      TIMESTAMP DEFAULT now() NOT NULL,
    FOREIGN KEY (id_doctype) REFERENCES doctype (id) ON DELETE CASCADE
);
/*
CREATE TABLE fieldtype
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             INTEGER                 NOT NULL
);
*/
CREATE TABLE field
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                 NOT NULL,
    id_fieldtype     INTEGER                 NOT NULL,
    id_var           INTEGER                         ,
    length           INTEGER
    --FOREIGN KEY (id_fieldtype) REFERENCES fieldtype (id) ON DELETE CASCADE
);

CREATE TABLE groupedfield
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR
);

CREATE TABLE doctype_fields
(
    id_doctype          INTEGER                 NOT NULL,
    id_field            INTEGER                 NOT NULL,
    position            INTEGER                 NOT NULL,
    id_groupedfield     INTEGER                         ,
    position_in_group   INTEGER                         ,
    max_count           INTEGER                 NOT NULL,
    role                VARCHAR                 NOT NULL,
    CONSTRAINT c_doctype_fields UNIQUE (id_doctype, id_field, position),
    FOREIGN KEY (id_doctype) REFERENCES doctype (id) ON DELETE CASCADE,
    FOREIGN KEY (id_field) REFERENCES field (id) ON DELETE CASCADE,
    FOREIGN KEY (id_groupedfield) REFERENCES groupedfield (id) ON DELETE CASCADE
);

CREATE TABLE var
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    id_parent_var    INTEGER                         ,
    name             VARCHAR                 NOT NULL,
    vartype          INTEGER                 NOT NULL
);

CREATE TABLE varelem
(
    id                  INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    value_int           INTEGER                         ,
    value_str           INTEGER                         ,
    id_var              INTEGER                 NOT NULL,
    id_parent_varelem   INTEGER                         ,
    FOREIGN KEY (id_var) REFERENCES var (id) ON DELETE CASCADE
);


CREATE TABLE doc_fields
(
    id_doc                 INTEGER                 NOT NULL,
    id_field               INTEGER                 NOT NULL,
    position_fact          INTEGER                 NOT NULL,
    id_groupedfield        INTEGER                         ,
    position_in_group_fact INTEGER                         ,
    id_varelem             INTEGER                         ,
    value_int              INTEGER                         ,
    value_str              VARCHAR                         ,
    value_date             TIMESTAMP                       ,
    CONSTRAINT c_doc_fields UNIQUE (id_doc, id_field, position_fact),
    FOREIGN KEY (id_doc) REFERENCES doc (id) ON DELETE CASCADE,
    FOREIGN KEY (id_field) REFERENCES field (id) ON DELETE CASCADE,
    FOREIGN KEY (id_groupedfield) REFERENCES groupedfield (id) ON DELETE CASCADE

);


