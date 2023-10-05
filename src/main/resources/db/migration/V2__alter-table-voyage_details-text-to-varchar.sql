ALTER TABLE voyage_details
    ALTER COLUMN departure_data TYPE VARCHAR(100),
    ALTER COLUMN departure_local TYPE VARCHAR(100),
    ALTER COLUMN arrival_data TYPE VARCHAR(100),
    ALTER COLUMN arrival_local TYPE VARCHAR(100),
    ALTER COLUMN voyage TYPE VARCHAR(100),
    ALTER COLUMN vessel_voyage TYPE VARCHAR(100),
    ALTER COLUMN container_gate_in TYPE VARCHAR(100),
    ALTER COLUMN shipping_instructions TYPE VARCHAR(100),
    ALTER COLUMN shipping_instructions_ams TYPE VARCHAR(100),
    ALTER COLUMN verified_gross_mass TYPE VARCHAR(100),
    ALTER COLUMN imo_number TYPE VARCHAR(100),
    ALTER COLUMN call_sign TYPE VARCHAR(100),
    ALTER COLUMN vessel_voyage_code TYPE VARCHAR(100);
