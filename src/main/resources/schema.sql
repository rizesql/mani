create table users
(
    id         uuid primary key,
    username   varchar(32)  not null,
    email      varchar(255) not null,

    created_at bigint       not null,
    updated_at bigint       not null
);

create table accounts
(
    id        uuid primary key,
    owner_id  uuid           not null,
    amount    numeric(19, 4) not null,
    currency  varchar(3)     not null,

    closed_at bigint,

    constraint fk_owner foreign key (owner_id) references users (id)
);

create table transactions
(
    id                uuid primary key,
    debit_account_id  uuid           not null,
    credit_account_id uuid           not null,
    amount            decimal(19, 4) not null,
    currency          varchar(3)     not null,
    timestamp         bigint         not null,

    constraint fk_account_debit_account_id foreign key (debit_account_id) references accounts (id),
    constraint fk_account_credit_account_id foreign key (credit_account_id) references accounts (id)
);

create table cards
(
    id         uuid primary key,
    account_id uuid        not null,
    iban       varchar(34) not null,
    opened_at  bigint      not null,
    pin        varchar(10),

    constraint fk_account foreign key (account_id) references accounts (id)
);

create table previous_pins
(
    card_id  uuid       not null,
    pin      varchar(4) not null,
    added_at bigint     not null,

    constraint fk_card_pin foreign key (card_id) references cards (id) on delete cascade
);