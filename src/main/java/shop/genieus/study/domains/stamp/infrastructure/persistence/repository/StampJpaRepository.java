package shop.genieus.study.domains.stamp.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;

public interface StampJpaRepository extends JpaRepository<Stamp, Long> {
  @Query(
      "SELECT s FROM Stamp s WHERE s.userId = :userId AND s.verifiedAt >= :start AND s.verifiedAt < :end")
  List<Stamp> getStampByDate(
      @Param("userId") Long userId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);

  @Query(
      "SELECT s FROM CodingTestStamp s WHERE s.userId = :userId AND s.verifiedAt >= :start AND s.verifiedAt < :end")
  List<CodingTestStamp> getCtStampByDate(
      @Param("userId") Long userId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);

  @Query(
      "SELECT s FROM TilStamp s WHERE s.userId = :userId AND s.verifiedAt >= :start AND s.verifiedAt < :end")
  List<TilStamp> getTilStampByDate(
      @Param("userId") Long userId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);

  @Query(
      "SELECT s FROM ResumeStamp s WHERE s.userId = :userId AND s.verifiedAt >= :start AND s.verifiedAt < :end")
  List<ResumeStamp> getResumeStampByDate(
      @Param("userId") Long userId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);

  @Query("SELECT COUNT(s) FROM CodingTestStamp s WHERE s.userId = :userId AND s.verifiedAt >= :start AND s.verifiedAt < :end")
  long countCtStampByDate(@Param("userId") Long userId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);

  @Query("SELECT COUNT(s) FROM TilStamp s WHERE s.userId = :userId AND s.verifiedAt >= :start AND s.verifiedAt < :end")
  long countTilStampByDate(@Param("userId") Long userId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);

  @Query("SELECT COUNT(s) FROM ResumeStamp s WHERE s.userId = :userId AND s.verifiedAt >= :start AND s.verifiedAt < :end")
  long countResumeStampByDate(@Param("userId") Long userId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);
}
