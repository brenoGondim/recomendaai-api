  CREATE TABLE IF NOT EXISTS TBL_API_RETURNS (
    id SERIAL PRIMARY KEY NOT NULL,
    api_name VARCHAR(20) NOT NULL,
    api_url  VARCHAR(100) NOT NULL,
    request_parameters TEXT NULL,
    response_json TEXT NOT NULL
);

INSERT INTO TBL_API_RETURNS (api_name, api_url, request_parameters, response_json)
VALUES ('Api-de-teste', 'https://apiteste.com.br/api/', 'id=1, name=joao, body={"estate": "NY", "city":"NY"}', 'id=1, name=joao, body={"estate": "NY", "city":"NY"}');
