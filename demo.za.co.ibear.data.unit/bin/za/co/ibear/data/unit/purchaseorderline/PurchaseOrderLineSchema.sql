-- DROP TABLE "PurchaseOrderLine";

CREATE TABLE "PurchaseOrderLine" (
	"UnitSequence" INT,
	"ElementSequence" INT,
	"PurchaseOrderLine" VARCHAR(30),
	"Product" VARCHAR(30),
	"Description" VARCHAR(70),
	"Volume" FLOAT,
	"Price" FLOAT,
	"Total" FLOAT,
	"TimeCreated" TIMESTAMP);

CREATE UNIQUE INDEX "i_PurchaseOrderLine_00" ON "PurchaseOrderLine" ("UnitSequence","ElementSequence");

ALTER TABLE "PurchaseOrderLine" ADD CONSTRAINT "pk_PurchaseOrderLine_01" PRIMARY KEY ("UnitSequence","ElementSequence");

