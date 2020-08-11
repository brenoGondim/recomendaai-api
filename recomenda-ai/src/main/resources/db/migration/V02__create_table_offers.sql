
CREATE TABLE IF NOT EXISTS TBL_OFFERS (
    partner_id VARCHAR(20) NOT NULL ,
    ticket_id VARCHAR(37) PRIMARY KEY,
    document_id VARCHAR(11) NOT NULL,
    response_json json NOT NULL
);

-- INSERT INTO TBL_OFFERS (partner_id, response_json)
-- VALUES ('12510540645', '{"teste": "teste", "teste2":"teste222"}');
