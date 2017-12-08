package es.uc3m.tiw.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the receipt database table.
 * 
 */
@Entity
@Table(name="receipt")
@NamedQuery(name="Receipt.findAll", query="SELECT r FROM Receipt r")
public class Receipt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date date;

	private BigDecimal total;

	//bi-directional many-to-one association to Event
	@ManyToOne
	@JoinColumn(name="idevent")
	@JsonIgnore
	private Event event;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="iduser")
	@JsonIgnore
	private User user;

	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="receipt")
	@JsonIgnore
	private List<Ticket> tickets;

	public Receipt() {
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

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Ticket addTicket(Ticket ticket) {
		getTickets().add(ticket);
		ticket.setReceipt(this);

		return ticket;
	}

	public Ticket removeTicket(Ticket ticket) {
		getTickets().remove(ticket);
		ticket.setReceipt(null);

		return ticket;
	}

}