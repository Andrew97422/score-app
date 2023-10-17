insert into consumer values(default, true, 'Для врачей, учителей и сотрудников ИТ',
                            '2035-10-10T00:00:00',  5000000, 84, 100000, 4.5, 12, 'Кредит особого назначения',
                            true, '2023-10-10T18:00:01', 'https://www.psbank.ru/Personal/Loans/SpecialPurpose', '3%');
insert into consumer values (default, true, '', '2035-10-10T00:00:00', 5000000, 84, 100000, 4.5, 12,
                             'Кредит для военнослужащих и работников ОПК России', true, '2023-10-10T18:00:02',
                             'https://www.psbank.ru/Personal/Loans/CreditAction', '3%');
insert into consumer values (default, true, '', '2035-10-10T00:00:00', 5000000, 84, 100000, 6.5, 12,
                             'Кредит для государственных служащих и работников бюджетной сферы', true, '2023-10-10T18:00:02',
                             'https://www.psbank.ru/Personal/Loans/PublicServant', '3%');
insert into consumer values (default, true, '', '2035-10-10T00:00:00', 5000000, 84, 100000, 8.5, 12,
                             'Кредит для клиентов с кредитной историей ПСБ', true, '2023-10-10T18:00:02',
                             'https://www.psbank.ru/Personal/Loans/TestOfTime', '3%');
insert into consumer values (default, true, '', '2035-10-10T00:00:00', 5000000, 84, 100000, 8.5, 12,
                             'Кредит для вкладчиков ПСБ', true, '2023-10-10T18:00:02',
                             'https://www.psbank.ru/Personal/Loans/Deposit', '3%');
insert into consumer values (default, true, '', '2035-10-10T00:00:00', 5000000, 84, 50000, 4.5, 12,
                             'Рефинансирование кредитов', true, '2023-10-10T18:00:02',
                             'https://www.psbank.ru/Personal/Loans/Refinancing', '3%');
insert into consumer values (default, true, '', '2035-10-10T00:00:00', 3000000, 60, 100000, 16.0, 12,
                             'Кредит «Открытый рынок»', true, '2023-10-10T18:00:02',
                             'https://www.psbank.ru/Personal/Loans/OpenMarket', '3%');
insert into consumer values (default, true, '', '2035-10-10T00:00:00', 1500000, 60, 100000, 13.2, 12,
                             'Кредит для военных и гражданских пенсионеров', true, '2023-10-10T18:00:02',
                             'https://www.psbank.ru/Personal/Loans/forSeniors', '3%');
insert into consumer values(default, true, '', '2035-10-10T00:00:00', 5000000, 84, 100000, 4.5, 12,
                            'Кредит для держателей зарплатных карт', true, '2023-10-10T18:00:03',
                            'https://www.psbank.ru/Personal/Loans/Salary', '3%');
insert into auto_loan values(default, true, 'COMMENT', '2035-10-10T00:00:00', 1000000, 60, 10000, 10, 12,  'CREDIT4', false, '2023-10-11T18:16:16', 'URL://wdwd4', 'MILEAGE4');
insert into auto_loan values(default, true, 'COMMENT', '2035-10-10T00:00:00', 1000000, 72, 1000, 5, 6,  'CREDIT5', false, '2023-10-11T18:16:16', 'URL://wdwd5', 'MILEAGE5');
insert into auto_loan values(default, true, 'COMMENT', '2035-10-10T00:00:00', 1000000, 48, 5000, 12, 24,  'CREDIT6', false, '2023-10-11T18:16:16', 'URL://wdwd6', 'MILEAGE6');
insert into mortgage values(default, true, '', '2035-10-10T00:00:00', 50000000, 360, 600000, 13.4, 36,
                            'Вторичное жилье в ипотеку', true, '2023-10-11T18:16:16',
                            'https://www.psbank.ru/Personal/Mortgage/Secondary', '', 'От 15%');
insert into mortgage values(default, true, 'Для семей, где хотя бы один ребенок родился с 1 января 2018 ' ||
                                           'по 31 декабря 2023 или есть несовершеннолетний ребенок с инвалидностью, ' ||
                                           'или есть двое и более несовершеннолетних детей', '2035-10-10T00:00:00',
                            12000000, 360, 600000, 5, 36,  'Семейная ипотека', true, '2023-10-11T18:16:16',
                            'https://www.psbank.ru/Personal/Mortgage/FamilyMortgage', '', 'От 20%');
