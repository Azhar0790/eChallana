package in.techpinnacle.andriod.eChallan.pojos;

import java.util.Date;

public class Police extends WUser {

	private String name;
	private String dob;
	private String doj;
	
	public Police() {
		super();
	}

	public Police(String wId, String pwd, Role role, String name, String dob,
			String doj) {
		super(wId, pwd, role);
		this.name = name;
		this.dob = dob;
		this.doj = doj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	
}
