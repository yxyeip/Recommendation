package entity.impl;

import entity.api.Guest;

public class GuestImpl implements Guest {
	private String guest;
	private String master;
	private boolean state;
	/*public GuestImpl(){};*/
	public GuestImpl(String master,String guest,boolean state) {
		this.master=master;
		this.guest=guest;
		this.state=state;
	}
	@Override
	public String getMaster() {

		return master;
	}

	@Override
	public String getGuest() {
		
		return guest;
	}

	@Override
	public boolean getState() {
		return state;
	}
	@Override
	public void setMaster(String master) {
		this.master=master;
		
	}
	@Override
	public void setGuest(String guest) {
		this.guest=guest;
		
	}
	@Override
	public void setState(Boolean state) {
		this.state=state;
		
	}

	

}
