package kz.diploma.kitaphub.data.mapper;

import kz.diploma.kitaphub.data.dto.SaleBookDto;
import kz.diploma.kitaphub.data.entity.SaleBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleBookMapper {
  @Mapping(target = "author", source = "saleBook.book.author.fullName")
  @Mapping(target = "name", source = "saleBook.book.name")
  @Mapping(target = "bookImageUrl", source = "saleBook.book.imageUrl")
  @Mapping(target = "isbn", source = "saleBook.book.isbn")
  @Mapping(target = "username", source = "saleBook.user.username")
  @Mapping(target = "userImageUrl", source = "saleBook.user.imageUrl")
  @Mapping(target = "userId", source = "saleBook.user.id")
  SaleBookDto toSaleBookDto(SaleBook saleBook);
}
