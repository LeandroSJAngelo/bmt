CREATE TABLE scheduler (
    id BIGSERIAL not null PRIMARY KEY,
    departure_data TIMESTAMP,
    departure_local VARCHAR(100),
    arrival_data TIMESTAMP,
    arrival_local VARCHAR(100),
    voyage VARCHAR(100),
    vessel_voyage VARCHAR(100),
    container_gate_in TIMESTAMP,
    shipping_instructions TIMESTAMP,
    shipping_instructions_ams TIMESTAMP,
    verified_gross_mass TIMESTAMP,
    imo_number VARCHAR(100),
    call_sign VARCHAR(100),
    vessel_voyage_code VARCHAR(100)
);
