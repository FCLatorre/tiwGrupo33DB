package es.uc3m.tiw.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the event database table.
 * 
 */
@Entity
@Table(name="event")
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String description;

	@Lob
	private byte[] image;

	private String location;

	private BigDecimal price;

	private String room;

	private int tickets;

	private Time time;

	private String title;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category")
	//@JsonManagedReference
	@JsonIgnore
	private Category categoryBean;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="iduser")
	//@JsonBackReference
	@JsonIgnore
	private User user;

	//bi-directional many-to-one association to Receipt
	@OneToMany(mappedBy="event")
	@JsonIgnore
	private List<Receipt> receipts;

	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="event")
	@JsonIgnore
	private List<Ticket> ticketsSet;

	public Event() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRoom() {
		return this.room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getTickets() {
		return this.tickets;
	}

	public void setTickets(int tickets) {
		this.tickets = tickets;
	}

	public Time getTime() {
		return this.time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategoryBean() {
		return this.categoryBean;
	}

	public void setCategoryBean(Category categoryBean) {
		this.categoryBean = categoryBean;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Receipt> getReceipts() {
		return this.receipts;
	}

	public void setReceipts(List<Receipt> receipts) {
		this.receipts = receipts;
	}

	public Receipt addReceipt(Receipt receipt) {
		getReceipts().add(receipt);
		receipt.setEvent(this);

		return receipt;
	}

	public Receipt removeReceipt(Receipt receipt) {
		getReceipts().remove(receipt);
		receipt.setEvent(null);

		return receipt;
	}

	public List<Ticket> getTicketsSet() {
		return this.ticketsSet;
	}

	public void setTicketsSet(List<Ticket> ticketsSet) {
		this.ticketsSet = ticketsSet;
	}

	public Ticket addTicketsSet(Ticket ticketsSet) {
		getTicketsSet().add(ticketsSet);
		ticketsSet.setEvent(this);

		return ticketsSet;
	}

	public Ticket removeTicketsSet(Ticket ticketsSet) {
		getTicketsSet().remove(ticketsSet);
		ticketsSet.setEvent(null);

		return ticketsSet;
	}

}