package in.techpinnacle.andriod.eChallan.pojos;

import java.io.Serializable;
import java.util.Date;

public class Offence4Anriod implements Serializable{
		
	private String vehicleId;
	private String typeOfVehicle;
	private String offenceDesc;
	private double fine;
	private String dateAndTime;
	private boolean isPaid;

	public Offence4Anriod() {
		super();
	}

	public String getVehicleId() {
		return vehicleId;
	}


	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}


	public String getTypeOfVehicle() {
		return typeOfVehicle;
	}


	public void setTypeOfVehicle(String typeOfVehicle) {
		this.typeOfVehicle = typeOfVehicle;
	}


	public String getOffenceDesc() {
		return offenceDesc;
	}


	public void setOffenceDesc(String offenceDesc) {
		this.offenceDesc = offenceDesc;
	}


	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public boolean isPaid(){
		return isPaid;
	}
}
