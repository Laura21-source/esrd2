DROP TABLE IF EXISTS field_child_field;

DROP TABLE IF EXISTS doctype_fields;

DROP TABLE IF EXISTS field;

DROP TABLE IF EXISTS valuedfield_child_valuedfield;

DROP TABLE IF EXISTS doc_valuedfields;

DROP TABLE IF EXISTS doc;

DROP TABLE IF EXISTS doctype;

DROP TABLE IF EXISTS valuedfield;

DROP TABLE IF EXISTS catalogelem;

DROP TABLE IF EXISTS catalog;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;
CREATE SEQUENCE agreement_seq START 1;
CREATE SEQUENCE depr_seq START 1;

CREATE TABLE doctype
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                 NOT NULL
);

CREATE TABLE doc
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    doctype_id       INTEGER                 NOT NULL,
    reg_num          VARCHAR                 NOT NULL,
    reg_date         DATE DEFAULT now()      NOT NULL,
    insert_datetime  TIMESTAMP DEFAULT now() NOT NULL,
    FOREIGN KEY (doctype_id) REFERENCES doctype (id) ON DELETE CASCADE
);

CREATE TABLE catalog
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    parent_catalog_id    INTEGER                         ,
    name             VARCHAR                 NOT NULL,
    catalogtype_id       INTEGER                 NOT NULL
);

CREATE TABLE catalogelem
(
    id                  INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    value_int           INTEGER                         ,
    value_str           VARCHAR                         ,
    catalog_id              INTEGER                 NOT NULL,
    parent_catalogelem_id   INTEGER                         ,
    FOREIGN KEY (catalog_id) REFERENCES catalog (id) ON DELETE CASCADE
);

CREATE TABLE field
(
    id                INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name              VARCHAR                 NOT NULL,
    fieldtype         VARCHAR                 NOT NULL,
    position_in_group INTEGER                         ,
    max_count         INTEGER                         ,
    length            INTEGER                         ,
    catalog_id            INTEGER                         ,
    FOREIGN KEY (catalog_id) REFERENCES catalog (id) ON DELETE CASCADE
);

CREATE TABLE field_child_field
(
    field_id          INTEGER                         ,
    child_field_id    INTEGER                         ,
    FOREIGN KEY (field_id) REFERENCES field (id) ON DELETE CASCADE,
    FOREIGN KEY (child_field_id) REFERENCES field (id) ON DELETE CASCADE
);

CREATE TABLE doctype_fields
(
    id                INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    doctype_id          INTEGER                 NOT NULL,
    field_id            INTEGER                 NOT NULL,
    position            INTEGER                 NOT NULL,
    role                VARCHAR                 NOT NULL,
    CONSTRAINT c_doctype_fields UNIQUE (doctype_id, field_id, position, role),
    FOREIGN KEY (doctype_id) REFERENCES doctype (id) ON DELETE CASCADE,
    FOREIGN KEY (field_id) REFERENCES field (id) ON DELETE CASCADE
);

CREATE TABLE valuedfield
(
    id                INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    field_id          INTEGER                 NOT NULL,
    catalogelem_id    INTEGER                         ,
    value_int         INTEGER                         ,
    value_str         VARCHAR                         ,
    value_date        DATE                            ,
    value_time        TIME                            ,
    value_datetime    TIMESTAMP                       ,
    FOREIGN KEY (catalogelem_id) REFERENCES catalogelem (id) ON DELETE CASCADE
);

CREATE TABLE valuedfield_child_valued_field
(
    valued_field_id          INTEGER                         ,
    child_valued_field_id    INTEGER                         ,
    FOREIGN KEY (valued_field_id) REFERENCES valuedfield (id) ON DELETE CASCADE,
    FOREIGN KEY (child_valued_field_id) REFERENCES valuedfield (id) ON DELETE CASCADE
);

CREATE TABLE doc_valuedfields
(
    id                     INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    doc_id                 INTEGER                 NOT NULL,
    valuedfield_id         INTEGER                 NOT NULL,
    position               INTEGER                 NOT NULL,
    FOREIGN KEY (doc_id) REFERENCES doc (id) ON DELETE CASCADE,
    FOREIGN KEY (valuedfield_id) REFERENCES valuedfield (id) ON DELETE CASCADE
);


