-- DROP TABLE"UnitSequence";

CREATE TABLE"UnitSequence" (
	"UnitName" VARCHAR(70),
	"NextSequence" INT);

CREATE UNIQUE INDEX "i_UnitSequence_00" ON"UnitSequence" ("UnitName");

ALTER TABLE"UnitSequence" ADD CONSTRAINT "pk_UnitSequence_00" PRIMARY KEY ("UnitName");

