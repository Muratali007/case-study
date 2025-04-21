package kz.diploma.kitaphub.controller;

import java.util.List;
import kz.diploma.kitaphub.data.dto.BookCompilationDto;
import kz.diploma.kitaphub.service.CompilationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/compilation")
public class CompilationController {
  private final CompilationService compilationService;

  public CompilationController(CompilationService compilationService) {
    this.compilationService = compilationService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<BookCompilationDto>> getAllBookCompilation() {
    return ResponseEntity.ok(compilationService.getAllBookCompilations());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookCompilationDto> getBookCompilationById(@PathVariable Long id) {
    return ResponseEntity.ok(compilationService.getBookCompilationById(id));
  }
}
