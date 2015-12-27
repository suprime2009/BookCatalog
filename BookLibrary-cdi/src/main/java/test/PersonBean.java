package test;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.richfaces.component.SortOrder;
 
import com.google.common.collect.Maps;

@ManagedBean
@SessionScoped
public class PersonBean implements Serializable {
    private static final long serialVersionUID = -5156711102367948040L;
 
    private static final class PersonDataModel extends JPADataModel<Person> {
        private PersonDataModel(EntityManager entityManager) {
            super(entityManager, Person.class);
        }
 
        @Override
        protected Object getId(Person t) {
            return t.getId();
        }
    }
 
    private Map<String, SortOrder> sortOrders = Maps.newHashMapWithExpectedSize(1);
    private Map<String, String> filterValues = Maps.newHashMap();
    private String sortProperty;
 
    public PersonBean() {
    	System.out.println("PersonBean CONSTRUCTOR");
        sortOrders.put("name", SortOrder.unsorted);
        sortOrders.put("surname", SortOrder.unsorted);
        sortOrders.put("email", SortOrder.unsorted);
    }
 
    @PersistenceContext(unitName = "primary")
	private EntityManager entityManager;
 
    public Map<String, SortOrder> getSortOrders() {
    	System.out.println("PersonBean getSortOrders");
        return sortOrders;
    }
 
    public Map<String, String> getFilterValues() {
    	System.out.println("PersonBean getFilterValues");
        return filterValues;
    }
 
    public String getSortProperty() {
    	System.out.println("PersonBean getSortProperty" + sortProperty);
        return sortProperty;
    }
 
    public void setSortProperty(String sortPropety) {
    	System.out.println("PersonBean setSortProperty");
        this.sortProperty = sortPropety;
    }
 
    public void toggleSort() {
        for (Entry<String, SortOrder> entry : sortOrders.entrySet()) {
            SortOrder newOrder;
 
            if (entry.getKey().equals(sortProperty)) {
                if (entry.getValue() == SortOrder.ascending) {
                    newOrder = SortOrder.descending;
                } else {
                    newOrder = SortOrder.ascending;
                }
            } else {
                newOrder = SortOrder.unsorted;
            }
 
            entry.setValue(newOrder);
        }
    }
 
    public Object getDataModel() {
    	System.out.println("PersonBean getDataModel");
        return new PersonDataModel(entityManager);
    }
}