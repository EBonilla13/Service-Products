INSERT INTO categories (category, created_at)
VALUES ('cosmeticos', NOW()), ('ropa', NOW()), ('tecnologia', NOW()), ('limpieza', NOW()), ('repuesto maquinaria', NOW());

INSERT INTO measurements (unit, symbol, created_at)
VALUES ('kilogramo', 'kg', NOW()), ('tonelada', 'ton', NOW()), ('pieza', 'pza', NOW()), ('set', 'set', NOW()), ('litro', 'l', NOW());

INSERT INTO products (product_name, model, specification, id_category, id_measurement, created_at)
SELECT p.product_name, p.model, p.specification, c.id, m.id, p.created_at
FROM (
    VALUES
    ('lavadora', 'mabe', '2 toneladas', 'tecnologia', 'pieza', NOW()),
    ('celular', 'iphone 14', '16 gb', 'tecnologia', 'pieza', NOW()),
    ('trapeador', 'clean', null, 'limpieza', 'pieza', NOW()),
    ('escoba', 'madera', '12 pulgadas', 'limpieza', 'pieza', NOW()),
    ('tornillo', 'milimetrico', '12 mm', 'repuesto maquinaria', 'pieza', NOW()),
    ('playera', 'lindura', 'mediana', 'ropa', 'pieza', NOW())
) AS p(product_name, model, specification, category_name, unit, created_at)
JOIN categories c ON c.category = p.category_name
JOIN measurements m ON m.unit = p.unit;

INSERT INTO suppliers (supplier_name, number_phone, email, created_at)
VALUES
('elektra', '8121383910', 'nl-elektra@gmail.com', NOW()),
('limpiatodo', '8145656960', 'limpiatdo@gmail.com', NOW()),
('elizondo', '8456344562', 'nl-elizondo@gmail.com', NOW()),
('refacciones martinez', '8111343312', 'ref_mtz@gmail.com', NOW());

INSERT INTO products_suppliers (price, id_product, id_supplier, created_at)
SELECT ps.price::numeric, p.id, s.id, ps.created_at
FROM (
    VALUES
        (5499.49, 'lavadora','mabe', 'elektra', NOW()),
        (4999.99, 'lavadora','mabe', 'elizondo', NOW()),
        (176.89, 'escoba','madera', 'limpiatodo', NOW()),
        (249.00, 'trapeador','clean', 'limpiatodo', NOW()),
        (49.99, 'tornillo','milimetrico', 'refacciones martinez', NOW()),
        (8499.49, 'celular','iphone 14', 'elektra', NOW()),
        (7899.49, 'celular','iphone 14', 'elizondo', NOW()),
        (278.00, 'playera','lindura', 'elizondo', NOW())
) AS ps(price, product_name, model, supplier_name, created_at)
JOIN products p ON p.product_name = ps.product_name AND p.model = ps.model
JOIN suppliers s ON s.supplier_name = ps.supplier_name;