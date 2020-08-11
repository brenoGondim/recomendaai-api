  CREATE TABLE IF NOT EXISTS TBL_API (
    ticket_id VARCHAR(37) PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    url  VARCHAR(100) NOT NULL,
    request_json json NOT NULL,
    response_json json NOT NULL
);


-- INSERT INTO TBL_API (partner_id, name, url, request_json, response_json)
-- VALUES ('12510540645', 'Api-de-teste', 'https://apiteste.com.br/api/', '{"estate": "NY", "city":"NY"}', '{}');
