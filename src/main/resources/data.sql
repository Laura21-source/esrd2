INSERT INTO public.users (id, name, lastname, firstname, patronym, email, phone, position) VALUES (4000, 'admin', 'Махров', 'Станислав', 'Станиславович', 'MakhrovSS1@mos.ru', '15-451', 'Начальник отдела');
INSERT INTO public.users (id, name, lastname, firstname, patronym, email, phone, position) VALUES (4001, 'user1', 'Петров', 'Петр', 'Петрович', 'PetrovPP@mos.ru', null, 'Главный специалист');
INSERT INTO public.users (id, name, lastname, firstname, patronym, email, phone, position) VALUES (4002, 'user2', 'Широкова', 'Елена', 'Юрьевна', 'IvanovII@mos.ru', null, 'Заместитель начальника Управления');

INSERT INTO public.role (id, name) VALUES (3000, 'ADMIN');
INSERT INTO public.role (id, name) VALUES (3001, 'Отраслевое управление');
INSERT INTO public.role (id, name) VALUES (3002, 'Секретарь Правления');
INSERT INTO public.role (id, name) VALUES (3003, 'Дата повестки');
INSERT INTO public.role (id, name) VALUES (3004, 'Время повестки');
INSERT INTO public.role (id, name) VALUES (3005, 'Вопросы повестки');
INSERT INTO public.role (id, name) VALUES (3006, 'USER');

INSERT INTO public.role_child_role (role_id, child_role_id) VALUES (3001, 3003);
INSERT INTO public.role_child_role (role_id, child_role_id) VALUES (3001, 3005);
INSERT INTO public.role_child_role (role_id, child_role_id) VALUES (3002, 3003);

INSERT INTO public.user_roles (user_id, role_id) VALUES (4000, 3000);
INSERT INTO public.user_roles (user_id, role_id) VALUES (4001, 3001);
INSERT INTO public.user_roles (user_id, role_id) VALUES (4001, 3006);
INSERT INTO public.user_roles (user_id, role_id) VALUES (4002, 3002);
INSERT INTO public.user_roles (user_id, role_id) VALUES (4002, 3006);

INSERT INTO public.doctype (id, name, role_id) VALUES (1, 'Повестка заседания Правления', 3001);
INSERT INTO public.doctype (id, name, role_id) VALUES (2, 'Приказ', 3001);
INSERT INTO public.doctype (id, name, role_id) VALUES (3, 'Протокол', 3001);

INSERT INTO public.catalog (id, parent_catalog_id, name, catalogtype_id) VALUES (1006, null, 'Организация', 0);
INSERT INTO public.catalog (id, parent_catalog_id, name, catalogtype_id) VALUES (1007, null, 'Ответственный', 0);
INSERT INTO public.catalog (id, parent_catalog_id, name, catalogtype_id) VALUES (1003, 1001, 'Сфера деятельности', 0);
INSERT INTO public.catalog (id, parent_catalog_id, name, catalogtype_id) VALUES (1005, 1004, 'Подключаемая система при технологическом присоединении', 0);
INSERT INTO public.catalog (id, parent_catalog_id, name, catalogtype_id) VALUES (1001, null, 'Предмет вопроса', 0);
INSERT INTO public.catalog (id, parent_catalog_id, name, catalogtype_id) VALUES (1002, 1001, 'Вопрос', 0);
INSERT INTO public.catalog (id, parent_catalog_id, name, catalogtype_id) VALUES (1008, 1002, 'Номер приказа', 0);
INSERT INTO public.catalog (id, parent_catalog_id, name, catalogtype_id) VALUES (1004, 1003, 'Вид тарифа', 0);

INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2001, null, 'ООО "ТОМА"', 1006, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2002, null, 'ООО "ТЕСТ-1"', 1006, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2003, null, 'ООО "ТЕСТ-2', 1006, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2004, null, 'Соколов И.В., тел. 8 (495) 957-75-00, доб. 55-345', 1007, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2005, null, 'Петров А.А., тел. 8 (495) 957-75-00, доб. 44-345', 1007, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2006, null, 'Сидоров П.А., тел. 8 (495) 957-75-00, доб. 44-245', 1007, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2019, null, 'О признании утратившим силу', 1002, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2059, null, 'производство электрической энергии (мощности) ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2027, null, 'подвоз воды ', 1004, 2024);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2062, null, 'услуги по передаче электрической энергии в целях расчетов с потребителями услуг ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2081, null, 'газораспределительные сети', 1005, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2013, null, 'Об аннулировании платы за подключение ', 1002, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2010, null, 'О корректировке тарифов', 1002, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2028, null, 'транспортировка воды ', 1004, 2024);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2044, null, 'горячая вода (горячее водоснабжение) ', 1004, 2023);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2052, null, 'услуги по транспортировке газа по газораспределительным сетям ', 1004, 2020);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2042, null, 'подключение (технологическое присоединение) к централизованной системе горячего водоснабжения ', 1004, 2023);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2063, null, 'услуги по передаче электрической энергии для сетевых организаций ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2080, null, 'электрические сети', 1005, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2024, null, 'холодное водоснабжение', 1003, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2072, null, 'захоронение твердых коммунальных отходов', 1004, 2026);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2066, null, 'оказание услуг по оперативно-диспетчерскому управлению в электроэнергетике ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2069, null, 'услуга регионального оператора по обращению с твердыми коммунальными отходами', 1004, 2026);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2061, null, 'реализация (сбыт) электрической энергии ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2038, null, 'тепловая энергия (мощность) ', 1004, 2025);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2078, null, 'горячего водоснабжения', 1005, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2068, null, 'индивидуальные цены (тарифы) на услуги по передаче электрической энергии для взаиморасчетов ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2074, null, 'централизованная система холодного водоснабжения', 1005, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2077, null, 'сеть дождевой канализации', 1005, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2064, null, 'услуги коммерческого оператора ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2043, null, 'транспортировка горячей воды ', 1004, 2023);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2039, null, 'теплоноситель ', 1004, 2025);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2046, null, 'компонент на теплоноситель ', 1004, 2023);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2035, null, 'водоотведение с превышением максимальных допустимых значений нормативных показателей общих сточных вод и концентрации загрязняющих веществ ', 1004, 2022);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2031, null, 'подключение (техрологическое подключение) к централизованной системе холодного водоснабжения ', 1004, 2024);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2033, null, 'транспортировка поверхностных сточных вод ', 1004, 2022);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2021, null, 'электричество', 1003, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2007, null, 'Вопрос об изменении тарифа', 1001, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2036, null, 'подключение (технологическое присоединение) к централизованной системе водоотведения.', 1004, 2022);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2060, null, 'производство электрической энергии объектом ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2045, null, 'горячая вода в открытых системах теплоснабжения (горячего водоснабжения) ', 1004, 2023);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2071, null, 'обезвреживание твердых коммунальных отходов', 1004, 2026);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2048, null, 'оптовые цены на газ ', 1004, 2020);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2079, null, 'система теплоснабжения', 1005, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2086, null, 'Приказ Департамента экономической политики и развития города Москвы от 9 декабря 2016 года № 330-ТР ', 1008, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2025, null, 'теплоснабжение (мощность)', 1003, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2040, null, 'услуги по передаче тепловой энергии ', 1004, 2025);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2089, null, 'Приказ Департамента экономической политики и развития города Москвы от 31 марта 2017 года № 45-ТР "Об установлении тарифов на водоотведение для потребителей общества с ограниченной ответственностью «Гамма» на 2017 год"', 1008, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2085, null, 'Приказ Департамента экономической политики и развития города Москвы от 2 декабря 2016 года № 259-ТР', 1008, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2037, null, 'подключение (технологическое присоединение) к системе теплоснабжения ', 1004, 2025);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2084, null, 'Приказ Департамента экономической политики и развития города Москвы от 2 декабря 2016 года № 258-ТР ', 1008, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2029, null, 'питьевая вода ', 1004, 2024);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2008, null, 'Вопрос о внесении изменений в приказ', 1001, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2058, null, 'передача электрической энергии по электрическим сетям ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2082, null, 'Приказ Департамента экономической политики и развития города Москвы от 29 ноября 2016 года № 215-ТР ', 1008, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2049, null, 'розничная цена на сжиженный газ ', 1004, 2020);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2092, null, 'Приказ Департамента экономической политики и развития города Москвы от 11 августа 2017 года № 136-ТР "Об установлении тарифов на водоотведение поверхностных сточных вод для потребителей общества с ограниченной ответственностью «Газпром энерго» Центральный', 1008, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2011, null, 'Об отсутствии оснований для установления тарифов', 1002, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2070, null, 'обработка твердых коммунальных отходов', 1004, 2026);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2090, null, 'Приказ Департамента экономической политики и развития города Москвы от 14 апреля 2017 года № 60-ТР', 1008, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2055, null, 'технологическое присоединение к электрическим сетям ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2012, null, 'Об отсутствии оснований для установления платы', 1002, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2030, null, 'техническая вода ', 1004, 2024);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2083, null, 'Приказ Департамента экономической политики и развития города Москвы от 2 декабря 2016 года № 254-ТР ', 1008, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2050, null, 'услуги по транспортировке газа по магистральным газопроводам ', 1004, 2020);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2026, null, 'твердые коммунальные отходы', 1003, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2009, null, 'Об установлении тарифов', 1002, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2075, null, 'централизованная система горячего водоснабжения', 1005, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2065, null, 'оказание услуг по обеспечению системной надежности ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2093, null, 'Приказ Департамента экономической политики и развития города Москвы от 11 августа 2017 года № 136-ТР "Об установлении тарифов на водоотведение поверхностных сточных вод для потребителей общества с ограниченной ответственностью «Газпром энерго» Центральный', 1008, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2056, null, 'сбытовые надбавки гарантирующих поставщиков электрической энергии ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2020, null, 'газ', 1003, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2034, null, 'транспортировка хозяйственно-бытовых сточных вод ', 1004, 2022);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2015, null, 'Об установлении долгосрочных тарифов', 1002, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2091, null, 'Приказ Департамента экономической политики и развития города Москвы от 28 апреля 2017 года № 90-ТР', 1008, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2016, null, 'Об установлении размера платы', 1002, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2018, null, 'О внесении изменений', 1002, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2088, null, 'Приказ Департамента экономической политики и развития города Москвы от 14 декабря 2016 года № 421-ТР', 1008, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2022, null, 'водоотведение', 1003, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2067, null, 'электрическая энергия (мощность) ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2051, null, 'услуги по транспортировке газа по газопроводам ', 1004, 2020);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2057, null, 'передача электрической энергии по единой национальной (общероссийской) электрической сети  ', 1004, 2021);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2032, null, 'водоотведение ', 1004, 2022);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2023, null, 'горячее водоснабжение', 1003, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2041, null, 'услуги по поддержанию резервной тепловой мощности при отсутствии потребления тепловой энергии', 1004, 2025);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2047, null, 'технологическое присоединение газоиспользующего оборудования к газораспределительным сетям ', 1004, 2020);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2087, null, 'Приказ Департамента экономической политики и развития города Москвы от 9 декабря 2016 года № 331-ТР', 1008, 2008);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2053, null, 'снабженческо-сбытовые услуги ', 1004, 2020);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2073, null, 'централизованная система водоснабжения', 1005, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2054, null, 'специальные надбавки к тарифам на услуги по транспортировке газа по газораспределительным сетям ', 1004, 2020);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2014, null, 'Об установлении стандартизированный тарифных ставок для расчета платы', 1002, 2007);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2076, null, 'централизованная система водоотведения', 1005, null);
INSERT INTO public.catalogelem (id, value_int, value_str, catalog_id, parent_catalogelem_id) VALUES (2017, null, 'О корректировке долгосрочных тарифов', 1002, 2007);

INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id, required, role_id, tag) VALUES (4, 'Дата заседания', 'DATE', null, null, null, null, true, 3003, 'MeetingDate');
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id, required, role_id, tag) VALUES (5, 'Время заседания', 'TIME', null, null, null, null, true, 3004, 'MeetingTime');
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id, required, role_id, tag) VALUES (6, 'Вопросы повестки', 'GROUP_FIELDS', null, 4, null, null, true, 3005, '');
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id, required, tag) VALUES (7, 'Предмет вопроса', 'CATALOG', null, null, null, 1001, true, '[Questions]Subject');
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id, required, tag) VALUES (8, 'Вопрос', 'CATALOG', null, null, null, 1002, false, '[Questions]Question');
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id, required, tag) VALUES (9, 'Сфера деятельности', 'CATALOG', null, null, null, 1003, false, '[Questions]Direction');
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id, required, tag) VALUES (10, 'Номер приказа', 'CATALOG', null, null, null, 1008, false, '[Questions]OrderNumber');
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id, required, tag) VALUES (11, 'Вид тарифа', 'CATALOG', null, null, null, 1004, false, '[Questions]TarifView');
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id, required, tag) VALUES (12, 'Подключаемая система при техническом присоединении', 'CATALOG', null, null, null, 1005, false, '[Questions]TechConnection');
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id, required, tag) VALUES (13, 'Организация', 'CATALOG', null, null, null, 1006, true, '[Questions]Organization');
INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id, required, tag) VALUES (14, 'Ответственный', 'CATALOG', null, null, null, 1007, true, '[Questions]AuthPerson');
--INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id, tag) VALUES (14, 'Email ответственного', 'CATALOG', null, null, null, null, 'EmailPerson');
--INSERT INTO public.field (id, name, fieldtype, position_in_group, max_count, length, catalog_id) VALUES (15, 'Вложение', 'ATTACHMENT', null, null, null, null);

INSERT INTO public.field_child_field(field_id, child_field_id) VALUES (6, 7);
INSERT INTO public.field_child_field(field_id, child_field_id) VALUES (6, 8);
INSERT INTO public.field_child_field(field_id, child_field_id) VALUES (6, 9);
INSERT INTO public.field_child_field(field_id, child_field_id) VALUES (6, 10);
INSERT INTO public.field_child_field(field_id, child_field_id) VALUES (6, 11);
INSERT INTO public.field_child_field(field_id, child_field_id) VALUES (6, 12);
INSERT INTO public.field_child_field(field_id, child_field_id) VALUES (6, 13);
--INSERT INTO public.field_child_field(field_id, child_field_id) VALUES (6, 14);
--INSERT INTO public.field_child_field(field_id, child_field_id) VALUES (6, 15);

INSERT INTO public.doctype_fields (id, doctype_id, field_id, position) VALUES (16, 1, 4, 1);
INSERT INTO public.doctype_fields (id, doctype_id, field_id, position) VALUES (17, 1, 5, 2);
INSERT INTO public.doctype_fields (id, doctype_id, field_id, position) VALUES (18, 1, 6, 3);

INSERT INTO public.doctype_routes (id, doctype_id, user_id, agree_stage)VALUES (20, 1, '4002', 1);
INSERT INTO public.doctype_routes (id, doctype_id, user_id, agree_stage)VALUES (21, 1, '4000', 2);