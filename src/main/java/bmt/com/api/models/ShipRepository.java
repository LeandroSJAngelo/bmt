package bmt.com.api.models;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ShipRepository extends JpaRepository<VoyageDetail, Long> {
    @Query("SELECT v FROM voyage_details v WHERE " +
            "(COALESCE(:param, '') = '' OR " +
            "v.departureData LIKE %:param% OR " +
            "v.arrivalData LIKE %:param% OR " +
            "v.vesselVoyage LIKE %:param% OR " +
            "v.voyage LIKE %:param%)")
    Page<VoyageDetail> findByGenericParam(@Param("param") String param, Pageable pages);
}
