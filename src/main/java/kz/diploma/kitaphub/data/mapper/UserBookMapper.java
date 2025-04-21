package kz.diploma.kitaphub.data.mapper;

import kz.diploma.kitaphub.data.dto.UserBookDto;
import kz.diploma.kitaphub.data.entity.UserBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserBookMapper {
  @Mapping(target = "author", source = "userBook.book.author.fullName")
  @Mapping(target = "genre", source = "userBook.book.genre.rusName")
  @Mapping(target = "name", source = "userBook.book.name")
  @Mapping(target = "imageUrl", source = "userBook.book.imageUrl")
  @Mapping(target = "isbn", source = "userBook.book.isbn")
  @Mapping(target = "username", source = "userBook.user.username")
  @Mapping(target = "userImageUrl", source = "userBook.user.imageUrl")
  @Mapping(target = "userId", source = "userBook.user.id")
  UserBookDto toUserBookDto(UserBook userBook);
}
