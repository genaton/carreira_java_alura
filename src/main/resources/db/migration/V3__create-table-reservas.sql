create table reservas(
    id bigint not null auto_increment,
    data_hora_reserva_inicio datetime not null,
    data_hora_reserva_fim datetime not null,
    status varchar(20) not null default 'ATIVA',
    usuario_id bigint not null,
    sala_id bigint not null,
    primary key(id),
    CONSTRAINT fk_reservas_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    CONSTRAINT fk_reservas_sala_id FOREIGN KEY (sala_id) REFERENCES salas (id)

);