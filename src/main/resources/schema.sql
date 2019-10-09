DROP TABLE IF EXISTS esrd.role_child_role CASCADE;
DROP TABLE IF EXISTS esrd.user_roles CASCADE;
DROP TABLE IF EXISTS esrd.doc_number_prefixes CASCADE;
DROP TABLE IF EXISTS esrd.doctype_routes CASCADE;
DROP TABLE IF EXISTS esrd.doc_agreement CASCADE;
DROP TABLE IF EXISTS esrd.users CASCADE;
DROP TABLE IF EXISTS esrd.field_child_field CASCADE;
DROP TABLE IF EXISTS esrd.fields_stages CASCADE;
DROP TABLE IF EXISTS esrd.doctype_fields CASCADE;
DROP TABLE IF EXISTS esrd.field CASCADE;
DROP TABLE IF EXISTS esrd.valuedfield_child_valued_field CASCADE;
DROP TABLE IF EXISTS esrd.doc_valuedfields CASCADE;
DROP TABLE IF EXISTS esrd.doc CASCADE;
DROP TABLE IF EXISTS esrd.doctype CASCADE;
DROP TABLE IF EXISTS esrd.role CASCADE;
DROP TABLE IF EXISTS esrd.valuedfield CASCADE;
DROP TABLE IF EXISTS esrd.catalogelem CASCADE;
DROP TABLE IF EXISTS esrd.catalog CASCADE;
DROP TABLE IF EXISTS esrd.organization CASCADE;

DROP SEQUENCE IF EXISTS esrd.global_seq CASCADE;
DROP SEQUENCE IF EXISTS esrd.agreement_seq CASCADE;
DROP SEQUENCE IF EXISTS esrd.agenda_seq CASCADE;
CREATE SEQUENCE esrd.global_seq START 100000;
CREATE SEQUENCE esrd.agreement_seq START 1;
CREATE SEQUENCE esrd.agenda_seq START 1;

CREATE TABLE esrd.role
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    name            VARCHAR                 NOT NULL
);

CREATE TABLE esrd.role_child_role
(
    role_id          INTEGER                         NOT NULL,
    child_role_id    INTEGER                         NOT NULL,
    FOREIGN KEY (role_id) REFERENCES esrd.role (id) ON DELETE CASCADE,
    FOREIGN KEY (child_role_id) REFERENCES esrd.role (id) ON DELETE CASCADE
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

CREATE TABLE esrd.doc_number_prefixes
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    name       VARCHAR      NOT NULL
);

CREATE TABLE esrd.doctype
(
    id                      INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    name                    VARCHAR                 NOT NULL,
    tmp_template_filename   VARCHAR                         ,
    template_filename       VARCHAR                         ,
    role_id                 INTEGER                 NOT NULL,
    doc_number_prefix_id    INTEGER                 NOT NULL,
    FOREIGN KEY (role_id) REFERENCES esrd.role (id) ON DELETE CASCADE,
    FOREIGN KEY (doc_number_prefix_id) REFERENCES esrd.doc_number_prefixes (id) ON DELETE CASCADE
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
    docstatus               VARCHAR                      NOT NULL,
    cur_agree_stage         INTEGER DEFAULT 0            NOT NULL,
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
    returned_user_id INTEGER                         ,
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
    tag               VARCHAR                         ,
    FOREIGN KEY (catalog_id) REFERENCES esrd.catalog (id) ON DELETE CASCADE
);

CREATE TABLE esrd.field_child_field
(
    field_id          INTEGER                         ,
    child_field_id    INTEGER                         ,
    FOREIGN KEY (field_id) REFERENCES esrd.field (id) ON DELETE CASCADE,
    FOREIGN KEY (child_field_id) REFERENCES esrd.field (id) ON DELETE CASCADE
);

CREATE TABLE esrd.fields_stages
(
    id                INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    doctype_id        INTEGER                 NOT NULL,
    field_id          INTEGER                 NOT NULL,
    agree_stage       INTEGER                 NOT NULL,
    required          BOOLEAN DEFAULT FALSE   NOT NULL,
    role_id           INTEGER                         ,
    FOREIGN KEY (doctype_id) REFERENCES esrd.doctype (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES esrd.role (id) ON DELETE CASCADE
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

CREATE TABLE esrd.organization
(
    id                     INTEGER PRIMARY KEY DEFAULT nextval('esrd.global_seq'),
    short_name_lf          VARCHAR                 NOT NULL,
    full_name_lf           VARCHAR                 NOT NULL,
    short_name             VARCHAR                 NOT NULL,
    full_name              VARCHAR                 NOT NULL,
    short_legal_form       VARCHAR                 NOT NULL,
    full_legal_form        VARCHAR                 NOT NULL,
    normalized_name        VARCHAR                 NOT NULL,
    ogrn                   VARCHAR                 NOT NULL,
    inn                    VARCHAR                 NOT NULL,
    kpp                    VARCHAR                 NOT NULL,
    address                VARCHAR                 NOT NULL,
    fio_manager            VARCHAR                 NOT NULL,
    position_manager       VARCHAR                 NOT NULL,
    CONSTRAINT c_organization UNIQUE (inn)
);

CREATE OR REPLACE FUNCTION esrd.generateDocNumber (mask VARCHAR, optional VARCHAR DEFAULT NULL)
    RETURNS VARCHAR AS $$
DECLARE Result VARCHAR;
DECLARE yearPostfix VARCHAR;
BEGIN
    yearPostfix = SUBSTRING(CAST(DATE_PART('year', CURRENT_DATE) AS text),3);
    IF (mask='согл') THEN
        SELECT 'согл-'||nextval('esrd.agreement_seq')||'/'||yearPostfix INTO Result;
    ELSIF (mask='ДПР-П') THEN
        SELECT 'ДПР-П-'||optional||'/'||yearPostfix INTO Result;
    ELSIF (mask='ДПР') THEN
        SELECT 'ДПР-'||nextval('esrd.agenda_seq')||'/'||yearPostfix INTO Result;
    ELSE
        RAISE 'Incorrect mask for generating document number' USING ERRCODE = 'INCORRECT_DOCNUMBER_MASK';
    END IF;
    RETURN Result;
END; $$ LANGUAGE plpgsql;


