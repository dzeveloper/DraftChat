CREATE SEQUENCE public."User_id_seq"
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 3
CACHE 1;

CREATE SEQUENCE public."Message_id_seq"
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 3
CACHE 1;

CREATE TABLE public."User"
(
  login character varying NOT NULL,
  "passHash" character varying NOT NULL,
  name character varying NOT NULL,
  id integer NOT NULL DEFAULT nextval('"User_id_seq"'::regclass),
  CONSTRAINT "User_pkey" PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public."User"
  OWNER TO app;

CREATE UNIQUE INDEX "idxLogin"
  ON public."User"
  USING btree
  (login COLLATE pg_catalog."default");

CREATE TABLE public."Message"
(
  "timestamp" timestamp without time zone NOT NULL,
  "authorId" integer NOT NULL,
  message character varying NOT NULL,
  id integer NOT NULL DEFAULT nextval('"Message_id_seq"'::regclass),
  CONSTRAINT "Message_pkey" PRIMARY KEY (id),
  CONSTRAINT author FOREIGN KEY ("authorId")
  REFERENCES public."User" (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE public."Message"
  OWNER TO app;