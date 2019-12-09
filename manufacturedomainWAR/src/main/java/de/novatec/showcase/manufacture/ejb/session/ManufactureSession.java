package de.novatec.showcase.manufacture.ejb.session;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import de.novatec.showcase.manufacture.ejb.entity.Assembly;
import de.novatec.showcase.manufacture.ejb.entity.Bom;
import de.novatec.showcase.manufacture.ejb.entity.BomPK;
import de.novatec.showcase.manufacture.ejb.entity.Component;
import de.novatec.showcase.manufacture.ejb.entity.ComponentDemand;
import de.novatec.showcase.manufacture.ejb.entity.Inventory;
import de.novatec.showcase.manufacture.ejb.entity.InventoryPK;

@Stateless
public class ManufactureSession implements ManufactureSessionLocal {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Component findComponent(String componentId) {
		return em.find(Component.class, componentId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Component> getAllComponents() {
		return em.createNamedQuery("componentQueryAll").getResultList();
	}

	@Override
	public Assembly findAssembly(String componentId) {
		return em.find(Assembly.class, componentId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Assembly> getAllAssemblies() {
		return em.createNamedQuery("assemblyQueryAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> getAllAssemblyIds() {
		return em.createNamedQuery("assemblyQueryAllIds").getResultList();
	}

	@Override
	public Bom findBom(BomPK bomPK) {
		return em.find(Bom.class, bomPK);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Bom> getAllBoms() {
		return em.createNamedQuery("bomQueryAll").getResultList();
	}

	@Override
	public Inventory getInventory(String componentId, Integer location) {
		// PESSIMISTIC_WRITE to avoid an OptimisticLockingException when the same
		// Inventory
		// is accessed by to different SessionBeans, what is not often the case.
		// Its a Workaround to put it in a "get" Method, but this one is only used to
		// change Inventories.
		return em.find(Inventory.class, new InventoryPK(componentId, location), LockModeType.PESSIMISTIC_WRITE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Inventory> getAllInventories() {
		return em.createNamedQuery("inventoryQueryAll").getResultList();
	}

	/**
	 * Inserts delivered components into the Inventory.
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deliver(List<ComponentDemand> componentDemands) {
		for (ComponentDemand componentDemand : componentDemands) {
			Inventory inventory = this.getInventory(componentDemand.getComponentId(), componentDemand.getLocation());
			inventory.addQuantityOnHand(componentDemand.getQuantity());
			// reduce quantity in order
			if (inventory.getQuantityInOrder() - componentDemand.getQuantity() < 0) {
				inventory.reduceQuantityInOrder(inventory.getQuantityInOrder());
			} else {
				inventory.reduceQuantityInOrder(componentDemand.getQuantity());
			}
			inventory.setAccDate(Calendar.getInstance());
			em.flush();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String createComponent(Component component) {
		em.persist(component);
		em.flush();
		return component.getId();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String createAssembly(Assembly assembly) {
		em.persist(assembly);
		em.flush();
		return assembly.getId();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public InventoryPK createInventory(Inventory inventory) {
		if (this.findComponent(inventory.getComponentId()) != null) {
			em.persist(inventory);
			em.flush();
			return inventory.getPk();
		} else {
			return null;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public BomPK createBom(Bom bom) {
		if (this.findComponent(bom.getPk().getComponentId()) != null
				&& this.findAssembly(bom.getPk().getAssemblyId()) != null) {
			em.persist(bom);
			em.flush();
			return bom.getPk();
		} else {
			return null;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addBomToComponent(BomPK bomPK) {
		Component component = this.findComponent(bomPK.getComponentId());
		Assembly assembly = this.findAssembly(bomPK.getAssemblyId());
		Bom bom = this.findBom(bomPK);
		if (component != null && assembly != null && bom != null) {
			component.addComponentBoms(Arrays.asList(bom));
			assembly.addComponent(bom);
			em.flush();
		}
		return;
	}
	
}