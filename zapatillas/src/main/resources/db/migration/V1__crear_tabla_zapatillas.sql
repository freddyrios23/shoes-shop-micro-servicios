CREATE TABLE marcas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL
);

CREATE TABLE sexos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    genero VARCHAR(100) NOT NULL
);

CREATE TABLE color(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL
);

CREATE TABLE tipos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE material(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL
);

CREATE TABLE zapatillas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio INT NOT NULL,
    marca_id INT,
    sexo_id INT,

    CONSTRAINT fk_zapatillas_marcas
        FOREIGN KEY (marca_id)
        REFERENCES marcas(id),

    CONSTRAINT fk_zapatillas_sexos
        FOREIGN KEY (sexo_id)
        REFERENCES sexos(id)
);

CREATE TABLE colores_zapatillas(
    id INT AUTO_INCREMENT PRIMARY KEY,

    zapatilla_id INT,
    color_id INT,

    CONSTRAINT fk_colores_zapatillas
        FOREIGN KEY (zapatilla_id)
        REFERENCES zapatillas(id),

    CONSTRAINT fk_colores_color
        FOREIGN KEY (color_id)
        REFERENCES color(id)
);

CREATE TABLE tipos_zapatillas(
    id INT AUTO_INCREMENT PRIMARY KEY,

    zapatilla_id INT,
    tipo_id INT,

    CONSTRAINT fk_tipos_zapatillas
        FOREIGN KEY (zapatilla_id)
        REFERENCES zapatillas(id),

    CONSTRAINT fk_tipos_tipo
        FOREIGN KEY (tipo_id)
        REFERENCES tipos(id)
);

CREATE TABLE materiales_zapatillas(
    id INT AUTO_INCREMENT PRIMARY KEY,

    zapatilla_id INT,
    material_id INT,

    CONSTRAINT fk_materiales_zapatillas
        FOREIGN KEY (zapatilla_id)
        REFERENCES zapatillas(id),

    CONSTRAINT fk_materiales_material
        FOREIGN KEY (material_id)
        REFERENCES material(id)
);

INSERT INTO marcas(nombre) VALUES
('Nike'),
('Adidas'),
('Puma');

INSERT INTO sexos(genero) VALUES
('Hombre'),
('Mujer');

INSERT INTO color(nombre) VALUES
('Negro'),
('Blanco'),
('Rojo');

INSERT INTO tipos(nombre) VALUES
('Running'),
('Basketball'),
('Casual');

INSERT INTO material(nombre) VALUES
('Cuero'),
('Malla'),
('Sintetico');

INSERT INTO zapatillas(nombre, precio, marca_id, sexo_id)
VALUES
('Nike Air Max', 89990, 1, 1),
('Adidas Superstar', 79990, 2, 2);

INSERT INTO colores_zapatillas(zapatilla_id, color_id)
VALUES
(1,1),
(1,2),
(2,3);

INSERT INTO tipos_zapatillas(zapatilla_id, tipo_id)
VALUES
(1,1),
(2,3);

INSERT INTO materiales_zapatillas(zapatilla_id, material_id)
VALUES
(1,2),
(2,1);