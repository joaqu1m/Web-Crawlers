create table crawlerComponente(
idComponente int PRIMARY KEY identity(1, 1),
nome VARCHAR(45)
);
create table crawlerSecao(
idSecao int PRIMARY KEY identity(1, 1),
nome VARCHAR(45),
fkSecao int,
fkComponente int,
foreign key (fkSecao) references crawlerSecao(idSecao),
foreign key (fkComponente) references crawlerComponente(idComponente)
);
create table crawlerLeitura(
idLeitura int PRIMARY KEY identity(1, 1),
nome VARCHAR(45),
minimo DECIMAL(9,3),
valor DECIMAL(9,3),
maximo DECIMAL(9,3),
horario time(0),
dia date,
fkSecao int,
foreign key (fkSecao) references crawlerSecao(idSecao)
);