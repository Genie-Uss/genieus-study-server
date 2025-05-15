package shop.genieus.study.domains.stamp.application.repository;

import java.time.LocalDate;
import java.util.List;
import shop.genieus.study.domains.stamp.domain.entity.CodingTestStamp;
import shop.genieus.study.domains.stamp.domain.entity.ResumeStamp;
import shop.genieus.study.domains.stamp.domain.entity.Stamp;
import shop.genieus.study.domains.stamp.domain.entity.TilStamp;

public interface StampRepository {
    Stamp save(Stamp stamp);

    Stamp findById(Long id);

    void delete(Stamp stamp);

    List<Stamp> getStampByDate(Long userId, LocalDate date);

    List<CodingTestStamp> getCtStampByDate(Long userId, LocalDate date);

    List<TilStamp> getTilStampByDate(Long userId, LocalDate date);

    List<ResumeStamp> getResumeStampByDate(Long userId, LocalDate date);

    long countCtStampByDate(Long userId, LocalDate date);

    long countTilStampByDate(Long userId, LocalDate date);

    long countResumeStampByDate(Long userId, LocalDate date);
}
