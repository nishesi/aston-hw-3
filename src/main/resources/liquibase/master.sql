--changeset nurislam:1
create table settlement
(
    settlement_id bigserial primary key,
    name          varchar,
    population    int     default 0,
    has_subway    boolean default false
);

--changeset nurislam:2
create table attraction
(
    attraction_id bigserial primary key,
    name          varchar,
    creation_date date,
    description   varchar(1000),
    type          varchar check (type in ('PALACE', 'PARK', 'MUSEUM', 'ARCHAEOLOGICAL_SITE', 'NATURE_RESERVE')),
    settlement_id bigint references settlement (settlement_id) on delete cascade
);

--changeset nurislam:3
create table service
(
    service_id  bigserial primary key,
    name        varchar,
    description varchar(1000)
);

--changeset nurislam:4
create table service_attractions
(
    service_id    bigint references service (service_id),
    attraction_id bigint references attraction (attraction_id) on delete cascade,
    constraint unique_service_attraction unique (service_id, attraction_id)
);
