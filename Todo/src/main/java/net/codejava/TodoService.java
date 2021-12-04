package net.codejava;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TodoService {

	@Autowired
	private TodoRepository repo;
	
	public List<Todo> listAll(Sort so) {
		return (List<Todo>) repo.findAll(so);
	}
	
	public TodoRepository getRepo() {
		return repo;
	}

	public void setRepo(TodoRepository repo) {
		this.repo = repo;
	}

	public void save(Todo todo) {
		repo.save(todo);
	}
	
	public Todo get(long id) {
		return repo.findById(id).get();
	}
	
	public void delete(long id) {
		repo.deleteById(id);
	}
}
