package com.tyn.jasca.rules;

/**
 * 
 * @author S.J.H.
 */
public class Category implements Comparable<Category> {
	
	public static Category ETC;
	static {
		ETC = new Category("ETC");
		ETC.setName("기타");
		ETC.setOrder(Integer.MAX_VALUE);
	}
	
	private String id;
	private String name;
	private String cwe;
	private String description;
	private int order = Integer.MAX_VALUE - 1;
	
	/** default */ Category(String id) {
		setId(id);
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
	
	public String getCwe() {
		return cwe;
	}
	
	public void setCwe(String cwe) {
		this.cwe = cwe;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	
//	/**
//	 * 
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		return id.hashCode();
//	}
//	
//	/**
//	 * 
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object object) {
//		if (object == this) {
//			return true;
//		}
//		if (object instanceof Category) {
//			Category category = (Category) object;
//			return id.equals(category.id);
//		}
//		return false;
//	}
	
	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id;
	}
	
	/**
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Category category) {
		return order - category.getOrder();
	}
}
