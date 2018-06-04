package entity.api;

import java.util.Date;

public interface Customer {
	
	//int Id;
	public long getId();
	public void setId(long Id);
	
	//String name;
	public String getName();
	public void setName(String name);
	
	//String password
	public String getPassword();
	public void setPassword(String password);
	
	//boolean sex;
	public boolean getSex();
	public void setSex(Boolean sex);
	
	//Date birthday;
	public Date getBirthday();
	public void setBirthday(Date birthday);
	
	//int weight;
	public int getWeight();
	public void setWeight(int weight);
	
	//int height;
	public int getHeight();
	public void setHeight(int height);
	
}
