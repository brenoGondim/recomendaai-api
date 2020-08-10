
CREATE TABLE IF NOT EXISTS TBL_STATUS (
    partner_id VARCHAR(11) PRIMARY KEY,
    description VARCHAR(20) NOT NULL
);

INSERT INTO TBL_STATUS (partner_id, description)
VALUES ('0', 'TESTE');
