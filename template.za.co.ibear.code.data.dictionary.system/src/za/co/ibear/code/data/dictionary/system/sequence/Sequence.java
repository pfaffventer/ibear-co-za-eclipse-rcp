package za.co.ibear.code.data.dictionary.system.sequence;

public class Sequence {

	private String name = null;
	private int sequence = 0;

	public Sequence(String name, int sequence) {
		super();
		this.name = name;
		this.sequence = sequence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return "Sequence [name=" + name + ", sequence=" + sequence + "]";
	}

}
