CREATE TABLE metodoEnvio(
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100) NOT NULL,
    costo INT NOT NULL,
    tiempoEntrega INT NOT NULL
);

CREATE TABLE metodoPago(
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100) NOT NULL
);

CREATE TABLE boleta(
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    cantidad INT NOT NULL,
    total INT NOT NULL,
    metodoEnvio_id INT NOT NULL,
    metodoPago_id INT NOT NULL,

    CONSTRAINT fk_boleta_metodoEnvio 
        FOREIGN KEY (metodoEnvio_id) 
        REFERENCES metodoEnvio(id),  

    CONSTRAINT fk_boleta_metodoPago 
        FOREIGN KEY (metodoPago_id) 
        REFERENCES metodoPago(id)
);

CREATE TABLE boletas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    boleta_id INT NOT NULL, 

    CONSTRAINT fk_boletas_boleta 
        FOREIGN KEY (boleta_id) 
        REFERENCES boleta(id)
);