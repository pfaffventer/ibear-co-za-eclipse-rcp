package za.co.ibear.code.data.dictionary.system.edit;

import java.util.Set;

import za.co.ibear.code.data.dictionary.system.unit.Unit;

public class UnitSelector {
	
	private Unit unit = null;
	private Set<String> returnSet = null;

	public UnitSelector(Unit unit,Set<String> returnSet) {
		this.unit = unit;
		this.returnSet = returnSet;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Set<String> getReturnSet() {
		return returnSet;
	}

	public void setReturnSet(Set<String> returnSet) {
		this.returnSet = returnSet;
	}

}
