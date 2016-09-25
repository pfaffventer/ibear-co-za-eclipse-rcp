-- DROP TABLE "ProductCategory";

CREATE TABLE "ProductCategory" (
	"UnitSequence" INT,
	"ProductCategory" VARCHAR(30),
	"Description" VARCHAR(70));

CREATE UNIQUE INDEX "i_ProductCategory_01" ON "ProductCategory" ("ProductCategory");
CREATE UNIQUE INDEX "i_ProductCategory_00" ON "ProductCategory" ("UnitSequence");

ALTER TABLE "ProductCategory" ADD CONSTRAINT "pk_ProductCategory_00" PRIMARY KEY ("ProductCategory");

