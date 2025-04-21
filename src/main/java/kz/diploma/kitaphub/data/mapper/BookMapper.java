package kz.diploma.kitaphub.data.mapper;

import kz.diploma.kitaphub.data.dto.BookCatalogDto;
import kz.diploma.kitaphub.data.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
  @Mapping(target = "author", source = "book.author.fullName")
  @Mapping(target = "price", source = "book.bookLink.marwinCurrentPrice")
  @Mapping(target = "oldPrice", source = "book.bookLink.marwinActualPrice")
  @Mapping(target = "genre", source = "book.genre.rusName")
  @Mapping(target = "language", source = "book.language")
  BookCatalogDto bookToCatalogDto(Book book);
}
