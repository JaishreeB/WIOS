package com.cts.wios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.wios.model.Zone;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Integer> {

}
