create table salas(
    id bigint not null auto_increment,
    nome varchar(100) not null unique,
    numero int(5) not null,
    capacidade int(3) not null,
    ativa tinyint(1) not null default 1,
    primary key(id)
       
);