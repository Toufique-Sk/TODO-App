package io.toufique.todo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.toufique.todo.exception.ResourceNotFoundException;
import io.toufique.todo.models.Todo;
import io.toufique.todo.repository.TodoRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TodoController {
	
	
	@Autowired
	TodoRepository todoRepo;
	
	
	@GetMapping("/all")
	public List<Todo> getAllTodos()
	{
		Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
		return todoRepo.findAll(sortByCreatedAtDesc);
	}
	
	@PostMapping("/todo")
	public Todo createTodo(@Valid @RequestBody Todo todo)
	{
		return todoRepo.save(todo);
	}
	
	@GetMapping("/todo/{id}")
	public Todo getTodoById(@PathVariable(value="id") Long todoId)
	{
		
		return todoRepo.findById(todoId).orElseThrow(()-> new ResourceNotFoundException("Note", "id", todoId));
	
		/*Todo todoById = null;
		List<Todo> allTodo= getAllTodos();
		for (Todo todo : allTodo)
		{
			if(todo.getId() == todoId)
			{
				todoById=todo;
				break;
			}
		}
		return todoById;*/
	}
	@PutMapping("/todo/{id}")
	public Todo updateTodo(@PathVariable(value="id") Long todoId,@Valid @RequestBody Todo todoDetails)
	{
		Todo todo=todoRepo.findById(todoId).orElseThrow(()-> new ResourceNotFoundException("Todo","id",todoId));
		todo.setTitle(todoDetails.getTitle());
		todo.setCompleted(todoDetails.getCompleted());
		
		Todo updatedTodo=todoRepo.save(todo);
		return updatedTodo;
	}
	@DeleteMapping("todo/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable(value="id") Long todoId)
	{
		Todo note = todoRepo.findById(todoId)
	            .orElseThrow(() -> new ResourceNotFoundException("Todo", "id", todoId));

	    todoRepo.delete(note);

	    return ResponseEntity.ok().build();
	}
	

}
