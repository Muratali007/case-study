package kz.diploma.kitaphub.service;

import java.util.List;
import kz.diploma.kitaphub.data.dto.BookCompilationDto;
import kz.diploma.kitaphub.data.mapper.BookCompilationMapper;
import kz.diploma.kitaphub.data.repository.BookCompilationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CompilationService {
  private final BookCompilationRepository bookCompilationRepository;
  private final BookCompilationMapper bookCompilationMapper;

  public CompilationService(BookCompilationRepository bookCompilationRepository,
                              BookCompilationMapper bookCompilationMapper) {
    this.bookCompilationRepository = bookCompilationRepository;
    this.bookCompilationMapper = bookCompilationMapper;
  }

  public List<BookCompilationDto> getAllBookCompilations() {
    return bookCompilationRepository.findAll().stream().map(
            bookCompilationMapper::toBookCompilationDto).toList();
  }

  public BookCompilationDto getBookCompilationById(Long id) {
    return bookCompilationMapper.toBookCompilationDto(
              bookCompilationRepository.findById(id).orElseThrow());
  }
}
