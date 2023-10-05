package bmt.com.api.models;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SchedulerRepository extends JpaRepository<SchedulerDetail, Long> {
    @Query("SELECT v FROM scheduler v WHERE " +
            "(:param IS NULL OR " +
            "CAST(v.departureData AS string) LIKE %:param% OR " +
            "CAST(v.arrivalData AS string) LIKE %:param% OR " +
            "v.vesselVoyage LIKE %:param% OR " +
            "v.voyage LIKE %:param%)")
    Page<SchedulerDetail> findByGenericParam(@Param("param") String param, Pageable pages);
}