insert into mortgage values(default, true, 'Кредит на покупку готового или строящегося жилья',
                            '2035-10-10T00:00:00', 6000000, 360, 600000, 2, 36,  'Госпрограмма.Новые субъекты 2%', true,
                            '2023-10-11T18:16:16', 'https://www.psbank.ru/Personal/Mortgage/new-territories', '', 'От 10%');
insert into mortgage values(default, true, 'Для участников НИС, у которых в семье двое и более несовершеннолетних ребенка,' ||
                                           ' или с 1 января 2018 года по 31 декабря 2023 года родился ребенок, ' ||
                                           'или у которых есть ребенок не старше 18 лет с установленной категорией ' ||
                                           '«ребенок-инвалид»', '2035-10-10T00:00:00', 4710000, 300, 600000, 5.6, 36,
                            'Семейная военная ипотека', true,'2023-10-11T18:16:16',
                            'https://www.psbank.ru/Personal/Mortgage/family-military-mortgage', '', 'От 20%');
insert into mortgage values(default, true, 'Переведите ипотеку из другого банка и платите меньше',
                            '2035-10-10T00:00:00', 4700000, 300, 600000, 5.6, 36,  'Военная ипотека. Рефинансирование', true,
                            '2023-10-11T18:16:16', 'https://www.psbank.ru/Personal/Mortgage/refinancing-military', '', 'От 20%');
insert into mortgage values(default, true, 'Кредит на покупку готового или строящегося жилья',
                            '2035-10-10T00:00:00', 3920000, 300, 600000, 7.6, 36,  'Военная ипотека 7,6%. Госпрограмма', true,
                            '2023-10-11T18:16:16', 'https://www.psbank.ru/Personal/Mortgage/military-program', '', 'От 20%');
insert into mortgage values(default, true, '','2035-10-10T00:00:00', 6000000, 300, 600000, 2, 12,
                            'Госпрограмма. Военная ипотека. Новые субъекты 2%', true,
                            '2023-10-11T18:16:16', 'https://www.psbank.ru/Personal/Mortgage/Military', '', 'От 10%');
insert into mortgage values(default, true, 'Кредит на покупку готового или строящегося жилья',
                            '2035-10-10T00:00:00', 12000000, 360, 600000, 8, 36,  'Госпрограмма 2020', true,
                            '2023-10-11T18:16:16', 'https://www.psbank.ru/Personal/Mortgage/2020', '', 'От 20%');
insert into mortgage values(default, true, '', '2035-10-10T00:00:00', 18000000, 360, 600000, 4.8, 36,
                            'Госпрограмма. ИТ-специалисты', true,
                            '2023-10-11T18:16:16', 'https://www.psbank.ru/Personal/Mortgage/it-program', '', 'От 20%');
insert into mortgage values(default, true, '', '2035-10-10T00:00:00', 50000000, 360, 600000, 13.3, 36,
                            'Ипотека на новостройку', true,
                            '2023-10-11T18:16:16', 'https://www.psbank.ru/Personal/Mortgage/NewBuilding', '', 'От 15%');
insert into mortgage values(default, true, 'На покупку жилья и другие цели', '2035-10-10T00:00:00',
                            50000000, 360, 600000, 13.4, 36, 'Кредит под залог квартиры', true,
                            '2023-10-11T18:16:16', 'https://www.psbank.ru/Personal/Mortgage/Alternative', '', 'От 15%');
insert into mortgage values(default, true, 'Кредит на покупку готового или строящегося жилья в ДФО', '2035-10-10T00:00:00',
                            6000000, 240, 600000, 1.5, 36, 'Дальневосточная ипотека', true,
                            '2023-10-11T18:16:16', 'https://www.psbank.ru/Personal/Mortgage/East', '', 'От 20%');
insert into users values(default, '08.12.1990', 'nosoff.4ndr@yandex.ru',  'Тестовый пользователь', '', 'user', 'false',
                         '$2a$08$k5Q7lc0vPm8V5Ymr8izXO.tbF0UEMyxZy90b3C7x9koXUyOOEp2mu', '89108688194', 'USER', '');
insert into users values(default, '05.03.1954', 'nosoff.4ndr@yandex.ru', 'Операционист', '', 'operator', 'false',
                         '$2a$08$XBCh8V3D1fxYuCNHdovOKORhq13SsNwHTwkKIRJD77RoMKuKDZK3O', '89108688194', 'OPERATOR', '');
insert into users values(default, '03.07.1967', 'nosoff.4ndr@yandex.ru', 'Администратор', '', 'admin', 'false',
                         '$2a$08$fEMnfSV6zp8IDWypU6uHK.vX8lFzjlzRR4I8xzjB7iB2MYFESIy4K', '89108688194', 'SUPER_ADMIN', '');