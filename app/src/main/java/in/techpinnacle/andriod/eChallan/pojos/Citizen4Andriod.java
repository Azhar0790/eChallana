package in.techpinnacle.andriod.eChallan.pojos;

import java.io.Serializable;

public class Citizen4Andriod implements Serializable{

	private String license;		
	private String name;	
	private String address;
	private String mailId;
	
	
	public Citizen4Andriod() {
		super();
	}
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

}
