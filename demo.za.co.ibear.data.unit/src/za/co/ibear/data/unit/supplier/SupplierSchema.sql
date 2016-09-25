-- DROP TABLE "Supplier";

CREATE TABLE "Supplier" (
	"UnitSequence" INT,
	"Supplier" VARCHAR(30),
	"Name" VARCHAR(70),
	"Address1" VARCHAR(50),
	"Address2" VARCHAR(50),
	"Address3" VARCHAR(50),
	"Address4" VARCHAR(50),
	"Address5" VARCHAR(50),
	"User" VARCHAR(30),
	"TimeCreated" TIMESTAMP);

CREATE UNIQUE INDEX "i_Supplier_01" ON "Supplier" ("Supplier");
CREATE UNIQUE INDEX "i_Supplier_00" ON "Supplier" ("UnitSequence");

ALTER TABLE "Supplier" ADD CONSTRAINT "pk_Supplier_00" PRIMARY KEY ("Supplier");

