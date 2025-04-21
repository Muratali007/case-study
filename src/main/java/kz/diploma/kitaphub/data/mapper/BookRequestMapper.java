package kz.diploma.kitaphub.data.mapper;


import kz.diploma.kitaphub.data.dto.BookRequestInfoDto;
import kz.diploma.kitaphub.data.entity.BookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookRequestMapper {
  @Mapping(target = "senderUserId", source = "senderUser.id")
  @Mapping(target = "senderUsername", source = "senderUser.username")
  @Mapping(target = "senderImageUrl", source = "senderUser.imageUrl")
  @Mapping(target = "receiverUserId", source = "receiverUser.id")
  @Mapping(target = "receiverUsername", source = "receiverUser.username")
  @Mapping(target = "receiverImageUrl", source = "receiverUser.imageUrl")
  @Mapping(target = "isbn", source = "bookRequest.book.isbn")
  @Mapping(target = "bookName", source = "bookRequest.book.name")
  @Mapping(target = "bookImageUrl", source = "bookRequest.book.imageUrl")
  @Mapping(target = "exchangeIsbn", source = "bookRequest.exchangeBook.isbn")
  @Mapping(target = "exchangeName", source = "bookRequest.exchangeBook.name")
  @Mapping(target = "exchangeImageUrl", source = "bookRequest.exchangeBook.imageUrl")
  BookRequestInfoDto toBookToRequestDto(BookRequest bookRequest);
}
