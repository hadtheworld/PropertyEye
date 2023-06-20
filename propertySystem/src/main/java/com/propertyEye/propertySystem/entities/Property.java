package com.propertyEye.propertySystem.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private String propName;
	private String propDescription;
	private String propLocation;
	
	@JsonIgnore
	@ManyToOne(fetch= FetchType.LAZY, optional=false)
	@JoinColumn(name="seller_id")
	private Seller seller;
	
	
	@ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(
        name = "property_buyer",
        joinColumns = @JoinColumn(name = "prop_id"),
        inverseJoinColumns = @JoinColumn(name = "buyer_id")
    )
	private List<Users> buyers = new ArrayList<Users>();

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

	public List<Users> getBuyers() {
		return buyers;
	}

	public void setBuyers(List<Users> buyers) {
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
