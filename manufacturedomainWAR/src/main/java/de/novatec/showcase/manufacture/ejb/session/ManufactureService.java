package de.novatec.showcase.manufacture.ejb.session;

import java.util.Collection;
import java.util.List;

import de.novatec.showcase.manufacture.ejb.entity.Assembly;
import de.novatec.showcase.manufacture.ejb.entity.Bom;
import de.novatec.showcase.manufacture.ejb.entity.BomPK;
import de.novatec.showcase.manufacture.ejb.entity.Component;
import de.novatec.showcase.manufacture.ejb.entity.ComponentDemand;
import de.novatec.showcase.manufacture.ejb.entity.Inventory;

public interface ManufactureService {
	Component findComponent(String id);

	Collection<Component> getAllComponents();

	Assembly findAssembly(String id);

	Collection<Assembly> getAllAssemblies();

	Collection<String> getAllAssemblyIds();

	Collection<Bom> getAllBoms();

	Inventory getInventory(String compId, Integer location);

	Collection<Inventory> getAllInventories();

	void deliver(List<ComponentDemand> delivery);

	String createComponent(Component component);

	String createAssembly(Assembly assembly);

	String createInventory(Inventory inventory);

	BomPK createBom(Bom bom);

	void addBomToComponent(Integer lineNo, String assemblyId, String componentId);

	Bom findBom(BomPK bomPK);
}
