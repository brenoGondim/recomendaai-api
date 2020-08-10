
CREATE TABLE IF NOT EXISTS TBL_OFFERS (
    partner_id VARCHAR(11) PRIMARY KEY,
    response_json json NOT NULL
);

INSERT INTO TBL_OFFERS (partner_id, response_json)
VALUES ('12510540645', '{"teste": "teste", "teste2":"teste222"}');
