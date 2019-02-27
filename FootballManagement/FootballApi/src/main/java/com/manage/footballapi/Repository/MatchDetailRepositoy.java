package com.manage.footballapi.Repository;

import com.manage.footballapi.Model.MatchDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchDetailRepositoy extends JpaRepository<MatchDetails, Long> {

}
