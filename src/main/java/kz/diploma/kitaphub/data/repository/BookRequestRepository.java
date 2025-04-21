package kz.diploma.kitaphub.data.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import kz.diploma.kitaphub.data.dto.DashboardPieDto;
import kz.diploma.kitaphub.data.dto.DashboardRequestDto;
import kz.diploma.kitaphub.data.entity.BookRequest;
import kz.diploma.kitaphub.data.entity.BookRequestStatus;
import kz.diploma.kitaphub.data.entity.BookRequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {
  @Query("SELECT b from BookRequest b where (:id is NULL or b.senderUser.id = :id) "
         + "or (:id is NULL or b.receiverUser.id = :id) ")
  List<BookRequest> getBookRequest(Long id);

  BookRequest getBookRequestById(Long id);

  @Modifying
  @Transactional
  @Query("""
      UPDATE BookRequest b 
      SET b.status = 'REJECTED' 
      WHERE (b.book.isbn = :bookIsbn 
      AND b.status = 'PENDING'
      AND b.receiverUser.id =:userId ) OR 
      (b.exchangeBook.isbn = :bookIsbn 
      AND b.status = 'PENDING'
      AND b.senderUser.id =:userId)
      """)
  void rejectedAllRequest(String bookIsbn, Long userId);

  @Modifying
  @Transactional
  @Query(""" 
        delete from BookRequest b where (b.senderUser.id =:userId or b.receiverUser.id =:userId) 
      """)
  void deleteBookById(Long userId);

  @Modifying
  @Transactional
  @Query(""" 
        delete from BookRequest b where (b.book.isbn =:isbn or b.exchangeBook.isbn =:isbn) 
      """)
  void deleteByIsbn(String isbn);

  @EntityGraph(attributePaths = {"receiverUser", "senderUser"})
  @Query("""
      SELECT b from BookRequest b where (:search is null or b.senderUser.username ilike %:search%)
      or (:search is null or b.receiverUser.username ilike %:search%)
      """
  )
  Page<BookRequest> adminFindAll(Pageable pageable, String search);

  Integer countByTypeAndStatus(BookRequestType type, BookRequestStatus status);

  @Query("""
          SELECT new kz.diploma.kitaphub.data.dto.DashboardRequestDto(
              FUNCTION('TO_CHAR', b.beginDate, 'YYYY-MM-DD'),
              SUM(CASE WHEN b.type = :type1 THEN 1 ELSE 0 END),
              SUM(CASE WHEN b.type = :type2 THEN 1 ELSE 0 END)
          )
          FROM BookRequest b
          GROUP BY FUNCTION('TO_CHAR', b.beginDate, 'YYYY-MM-DD')
          ORDER BY FUNCTION('TO_CHAR', b.beginDate, 'YYYY-MM-DD') ASC
      """)
  List<DashboardRequestDto> getDashboard1(
      @Param("type1") BookRequestType type1,
      @Param("type2") BookRequestType type2
  );

  @Query("""
          SELECT new kz.diploma.kitaphub.data.dto.DashboardRequestDto(
              FUNCTION('TO_CHAR', b.beginDate, 'YYYY-MM-DD'),
              SUM(CASE WHEN b.type = :type1 THEN 1 ELSE 0 END),
              SUM(CASE WHEN b.type = :type2 THEN 1 ELSE 0 END)
          )
          FROM BookRequest b
          WHERE b.status = :status1
          GROUP BY FUNCTION('TO_CHAR', b.beginDate, 'YYYY-MM-DD')
          ORDER BY FUNCTION('TO_CHAR', b.beginDate, 'YYYY-MM-DD') ASC
      """)
  List<DashboardRequestDto> getDashboard2(
      @Param("type1") BookRequestType type1,
      @Param("type2") BookRequestType type2,
      @Param("status1") BookRequestStatus status1
  );

  @Query("""
          SELECT new kz.diploma.kitaphub.data.dto.DashboardPieDto(
              SUM(CASE WHEN b.status = :status1 THEN 1 ELSE 0 END),
              SUM(CASE WHEN b.status = :status2 THEN 1 ELSE 0 END),
              SUM(CASE WHEN b.status = :status3 THEN 1 ELSE 0 END)
          )
          FROM BookRequest b
      """)
  DashboardPieDto getDashboard3(
      BookRequestStatus status1,
      BookRequestStatus status2,
      BookRequestStatus status3
  );
}
