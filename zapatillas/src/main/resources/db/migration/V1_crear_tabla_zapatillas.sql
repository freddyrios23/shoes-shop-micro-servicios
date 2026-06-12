CREATE TABLE marca(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL
);

CREATE TABLE sexo(
    id INT AUTO_INCREMENT PRIMARY KEY,
    genero VARCHAR(100) NOT NULL
);

CREATE TABLE color(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL
);

CREATE TABLE tipo(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE material(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL
);

CREATE TABLE zapatilla(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio INT NOT NULL,
    marca_id INT,
    sexo_id INT,

    CONSTRAINT fk_zapatilla_marca
        FOREIGN KEY (marca_id)
        REFERENCES marca(id),

    CONSTRAINT fk_zapatilla_sexo
        FOREIGN KEY (sexo_id)
        REFERENCES sexo(id)
);

CREATE TABLE colores(
    id INT AUTO_INCREMENT PRIMARY KEY,

    zapatilla_id INT,
    color_id INT,

    CONSTRAINT fk_colores_zapatilla
        FOREIGN KEY (zapatilla_id)
        REFERENCES zapatilla(id),

    CONSTRAINT fk_colores_color
        FOREIGN KEY (color_id)
        REFERENCES color(id)
);

CREATE TABLE tipos(
    id INT AUTO_INCREMENT PRIMARY KEY,

    zapatilla_id INT,
    tipo_id INT,

    CONSTRAINT fk_tipos_zapatilla
        FOREIGN KEY (zapatilla_id)
        REFERENCES zapatilla(id),

    CONSTRAINT fk_tipos_tipo
        FOREIGN KEY (tipo_id)
        REFERENCES tipo(id)
);

CREATE TABLE materiales(
    id INT AUTO_INCREMENT PRIMARY KEY,

    zapatilla_id INT,
    material_id INT,

    CONSTRAINT fk_materiales_zapatilla
        FOREIGN KEY (zapatilla_id)
        REFERENCES zapatilla(id),

    CONSTRAINT fk_materiales_material
        FOREIGN KEY (material_id)
        REFERENCES material(id)
);