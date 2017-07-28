package in.techpinnacle.andriod.eChallan.pojos;

import java.io.Serializable;


public class WUser implements Serializable, Comparable<WUser>{

	private String wId;
	private String pwd;

	private Role role;
	
	public WUser() {
	}

	public WUser(String wId, String pwd, Role role) {
		this.wId = wId;
		this.pwd = pwd;
		this.role = role;
	}

	public String getwId() {
		return wId;
	}

	public void setwId(String wId) {
		this.wId = wId;
	}


	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	@Override
	public int compareTo(WUser wuser) {
		return (this.wId).compareTo(wuser.wId);
	}
}
