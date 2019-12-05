package de.novatec.showcase.manufacture.dto;

import java.io.Serializable;
import java.util.Objects;

public class InventoryPK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String componentId;
	private Integer location;
	
	public InventoryPK(){
		
	}

	public InventoryPK(String componentId, Integer location) {
		super();
		this.componentId = componentId;
		this.location = location;
	}
	
	public InventoryPK(InventoryPK pk){
		super();
		this.componentId = pk.componentId;
		this.location = pk.location;
	}

	public String getComponentId() {
		return componentId;
	}

	public Integer getLocation() {
		return location;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		return Objects.hash(componentId, location);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof InventoryPK)) {
			return false;
		}
		InventoryPK other = (InventoryPK) obj;
		return Objects.equals(componentId, other.componentId) && Objects.equals(location, other.location);
	}

	@Override
	public String toString() {
		return "InventoryPK [componentId=" + componentId + ", location=" + location + "]";
	}
}
