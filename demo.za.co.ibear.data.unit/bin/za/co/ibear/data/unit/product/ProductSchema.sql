-- DROP TABLE "Product";

CREATE TABLE "Product" (
	"UnitSequence" INT,
	"Product" VARCHAR(30),
	"Description" VARCHAR(70),
	"ProductCategory" VARCHAR(30),
	"TimeCreated" TIMESTAMP,
	"Volume" FLOAT,
	"User" VARCHAR(30));

CREATE UNIQUE INDEX "i_Product_01" ON "Product" ("Product");
CREATE UNIQUE INDEX "i_Product_00" ON "Product" ("UnitSequence");

ALTER TABLE "Product" ADD CONSTRAINT "pk_Product_01" PRIMARY KEY ("Product");

