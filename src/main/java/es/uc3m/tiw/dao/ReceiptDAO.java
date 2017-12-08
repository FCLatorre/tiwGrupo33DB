package es.uc3m.tiw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.uc3m.tiw.entities.Receipt;


public interface ReceiptDAO extends CrudRepository<Receipt, Long>{
	public List<Receipt> findAll();
	public List<Receipt> findByUserId(Long userid);
	public Receipt findByIdAndUserId(Long id, Long userid);
}
