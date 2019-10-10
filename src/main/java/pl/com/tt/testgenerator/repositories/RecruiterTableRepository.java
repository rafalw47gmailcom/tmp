package pl.com.tt.testgenerator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.tt.testgenerator.entieties.RecruiterTable;
import pl.com.tt.testgenerator.entieties.enumclasses.RecruitmentState;

@Repository
public interface RecruiterTableRepository extends JpaRepository<RecruiterTable, Long> {


    @Modifying
    @Query(value = "Update RecruiterTable r Set r.state = :state Where r.id = :id")
    void setState(@Param("id") long id, @Param("state") RecruitmentState state);

}
