package com.example.demo.dao;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Quiz;


@Repository
@EnableJpaRepositories
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
	@Query(value = "select * from quiz q where q.username=?", nativeQuery = true)
	List<Quiz> findByUsername(@Param("username") String username);
	
	@Query(value = "select * from quiz q where q.quiz_status='ACTIVE' AND q.quiz_topic=? ", nativeQuery = true)
	List<Quiz> findByquizTopic(@Param("topic") String topic);

	@Query(value = "select distinct quiz_topic from quiz", nativeQuery = true)
	Set<String> getAllTopics();

	@Query(value = "select * from quiz q where q.quiz_id=? and q.quiz_status='ACTIVE'", nativeQuery = true)
	Quiz getQuizForStudent(@Param("id") Integer id);
	
}

