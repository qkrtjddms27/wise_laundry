package com.ssafy.wiselaundry.domain.laundry.db.repository;

import com.ssafy.wiselaundry.domain.laundry.db.entity.Laundry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface LaundryRepository extends JpaRepository<Laundry, Long> {
    Laundry findByLaundryId(long laundryId);

    @Transactional
    void deleteByLaundryId(long laundryId);
}
