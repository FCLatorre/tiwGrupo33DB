package es.uc3m.tiw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.uc3m.tiw.entities.Ticket;


public interface TicketDAO extends CrudRepository<Ticket, Long>{
	public List<Ticket> findAll();
	public List<Ticket> findByReceiptIdAndReceiptUserId(Long id, Long userid);
	public List<Ticket> findByReceiptUserId(Long id);
}
