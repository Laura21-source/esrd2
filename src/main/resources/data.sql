INSERT INTO public.doctype (id, name) VALUES (1, 'Повестка заседания Правления');
INSERT INTO public.doctype (id, name) VALUES (2, 'Приказ');
INSERT INTO public.doctype (id, name) VALUES (3, 'Протокол');

INSERT INTO public.field (id, name, id_fieldtype, id_var, length) VALUES (4, 'Дата заседания', 3, null, null);
INSERT INTO public.field (id, name, id_fieldtype, id_var, length) VALUES (5, 'Время заседания', 4, null, null);

INSERT INTO public.doctype_fields (id, id_doctype, id_field, position, id_groupedfield, position_in_group, role)
VALUES (6, 1, 4, 1, null, null, 'ROLE_ELECTRO_ENERGY');
INSERT INTO public.doctype_fields (id, id_doctype, id_field, position, id_groupedfield, position_in_group, role)
VALUES (7, 1, 5, 2, null, null, 'ROLE_ELECTRO_ENERGY');