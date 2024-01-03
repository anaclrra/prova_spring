create table paciente (
    id int auto_increment primary key,
    nome varchar(255) not null,
    cpf char(11) unique,
    historico_medico varchar(255) not null
);