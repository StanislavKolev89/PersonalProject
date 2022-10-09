create table roles
(
    id   SERIAL
        primary key,
    name varchar(255) not null
);

create table categories
(
    id        SERIAL
        primary key,
    deleted   bit          not null,
    image_url
        text     not null,
    name      varchar(255) not null
);

create table products
(
    id          SERIAL
        primary key,
    deleted     bit            not null,
    description
        text       not null,
    image_url
        text       not null,
    price       decimal(19, 2) not null,
    title       varchar(255)   not null,
    category_id SERIAL       not null,
    constraint FKog2rp4qthbtt2lfyhfo32lsw9
        foreign key (category_id) references categories (id)
);

create table warehouse
(
    id         SERIAL
        primary key,
    quantity   int    not null,
    product_id SERIAL not null,
    constraint FKow13o6v2o8btmca0nw5pblpss
        foreign key (product_id) references products (id)
);

-- auto-generated definition
create table users
(
    id         SERIAL
        primary key,
    active     bit          not null,
    address    varchar(255) not null,
    email      varchar(255) not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    password   varchar(255) not null,
    username   varchar(255) not null,
    role_id    SERIAL      not null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint UK_r43af9ap4edm43mmtq01oddj6
        unique (username),
    constraint FKp56c1712k691lhsyewcssf40f
        foreign key (role_id) references roles (id)
);

-- auto-generated definition
create table used_products
(
    id           SERIAL
        primary key,
    description
        text       not null,
    image_url
        text       null,
    phone_number varchar(255)   not null,
    price        decimal(19, 2) not null,
    title        varchar(255)   not null,
    category_id  SERIAL         not null,
    user_id      SERIAL        not null,
    constraint FK6dudjpfumrokdha1x1fni9eor
        foreign key (category_id) references categories (id),
    constraint FKp0i6ypn5mpd5du3tbibrumvrj
        foreign key (user_id) references users (id)
);


create table orders
(
    id      SERIAL
        primary key,
    created_at datetime(6) not null,
    deleted    bit         not null,
    user_id    SERIAL      not null,
    constraint FK32ql8ubntj5uh44ph9659tiih
        foreign key (user_id) references users (id)
);


create table ordered_products
(
    id         SERIAL
        primary key,
    quantity   int    not null,
    order_id   SERIAL not null,
    product_id SERIAL not null,
    constraint FKi7isf670mbq331v0muqqry1cd
        foreign key (order_id) references orders (id),
    constraint FKn042ir6sf41qikoy2c07gt87i
        foreign key (product_id) references products (id)
);


create table comments
(
    id         SERIAL
        primary key,
    content    text        not null,
    created_at datetime(6) not null,
    author_id  SERIAL      not null,
    product_id SERIAL     not null,
    constraint FK6uv0qku8gsu6x1r2jkrtqwjtn
        foreign key (product_id) references products (id),
    constraint FKn2na60ukhs76ibtpt9burkm27
        foreign key (author_id) references users (id)
);