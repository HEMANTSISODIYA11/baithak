package com.hemant.baithak.repository;

import com.hemant.baithak.model.Meet;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetRepository extends JpaRepository<@NonNull Meet, @NonNull Long> {

  Optional<Meet> findByMeetId(String meetId);
}
