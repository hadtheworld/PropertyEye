package com.propertyEye.propertySystem.entities;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Property {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long propId;
	
	@Column(nullable=false)
	private String propName;
	
	
	@Column(columnDefinition="TEXT")
	private String propDescription;
	
	@Column(nullable=false)
	private String propLocation;
	
	
	@ManyToOne(fetch= FetchType.LAZY, optional=false)
	@JoinColumn(name="seller_id")
	private Seller seller;
	
	
	@ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(
        name = "property_buyer",
        joinColumns = @JoinColumn(name = "prop_id"),
        inverseJoinColumns = @JoinColumn(name = "buyer_id")
    )
	private Set<Users> buyers = new LinkedHashSet<Users>();

	
	
	public Property() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Property(long propId, String propName, String propDescription, String propLocation) {
		super();
		this.propId = propId;
		this.propName = propName;
		this.propDescription = propDescription;
		this.propLocation = propLocation;
	}

	public long getPropId() {
		return propId;
	}

	public void setPropId(long propId) {
		this.propId = propId;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropDescription() {
		return propDescription;
	}

	public void setPropDescription(String propDescription) {
		this.propDescription = propDescription;
	}

	public String getPropLocation() {
		return propLocation;
	}

	public void setPropLocation(String propLocation) {
		this.propLocation = propLocation;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Set<Users> getBuyers() {
		return buyers;
	}

	public void setBuyers(Set<Users> buyers) {
		this.buyers = buyers;
	}
	 public void addBuyer(Users buyer) {
		 buyer.getInterestedProperty().add(this);
		    this.buyers.add(buyer);
		    
	}
	 public void removeBuyer(long buyerId) {
		    Users buyer = this.buyers.stream().filter(t -> t.getUserId() == buyerId).findFirst().orElse(null);
		    if (buyer != null) {
		      this.buyers.remove(buyer);
		      buyer.getInterestedProperty().remove(this);
		    }
		  }
}
