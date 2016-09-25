package za.co.ibear.data.unit.categoryquery;

import javax.xml.bind.annotation.XmlRootElement;

import za.co.ibear.property.model.PropertyModel;


@XmlRootElement(name = "categoryquery")
public class CategoryQueryModel extends PropertyModel {

	private String productcategory = null;
	private String description = null;
	private String name = null;

	public CategoryQueryModel() {
		setProductCategory("");
		setDescription("");
		setName("");
	}


	public void setProductCategory(String productcategory) {
		firePropertyChange("productcategory", this.productcategory, this.productcategory = productcategory);
	}

	public String getProductCategory() {
		return this.productcategory;
	}

	public void setDescription(String description) {
		firePropertyChange("description", this.description, this.description = description);
	}

	public String getDescription() {
		return this.description;
	}

	public void setName(String name) {
		firePropertyChange("name", this.name, this.name = name);
	}

	public String getName() {
		return this.name;
	}


}