package entity.impl;
/**
 * @author Ye xiuyun
 *
 */
import java.util.Date;

import entity.api.Customer;

public class CustomerImpl implements Customer {
	private int Id;
	private String name;
	private String password;
	private Boolean sex;//is male
	private Date birthday;
	private int weight;
	private int height;
	public CustomerImpl() {
		
	}
	public CustomerImpl(String name,String password,Boolean sex,Date birthday,int height,int weight) {
		this.name=name;
		this.password=password;
		this.sex=sex;
		this.birthday=birthday;
		this.weight=weight;
		this.height=height;
	}
	@Override
	public int getId() {
		return Id;
	}

	@Override
	public void setId(int id) {
		this.Id=id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}

	@Override
	public String getPassword() {		
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password=password;
	}

	@Override
	public boolean getSex() {
		return sex;
	}

	@Override
	public void setSex(Boolean sex) {
		this.sex=sex;
	}

	@Override
	public Date getBirthday() {
		return birthday;
	}

	@Override
	public void setBirthday(Date birthday) {
		this.birthday=birthday;

	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public void setWeight(int weight) {
		this.weight=weight;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height=height;
	}

}
