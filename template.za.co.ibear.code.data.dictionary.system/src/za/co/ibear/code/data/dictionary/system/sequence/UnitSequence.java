package za.co.ibear.code.data.dictionary.system.sequence;

import java.util.LinkedHashSet;

import za.co.ibear.code.data.dictionary.system.field.Field;
import za.co.ibear.code.data.dictionary.system.field.SystemFields;
import za.co.ibear.code.data.dictionary.system.index.Index;
import za.co.ibear.code.data.dictionary.system.index.IndexConstant;
import za.co.ibear.code.data.dictionary.system.primary.key.PrimaryKey;
import za.co.ibear.code.data.dictionary.system.schema.SchemaConstant;
import za.co.ibear.code.data.dictionary.system.unit.Unit;

public class UnitSequence extends Unit {

	public UnitSequence() {
		super(SchemaConstant.SYSTEM,false);
		FIELD = new LinkedHashSet<Field>();

		FIELD.add(SystemFields.UNIT_NAME);
		FIELD.add(SystemFields.NEXT_SEQUENCE);

		INDEX.add(new Index(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"00",SystemFields.UNIT_NAME));

		PRIMARY_KEY.add(new PrimaryKey(this.NAME,this.FULL_NAME,IndexConstant.UNIQUE,"00",SystemFields.UNIT_NAME));

	}
	
	protected static void p(String v) {
		System.out.println("UnitSequence:) " + v);
	}

}
