package entity.api;

import java.math.BigDecimal;

public interface Food {
	public int getId();
	//public void setId(int id);
	
	public String getName();
	//public void setName();
	
	public BigDecimal getPrice();
	//public void setPrice(BigDecimal price);
	
	public String getDescription();
	//public void 
	public String getKind();
	public void setId(int id);
	public void setName(String name);
	public void setPrice(BigDecimal price);
	public void setDescription(String description);

}
