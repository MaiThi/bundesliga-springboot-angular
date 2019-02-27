package com.manage.footballapi.Repository;

import com.manage.footballapi.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    public List<Player>findByIdGreaterThanAndIdLessThan(long idGreater, long idLesser);
    public Player findById(long id);
    public List<Player>findByIdNotIn (Long[] listId);
}
