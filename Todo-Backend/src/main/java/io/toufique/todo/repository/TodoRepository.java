package io.toufique.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.toufique.todo.models.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Long> {

}
