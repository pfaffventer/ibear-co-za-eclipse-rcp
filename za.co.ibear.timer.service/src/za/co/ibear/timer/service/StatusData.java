package za.co.ibear.timer.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StatusData {
	
	private Set<StatusMessage> data = new HashSet<StatusMessage>();

	public void addAll(Set<StatusMessage> records) {
		this.data.addAll(records);
	}
	
	public void add(StatusMessage record) {
		this.data.add(record);
	}

	public void remove(StatusMessage record) {
		this.data.remove(record);
	}

	public Set<StatusMessage> getData() {
		return data;
	}

	public void setData(Set<StatusMessage> data) {
		this.data = data;
	}

	public List<StatusMessage> toArrayList() {
		return new ArrayList<StatusMessage>(data);
	}

}
