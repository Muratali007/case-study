package kz.diploma.kitaphub.data.mapper;

import java.util.List;
import kz.diploma.kitaphub.data.dto.BookCatalogDto;
import kz.diploma.kitaphub.data.dto.BookCompilationDto;
import kz.diploma.kitaphub.data.entity.BookCompilation;
import kz.diploma.kitaphub.data.entity.BookCompilationBook;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface BookCompilationMapper {

  @Mapping(target = "id", source = "bookCompilation.id")
  @Mapping(target = "name", source = "bookCompilation.name")
  @Mapping(target = "description", source = "bookCompilation.description")
  @Mapping(target = "imageUrl", source = "bookCompilation.imageUrl")
  @Mapping(target = "bookIsbns", source = "bookCompilation.bookCompilationBooks", qualifiedByName = "mapBookIsbns")
  @Mapping(target = "books", source = "bookCompilation.bookCompilationBooks")
  BookCompilationDto toBookCompilationDto(BookCompilation bookCompilation);

  @Named("mapBookIsbns")
  default List<String> mapBookIsbns(List<BookCompilationBook> bookCompilationBooks) {
    return bookCompilationBooks.stream()
        .map(bookCompilationBook -> bookCompilationBook.getBook().getIsbn())
        .toList();
  }

  default List<BookCatalogDto> bookCompilationBooksToBookCatalogDto(
      List<BookCompilationBook> bookCompilationBooks) {
    var bookMapper = Mappers.getMapper(BookMapper.class);
    return bookCompilationBooks.stream().map(BookCompilationBook::getBook)
        .map(bookMapper::bookToCatalogDto).toList();
  }
}
