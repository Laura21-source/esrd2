DROP TABLE IF EXISTS role_child_role;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS doctype_routes;
DROP TABLE IF EXISTS doc_agreement;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS field_child_field;
DROP TABLE IF EXISTS doctype_fields;
DROP TABLE IF EXISTS field;
DROP TABLE IF EXISTS valuedfield_child_valued_field;
DROP TABLE IF EXISTS doc_valuedfields;
DROP TABLE IF EXISTS doc;
DROP TABLE IF EXISTS doctype;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS valuedfield;
DROP TABLE IF EXISTS catalogelem;
DROP TABLE IF EXISTS catalog;

DROP SEQUENCE IF EXISTS global_seq;
DROP SEQUENCE IF EXISTS agreement_seq;
DROP SEQUENCE IF EXISTS depr_seq;
CREATE SEQUENCE esrd.global_seq START 100000;
CREATE SEQUENCE esrd.agreement_seq START 1;
CREATE SEQUENCE esrd.depr_seq START 1;

CREATE TABLE esrd.role
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    name            VARCHAR                 NOT NULL
);

CREATE TABLE esrd.role_child_role
(
    role_id          INTEGER                         NOT NULL,
    child_role_id    INTEGER                         NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE,
    FOREIGN KEY (child_role_id) REFERENCES role (id) ON DELETE CASCADE
);

CREATE TABLE esrd.users
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    name        VARCHAR                 NOT NULL,
    lastname    VARCHAR                         ,
    firstname   VARCHAR                         ,
    patronym    VARCHAR                         ,
    email       VARCHAR                         ,
    phone       VARCHAR                         ,
    position    VARCHAR
);

CREATE TABLE esrd.user_roles
(
    user_id          INTEGER            NOT NULL,
    role_id          INTEGER            NOT NULL,
    FOREIGN KEY (user_id) REFERENCES esrd.users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES esrd.role (id) ON DELETE CASCADE
);

CREATE TABLE esrd.doctype
(
    id                      INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    name                    VARCHAR                 NOT NULL,
    tmp_template_filename   VARCHAR                         ,
    template_filename       VARCHAR                         ,
    role_id                 INTEGER                 NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

CREATE TABLE esrd.doc
(
    id                      INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    doctype_id              INTEGER                      NOT NULL,
    reg_num                 VARCHAR                              ,
    reg_datetime            TIMESTAMP                            ,
    project_reg_num         VARCHAR                      NOT NULL,
    project_reg_datetime    TIMESTAMP DEFAULT now()      NOT NULL,
    insert_datetime         TIMESTAMP DEFAULT now()      NOT NULL,
    cur_agree_stage         INTEGER                              ,
    url_pdf                 VARCHAR                              ,
    FOREIGN KEY (doctype_id) REFERENCES esrd.doctype (id) ON DELETE CASCADE
);

CREATE TABLE esrd.doctype_routes
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    doctype_id       INTEGER                 NOT NULL,
    user_id          INTEGER                 NOT NULL,
    agree_stage      INTEGER                 NOT NULL,
    FOREIGN KEY (doctype_id) REFERENCES esrd.doctype (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES esrd.users (id) ON DELETE CASCADE
);

CREATE TABLE esrd.doc_agreement
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    doc_id           INTEGER                 NOT NULL,
    user_id          INTEGER                 NOT NULL,
    agreed_datetime  TIMESTAMP                       ,
    comment          VARCHAR                         ,
    decision_type    VARCHAR                         ,
    FOREIGN KEY (doc_id) REFERENCES esrd.doc (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES esrd.users (id) ON DELETE CASCADE
);

/*
CREATE TABLE doc_routes
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    doctype_id       INTEGER                 NOT NULL,
    userldap         VARCHAR                 NOT NULL,
    agree_order      INTEGER                 NOT NULL,
    FOREIGN KEY (doctype_id) REFERENCES doctype (id) ON DELETE CASCADE
);*/

CREATE TABLE esrd.catalog
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    parent_catalog_id    INTEGER                         ,
    name             VARCHAR                 NOT NULL,
    catalogtype_id       INTEGER                 NOT NULL
);

CREATE TABLE esrd.catalogelem
(
    id                      INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    value_int               INTEGER                         ,
    value_str               VARCHAR                         ,
    catalog_id              INTEGER                 NOT NULL,
    parent_catalogelem_id   INTEGER                         ,
    FOREIGN KEY (catalog_id) REFERENCES esrd.catalog (id) ON DELETE CASCADE
);

CREATE TABLE esrd.field
(
    id                INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    name              VARCHAR                 NOT NULL,
    fieldtype         VARCHAR                 NOT NULL,
    position_in_group INTEGER                         ,
    max_count         INTEGER                         ,
    length            INTEGER                         ,
    catalog_id        INTEGER                         ,
    required          BOOLEAN DEFAULT FALSE   NOT NULL,
    role_id           INTEGER                         ,
    tag               VARCHAR                         ,
    FOREIGN KEY (catalog_id) REFERENCES esrd.catalog (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES esrd.role (id) ON DELETE CASCADE
);

CREATE TABLE esrd.field_child_field
(
    field_id          INTEGER                         ,
    child_field_id    INTEGER                         ,
    FOREIGN KEY (field_id) REFERENCES esrd.field (id) ON DELETE CASCADE,
    FOREIGN KEY (child_field_id) REFERENCES esrd.field (id) ON DELETE CASCADE
);

CREATE TABLE esrd.doctype_fields
(
    id                INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    doctype_id          INTEGER                 NOT NULL,
    field_id            INTEGER                 NOT NULL,
    position            INTEGER                 NOT NULL,
    CONSTRAINT c_doctype_fields UNIQUE (doctype_id, field_id, position),
    FOREIGN KEY (doctype_id) REFERENCES esrd.doctype (id) ON DELETE CASCADE,
    FOREIGN KEY (field_id) REFERENCES esrd.field (id) ON DELETE CASCADE
);

CREATE TABLE esrd.valuedfield
(
    id                INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    field_id          INTEGER                 NOT NULL,
    catalogelem_id    INTEGER                         ,
    value_int         INTEGER                         ,
    value_str         VARCHAR                         ,
    value_date        DATE                            ,
    value_time        TIME                            ,
    value_datetime    TIMESTAMP                       ,
    FOREIGN KEY (catalogelem_id) REFERENCES esrd.catalogelem (id) ON DELETE CASCADE
);

CREATE TABLE esrd.valuedfield_child_valued_field
(
    valued_field_id          INTEGER                         ,
    child_valued_field_id    INTEGER                         ,
    FOREIGN KEY (valued_field_id) REFERENCES esrd.valuedfield (id) ON DELETE CASCADE,
    FOREIGN KEY (child_valued_field_id) REFERENCES esrd.valuedfield (id) ON DELETE CASCADE
);

CREATE TABLE esrd.doc_valuedfields
(
    id                     INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    doc_id                 INTEGER                 NOT NULL,
    valuedfield_id         INTEGER                 NOT NULL,
    position               INTEGER                 NOT NULL,
    FOREIGN KEY (doc_id) REFERENCES esrd.doc (id) ON DELETE CASCADE,
    FOREIGN KEY (valuedfield_id) REFERENCES esrd.valuedfield (id) ON DELETE CASCADE
);


