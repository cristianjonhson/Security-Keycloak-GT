insert into tbl_company(company_id, company, contact, country)
values (
        1,
        'Centro comercial Moctezuma',
        'Francisco Chang',
        'Mexico'
    ) ON CONFLICT (company_id) DO NOTHING;
insert into tbl_company(company_id, company, contact, country)
values (
        2,
        'Alfreds Futterkiste',
        'Maria Anders',
        'Germany'
    ) ON CONFLICT (company_id) DO NOTHING;
