insert into consumer values(default, true, 'Для врачей, учителей и сотрудников ИТ',
                            '2035-10-10T00:00:002',  5000000, 84, 100000, 4.5, 12, 'Кредит особого назначения',
                            '2023-10-10T18:00:00', 'https://www.psbank.ru/Personal/Loans/SpecialPurpose', '3%');
insert into consumer values(default, true, '', '2035-10-10T00:00:00', 5000000, 84, 100000, 6.5, 12,
                            'Кредит для государственных служащих и работников бюджетной сферы',
                            '2023-10-10T18:00:00', 'https://www.psbank.ru/Personal/Loans/PublicServant', '3%');
insert into consumer values(default, true, 'COMMENT', '2035-10-10T00:00:00', 5000000, 84, 100000, 4.5, 12,
                            'Кредит для держателей зарплатных карт', '2023-10-10T18:00:00',
                            'https://www.psbank.ru/Personal/Loans/Salary', '3%');
insert into auto_loan values(default, true, 'COMMENT', '2035-10-10T00:00:00', 1000000, 60, 10000, 10, 12,  'CREDIT4', '2023-10-11T18:16:16', 'URL://wdwd4', 'MILEAGE4');
insert into auto_loan values(default, true, 'COMMENT', '2035-10-10T00:00:00', 1000000, 72, 1000, 5, 6,  'CREDIT5', '2023-10-11T18:16:16', 'URL://wdwd5', 'MILEAGE5');
insert into auto_loan values(default, true, 'COMMENT', '2035-10-10T00:00:00', 1000000, 48, 5000, 12, 24,  'CREDIT6', '2023-10-11T18:16:16', 'URL://wdwd6', 'MILEAGE6');
insert into mortgage values(default, true, '', '2035-10-10T00:00:00', 50000000, 360, 600000, 13.4, 36,
                            'Вторичное жилье в ипотеку', '2023-10-11T18:16:16',
                            'https://www.psbank.ru/Personal/Mortgage/Secondary', '', 'От 15%');
insert into mortgage values(default, true, 'Для семей, где хотя бы один ребенок родился с 1 января 2018 ' ||
                                           'по 31 декабря 2023 или есть несовершеннолетний ребенок с инвалидностью, ' ||
                                           'или есть двое и более несовершеннолетних детей', '2035-10-10T00:00:00',
                            12000000, 360, 600000, 5, 36,  'Семейная ипотека', '2023-10-11T18:16:16',
                            'https://www.psbank.ru/Personal/Mortgage/FamilyMortgage', '', 'От 20%');
insert into mortgage values(default, true, 'Кредит на покупку готового или строящегося жилья',
                            '2035-10-10T00:00:00', 6000000, 360, 600000, 2, 36,  'Госпрограмма.Новые субъекты 2%',
                            '2023-10-11T18:16:16', 'https://www.psbank.ru/Personal/Mortgage/new-territories', '', 'От 10%');
insert into users values(default, '08.12.1990', 'nosoff.4ndr@yandex.ru',  'User', '', 'user', 'false',
                         '$2a$08$k5Q7lc0vPm8V5Ymr8izXO.tbF0UEMyxZy90b3C7x9koXUyOOEp2mu', '89108688194', 'USER', '');
insert into users values(default, '05.03.1954', 'nosoff.4ndr@yandex.ru', 'Operator', '', 'operator', 'false',
                         '$2a$08$XBCh8V3D1fxYuCNHdovOKORhq13SsNwHTwkKIRJD77RoMKuKDZK3O', '89108688194', 'OPERATOR', '');
insert into users values(default, '03.07.1967', 'nosoff.4ndr@yandex.ru', 'Administrator', '', 'admin', 'false',
                         '$2a$08$fEMnfSV6zp8IDWypU6uHK.vX8lFzjlzRR4I8xzjB7iB2MYFESIy4K', '89108688194', 'SUPER_ADMIN', '');