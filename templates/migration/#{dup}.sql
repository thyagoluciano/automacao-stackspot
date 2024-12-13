--liquibase formatted sql

--changeset F002464:1 labels:0.0.5.24
--preconditions onFail:CONTINUE onError:CONTINUE
--comment Script para criação das tabelas da nova arquitetura (dup_517_evt)

SET SEARCH_PATH = dup_canal_api;

CREATE TABLE IF NOT EXISTS dup_#{dup}_evt (
    origem          VARCHAR(100) NOT NULL,
    dh_incl         TIMESTAMP(6),
    dh_atlz         TIMESTAMP(6),
    dh_req          TIMESTAMP(6),    -- QUANDO FOI FEITA A REQUEST
    payload_req     TEXT,            -- PAYLOAD REQUEST
    offset_req      BIGINT,
    particao_req    INT,
    dh_respt        TIMESTAMP(6),    -- QUANDO FOI FEITA A RESPONSE
    payload_respt   TEXT,            -- PAYLOAD RESPONSE
    offset_respt    BIGINT,
    particao_respt  INT,
    id_op           UUID NOT NULL,   -- IDENTIFICADOR DA OPERACAO
    id              UUID NOT NULL DEFAULT gen_random_uuid(), -- GERAÇÃO AUTOMÁTICA DE UUID
    CONSTRAINT pk_dup_#{dup}_evt_id PRIMARY KEY (id)
);

-- rollback drop table dup_canal_api.dup_517_evt;

--changeset F002464:2 labels:0.0.5.24
--preconditions onFail:CONTINUE onError:CONTINUE
--comment Script para criação das tabelas da nova arquitetura (dup_517_op)

SET SEARCH_PATH = dup_canal_api;

CREATE TABLE IF NOT EXISTS dup_#{dup}_op (
    id_op UUID NOT NULL DEFAULT gen_random_uuid(), -- numOp (GERAÇÃO AUTOMÁTICA DE UUID)
    ic_cit_op VARCHAR(255) NULL, -- SITUACAO DA OPERACAO
    payload_respt TEXT NULL,
    errors TEXT NULL,
    dh_incl TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    dh_atlz TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_dup_#{dup}_op_id PRIMARY KEY (id_op)
);

-- rollback drop table dup_canal_api.dup_517_op;
