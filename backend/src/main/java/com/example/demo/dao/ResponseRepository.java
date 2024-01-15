package com.example.demo.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Response;

@Repository
@EnableJpaRepositories
public interface ResponseRepository extends JpaRepository<Response, Integer>  {

}
