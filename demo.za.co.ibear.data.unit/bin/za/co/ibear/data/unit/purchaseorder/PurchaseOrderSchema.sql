-- DROP TABLE "PurchaseOrder";

CREATE TABLE "PurchaseOrder" (
	"UnitSequence" INT,
	"PurchaseOrder" VARCHAR(30),
	"Supplier" VARCHAR(30),
	"Name" VARCHAR(70),
	"User" VARCHAR(30),
	"TimeCreated" TIMESTAMP);

CREATE UNIQUE INDEX "i_PurchaseOrder_01" ON "PurchaseOrder" ("PurchaseOrder");
CREATE UNIQUE INDEX "i_PurchaseOrder_00" ON "PurchaseOrder" ("UnitSequence");

ALTER TABLE "PurchaseOrder" ADD CONSTRAINT "pk_PurchaseOrder_00" PRIMARY KEY ("PurchaseOrder");

