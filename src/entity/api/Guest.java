package entity.api;

public interface Guest {
public String getMaster() ;
public String getGuest();
public boolean getState();
public void setMaster(String master);
public void setGuest(String guest);
public void setState(Boolean state);
}
