package kz.diploma.kitaphub.controller;

import java.util.List;
import kz.diploma.kitaphub.data.dto.BookRequestInfoDto;
import kz.diploma.kitaphub.data.entity.BookRequest;
import kz.diploma.kitaphub.data.entity.BookRequestStatus;
import kz.diploma.kitaphub.data.entity.BookRequestType;
import kz.diploma.kitaphub.service.RequestService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//TODO:change name of controller
@RestController
@RequestMapping("/book/request")
public class RequestController {
  private final RequestService requestService;

  public RequestController(RequestService requestService) {
    this.requestService = requestService;
  }

  @PostMapping("")
  public ResponseEntity<BookRequest> bookRequest(@RequestBody BookRequestDto bookRequest) {
    return ResponseEntity.ok(requestService.bookRequest(bookRequest));
  }

  @GetMapping("/all")
  public ResponseEntity<List<BookRequestInfoDto>> bookRequest() {
    return ResponseEntity.ok(requestService.getBookRequest());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookRequestInfoDto> bookRequest(@PathVariable Long id) {
    return ResponseEntity.ok(requestService.getBookRequestById(id));
  }

  @PostMapping("/{id}/{status}")
  public ResponseEntity<BookRequestInfoDto> bookRequestAccepted(@PathVariable Long id,
                                                                @PathVariable BookRequestStatus status) {
    return requestService.requestBook(id, status);
  }

  @Getter
  @Setter
  @ToString
  public static class BookRequestDto {
    private Long receiverUserId;
    private String isbn;
    private String exchangeIsbn;
    private BookRequestType type;
    private String message;
    private Integer price;
  }
}
