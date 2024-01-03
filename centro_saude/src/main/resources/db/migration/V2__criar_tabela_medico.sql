create table medico (
    id int auto_increment primary key,
    nome varchar(255) not null,
    especialidade varchar(50) not null,
    crm varchar(150) not null unique
);