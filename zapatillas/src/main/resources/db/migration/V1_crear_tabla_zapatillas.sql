CREATE TABLE zapatillas(
    idZapatilla INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR2(100) NOT  NULL,
    precio Int NOT NULL,
    marcaId INT NOT NULL,
    sexoId INT NOT NULL

    CONSTRAINT fk_zapatillas_marcas
        FOREIGN KEY (marcaId) 
        REFERENCES marcas(id),
    
    CONSTRAINT fk_zapatillas_sexo
        FOREIGN KEY (sexoId),
        REFERENCES sexo(id)
);

CREATE TABLE tipo (
    idTipo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR2(50) NOT  NULL
);

CREATE TABLE tipos(
    idTipo INT AUTO_INCREMENT PRIMARY KEY

);

CREATE TABLE sexo(

);

CREATE TABLE materiales(

);
