INSERT INTO products (product_name, model, specification, id_category, id_measurement, created_at)
SELECT p.product_name, p.model, p.specification, c.id, m.id, p.created_at
FROM (
    VALUES
    ('laptop', 'lenovo thinkpad', 'T410', 'tecnologia', 'pieza', NOW())
) AS p(product_name, model, specification, category_name, unit, created_at)
JOIN categories c ON c.category = p.category_name
JOIN measurements m ON m.unit = p.unit;

--DROP FUNCTION IF EXISTS fun_get_suppliers_of_product(INT);

CREATE OR REPLACE FUNCTION fun_get_suppliers_of_product(p_product_id INT)
RETURNS JSONB
AS $$

DECLARE
		v_result JSONB;
BEGIN
		SELECT jsonb_build_object(
				'product_id', p.id,
				'product_name', p.product_name,
				'product_model', p.model,
				'product_specification', p.specification,
				'category', c.category,
				'symbol', m.symbol,
				'suppliers', COALESCE(
					jsonb_agg(
						jsonb_build_object(
							'supplier_name', s.supplier_name,
							'supplier_phone', s.number_phone,
							'supplier_email', s.email,
							'price', ps.price
						)
					) FILTER (WHERE s.id IS NOT NULL),
					'[]'::jsonb
				)
		) INTO v_result
		FROM products p
		JOIN categories c ON p.id_category = c.id
		JOIN measurements m ON p.id_measurement = m.id
		LEFT JOIN products_suppliers ps ON p.id = ps.id_product
		LEFT JOIN suppliers s ON ps.id_supplier = s.id
		WHERE p.id = p_product_id
		GROUP BY p.id, c.category, m.symbol;

	RETURN v_result;

END;
$$ LANGUAGE plpgsql
@@