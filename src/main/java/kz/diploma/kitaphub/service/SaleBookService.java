package kz.diploma.kitaphub.service;

import java.util.List;
import java.util.Optional;
import kz.diploma.kitaphub.data.dto.SaleBookDto;
import kz.diploma.kitaphub.data.entity.SaleBook;
import kz.diploma.kitaphub.data.mapper.SaleBookMapper;
import kz.diploma.kitaphub.data.repository.SaleBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class SaleBookService {
  private final SaleBookRepository saleBookRepository;

  private final SaleBookMapper saleBookMapper;

  public SaleBookService(SaleBookRepository saleBookRepository, SaleBookMapper saleBookMapper) {
    this.saleBookRepository = saleBookRepository;
    this.saleBookMapper = saleBookMapper;
  }

  public List<SaleBookDto> getSaleBooks(String username) {
    return saleBookRepository.findAllByUsername(username).stream()
        .map(saleBookMapper::toSaleBookDto).toList();
  }

  public Long addSaleBook(SaleBook saleBook) {
    var created = saleBookRepository.save(saleBook);
    return created.getId();
  }

  public List<SaleBookDto> getBooksForSaleByIbn(String username, String isbn) {
    List<SaleBook> saleBooks = saleBookRepository.findAllByIsbn(username, isbn);
    return saleBooks.stream().map(saleBookMapper::toSaleBookDto).toList();
  }

  public Optional<SaleBookDto> getSaleBookByIdDto(Long id) {
    return saleBookRepository.findById(id).map(saleBookMapper::toSaleBookDto);
  }

  public Optional<SaleBook> getSaleBookById(Long id) {
    return saleBookRepository.findById(id);
  }

  public Long updateSaleBook(SaleBook saleBook) {
    var updated = saleBookRepository.save(saleBook);
    return updated.getId();
  }

  public String deleteSaleBook(Long id) {
    saleBookRepository.deleteById(id);
    return "Book deleted successfully";
  }
}
