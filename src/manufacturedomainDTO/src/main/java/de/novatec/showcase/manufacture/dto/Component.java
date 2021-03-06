package de.novatec.showcase.manufacture.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.validation.constraints.Size;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// This is done for the case of the Inventory.getComponent call which could also be an Assembly -> Jockson does not recognize this...
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name="Component", description="POJO that represents a Component.")
public class Component {

	@Size(max = 20)
	private String id;
	@Size(max = 35)
	private String name;
	@Size(max = 100)
	private String description;
	@Size(max = 6)
	private String revision;
	private Integer planner;
	private Integer type;
	private Integer purchased;
	private Integer lomark;
	private Integer himark;

	private Collection<Bom> componentBoms;

	private Integer version;

	public Component() {
		super();
	}

	public Component(String name, String description, String revision, Integer planner, Integer type, Integer purchased,
			Integer lomark, Integer himark) {
		super();
		this.name = name;
		this.description = description;
		this.revision = revision;
		this.planner = planner;
		this.type = type;
		this.purchased = purchased;
		this.lomark = lomark;
		this.himark = himark;
		this.componentBoms = new ArrayList<Bom>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public Integer getPlanner() {
		return planner;
	}

	public void setPlanner(Integer planner) {
		this.planner = planner;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPurchased() {
		return purchased;
	}

	public void setPurchased(Integer purchased) {
		this.purchased = purchased;
	}

	public Integer getLomark() {
		return lomark;
	}

	public void setLomark(Integer lomark) {
		this.lomark = lomark;
	}

	public Integer getHimark() {
		return himark;
	}

	public void setHimark(Integer himark) {
		this.himark = himark;
	}

	public Collection<Bom> getComponentBoms() {
		return componentBoms;
	}

	void addComponentBoms(Collection<Bom> componentBoms) {
		this.componentBoms.addAll(componentBoms);
	}

	public void setComponentBoms(Collection<Bom> componentBoms) {
		this.componentBoms = componentBoms;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getVersion() {
		return this.version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(componentBoms, description, himark, id, lomark, name, planner, purchased, revision, type,
				version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Component)) {
			return false;
		}
		Component other = (Component) obj;
		return Objects.equals(componentBoms, other.componentBoms) && Objects.equals(description, other.description)
				&& Objects.equals(himark, other.himark) && Objects.equals(id, other.id)
				&& Objects.equals(lomark, other.lomark) && Objects.equals(name, other.name)
				&& Objects.equals(planner, other.planner) && Objects.equals(purchased, other.purchased)
				&& Objects.equals(revision, other.revision) && Objects.equals(type, other.type)
				&& version == other.version;
	}

	@Override
	public String toString() {
		return "Component [id=" + id + ", name=" + name + ", description=" + description + ", revision=" + revision
				+ ", planner=" + planner + ", type=" + type + ", purchased=" + purchased + ", lomark=" + lomark
				+ ", himark=" + himark + ", componentBoms=" + componentBoms + ", version=" + version + "]";
	}
}
