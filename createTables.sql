CREATE TABLE "App"
(
    id integer NOT NULL DEFAULT nextval('"App_id_seq"'::regclass),
    name character varying(20)  NOT NULL,
    CONSTRAINT "App_pkey" PRIMARY KEY (id)
);

CREATE TABLE "Level"
(
    id integer NOT NULL DEFAULT nextval('"Level_id_seq"'::regclass),
    name character varying(20)  NOT NULL,
    CONSTRAINT "Level_pkey" PRIMARY KEY (id)
);

CREATE TABLE "Log"
(
    id integer NOT NULL DEFAULT nextval('"Log_id_seq"'::regclass),
    level_id bigint NOT NULL,
    app_id bigint NOT NULL,
    message character varying ,
    class character varying  NOT NULL,
    thread character varying NOT NULL,
    date timestamp without time zone,
    CONSTRAINT "Log_pkey" PRIMARY KEY (id),
    CONSTRAINT app_id FOREIGN KEY (app_id)
        REFERENCES "App" (id) MATCH SIMPLE,
    CONSTRAINT level_id FOREIGN KEY (level_id)
        REFERENCES "Level" (id) MATCH SIMPLE
);