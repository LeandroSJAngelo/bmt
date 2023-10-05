CREATE TABLE voyage_details (
    id BIGSERIAL not null PRIMARY KEY,
    departure_data TEXT,
    departure_local TEXT,
    arrival_data TEXT,
    arrival_local TEXT,
    voyage TEXT,
    vessel_voyage TEXT,
    container_gate_in TEXT,
    shipping_instructions TEXT,
    shipping_instructions_ams TEXT,
    verified_gross_mass TEXT,
    imo_number TEXT,
    call_sign TEXT,
    vessel_voyage_code TEXT
);
