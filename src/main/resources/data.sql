INSERT INTO public.doctype (id, name) VALUES (1, 'Повестка заседания Правления');
INSERT INTO public.doctype (id, name) VALUES (2, 'Приказ');
INSERT INTO public.doctype (id, name) VALUES (3, 'Протокол');

INSERT INTO public.catalog (id, parent_catalog_id, name, catalogtype_id) VALUES (7, null, 'Сферы деятельности', 0);
INSERT INTO public.catalog (id, parent_catalog_id, name, catalogtype_id) VALUES (9, 8, 'Тарифы', 0);
INSERT INTO public.catalog (id, parent_catalog_id, name, catalogtype_id) VALUES (10, null, 'Предмет вопроса повестки', 0);

INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (11, null, 'газ', 7, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (12, null, 'тепло', 7, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (13, null, 'электричество', 7, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (14, null, 'тариф на транспортировку по магистральным газопроводам', 9, 11);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (15, null, 'тариф на транспортировку по газораспределительным каналам', 9, 11);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (16, null, 'тариф на производство тепла для потребителей', 9, 12);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (17, null, 'тариф на производство тепла в режиме комбинирования', 9, 12);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (18, null, 'тариф на присоединение к электрическим сетям', 9, 13);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (19, null, 'тариф на поставку электроэнергии', 9, 13);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (20, null, 'Установление тарифа', 10, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (21, null, 'Корректировка тарифа', 10, null);

INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id) VALUES (4, 'Дата заседания', 'DATE', null, null, null, null);
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id) VALUES (5, 'Время заседания', 'TIME', null, null, null, null);
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id) VALUES (22, 'Вопросы повестки', 'GROUP_FIELDS', null, 4, null, null);
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id) VALUES (23, 'Предмет вопроса', 'CATALOG', null, null, null, 10);
INSERT INTO public.field (id, name,  fieldtype, position_in_group, max_count, length, catalog_id) VALUES (24, 'Сфера деятельности', 'CATALOG', null, null, null, 7);

INSERT INTO public.field_child_field(field_id, child_field_id) VALUES (22, 23);
INSERT INTO public.field_child_field(field_id, child_field_id) VALUES (22, 24);

INSERT INTO public.doctype_fields (id, doctype_id, field_id, position, role) VALUES (25, 1, 4, 1, 'ROLE_ELECTRO_ENERGY');
INSERT INTO public.doctype_fields (id, doctype_id, field_id, position, role) VALUES (26, 1, 5, 2, 'ROLE_ELECTRO_ENERGY');
INSERT INTO public.doctype_fields (id, doctype_id, field_id, position, role) VALUES (27, 1, 22, 3, 'ROLE_ELECTRO_ENERGY');

INSERT INTO public.doc (id, doctype_id, reg_num, reg_date, insert_date) VALUES (28, 1, 'согл-1/19', '2019-08-15 00:00:00.000000', '2019-08-15 00:00:00.000000');

INSERT INTO public.valuedfield (id, field_id, catalogelem_id, value_int, value_str, value_date, value_time, value_datetime) VALUES (29, 4, null, null, null, '2019-08-16', null, null);
INSERT INTO public.valuedfield (id, field_id, catalogelem_id, value_int, value_str, value_date, value_time, value_datetime) VALUES (30, 5, null, null, null, null, '14:00:00.000000', null);
INSERT INTO public.valuedfield (id, field_id, catalogelem_id, value_int, value_str, value_date, value_time, value_datetime) VALUES (31, 22, null, null, null, null, null, null);
INSERT INTO public.valuedfield (id, field_id, catalogelem_id, value_int, value_str, value_date, value_time, value_datetime) VALUES (32, 23, 12, null, null, null, null, null);
INSERT INTO public.valuedfield (id, field_id, catalogelem_id, value_int, value_str, value_date, value_time, value_datetime) VALUES (33, 24, 16, null, null, null, null, null);

INSERT INTO public.valuedfield_child_valuedfield (valuedfield_id, child_valuedfield_id) VALUES (31, 32);
INSERT INTO public.valuedfield_child_valuedfield (valuedfield_id, child_valuedfield_id) VALUES (31, 33);

INSERT INTO public.doc_valuedfields (id, doc_id, valuedfield_id, position) VALUES (35, 28, 30, 2);
INSERT INTO public.doc_valuedfields (id, doc_id, valuedfield_id, position) VALUES (36, 28, 31, 3);
INSERT INTO public.doc_valuedfields (id, doc_id, valuedfield_id, position) VALUES (34, 28, 29, 1);

