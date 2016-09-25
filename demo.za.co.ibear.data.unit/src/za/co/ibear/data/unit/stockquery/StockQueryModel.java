package za.co.ibear.data.unit.stockquery;

import za.co.ibear.property.model.PropertyModel;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "stockquery")
public class StockQueryModel extends PropertyModel {

	private String product = null;
	private String description = null;

	public StockQueryModel() {
		setProduct("");
		setDescription("");
	}


	public void setProduct(String product) {
		firePropertyChange("product", this.product, this.product = product);
	}

	public String getProduct() {
		return this.product;
	}

	public void setDescription(String description) {
		firePropertyChange("description", this.description, this.description = description);
	}

	public String getDescription() {
		return this.description;
	}


}