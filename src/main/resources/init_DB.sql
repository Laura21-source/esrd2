CREATE SEQUENCE global_seq START 100000;

CREATE TABLE doc
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  id_doctype       INTEGER                 NOT NULL,
  reg_num          VARCHAR                 NOT NULL,
  reg_date         TIMESTAMP               NOT NULL,
  insert_date      TIMESTAMP DEFAULT now() NOT NULL,
  FOREIGN KEY (id_doctype) REFERENCES doctype (id) ON DELETE CASCADE
);

CREATE TABLE doctype
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL
);

CREATE TABLE field
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  id_fieldtype     INTEGER                 NOT NULL,
  length           INTEGER                 NOT NULL,
  FOREIGN KEY (id_fieldtype) REFERENCES fieldtype (id) ON DELETE CASCADE
);

CREATE TABLE fieldtype
(
  id               INTEGER                 NOT NULL,
  name             INTEGER                 NOT NULL
);

CREATE TABLE doctypefields
(
  id_doctype       INTEGER                 NOT NULL,
  id_field         INTEGER                 NOT NULL,
  position         INTEGER                 NOT NULL,
  positionin_group INTEGER                         ,
  group_num        INTEGER                         ,
  is_multiple      BOOLEAN                         ,
  max_count        INTEGER                         ,
  CONSTRAINT doctypefields UNIQUE (id_doctype, id_field, group_num)
);

CREATE TABLE docfields
(
  id_doc                INTEGER                 NOT NULL,
  id_field              INTEGER                 NOT NULL,
  position_fact         INTEGER                 NOT NULL,
  positionin_group      INTEGER                         ,
  group_num             INTEGER                         ,
  int_value             INTEGER                         ,
  str_value             VARCHAR                         ,
  date_value            TIMESTAMP                       ,
  id_var                INTEGER                         ,
  CONSTRAINT docfields UNIQUE (id_doc, id_field),
  FOREIGN KEY (id_var) REFERENCES var (id) ON DELETE CASCADE
);


CREATE TABLE var
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             INTEGER                 NOT NULL,
  vartype          INTEGER                 NOT NULL
);


CREATE TABLE varelem
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  val_int          INTEGER                 NOT NULL,
  val_str          INTEGER                 NOT NULL,
  id_var           INTEGER                 NOT NULL,
  FOREIGN KEY (id_var) REFERENCES var (id) ON DELETE CASCADE
);