CREATE TABLE transactions(
  PosBalanceBefore             NUMERIC(8,2)
  ,apiClientLogin              VARCHAR(30)
  ,balanceAfter                NUMERIC(8,2)
  ,balanceBefore               NUMERIC(8,2)
  ,cardNumbers                 VARCHAR(255) DEFAULT ''
  ,certificateBlockReason      VARCHAR(100)
  ,certificateCounteragent     VARCHAR(50)
  ,certificateCounteragentType VARCHAR(30)
  ,certificateEmitentName      VARCHAR(50)
  ,certificateNominal          NUMERIC(8,2)
  ,certificateNumber           VARCHAR(30)
  ,certificateStatus           VARCHAR(30) NOT NULL
  ,certificateType             VARCHAR(30)
  ,comment                     VARCHAR(255)
  ,couponNumber                VARCHAR(30)
  ,couponSeries                VARCHAR(40)
  ,iikoBizUser                 VARCHAR(50)
  ,marketingCampaignName       VARCHAR(50)
  ,networkId                   UUID NOT NULL
  ,orderCreateDate             TIMESTAMP
  ,orderNumber                 VARCHAR(30)
  ,orderSum                    NUMERIC(8,2) NOT NULL
  ,organizationId              UUID
  ,phoneNumber                 VARCHAR(16)
  ,programName                 VARCHAR(64)
  ,transactionCreateDate       TIMESTAMP NOT NULL
  ,transactionSum              NUMERIC(8,2)
  ,transactionType             VARCHAR(30) NOT NULL
  ,PRIMARY KEY(phoneNumber,transactionCreateDate,transactionType)
);
CREATE INDEX idx_transactions_date_desc ON transactions (transactionCreateDate DESC);
