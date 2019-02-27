package com.manage.footballapi.Repository;

import com.manage.footballapi.Model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository <Location, Long> {
}
