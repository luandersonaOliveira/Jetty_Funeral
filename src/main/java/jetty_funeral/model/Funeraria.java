package jetty_funeral.model;

public class Funeraria {
	private int id;
	private String companyName;
	private String description;

	public Funeraria() {
	}

	public Funeraria(String companyName, String description) {
		this.companyName = companyName;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
