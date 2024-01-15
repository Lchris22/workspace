package com.example.demo.dao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Result;

@Repository
@EnableJpaRepositories
public interface ResultRepository extends JpaRepository<Result, Integer> {

	@Query(value = "select result_id, MAX(percentage) as percentage,quiz_id,username, MAX(score) as score\r\n"
			+ "FROM result\r\n"
			+ "where quiz_id=?\r\n"
			+ "group by username\r\n"
			+ "ORDER BY MAX(score)  DESC limit 3;", nativeQuery = true)
	List<Result> showLeaderBoard(@Param("id") int id);
	
	@Query(value = "select count(*) from (select * from result r\r\n"
			+ "where r.quiz_id=? and r.username=?) x ;", nativeQuery = true)
	int hasAnswered(@Param("quiz_id") int quiz_id, @Param("username") String username );
	
}
