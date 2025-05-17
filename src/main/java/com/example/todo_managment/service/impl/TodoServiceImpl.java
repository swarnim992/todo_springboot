package com.example.todo_managment.service.impl;

import com.example.todo_managment.dto.TodoDto;
import com.example.todo_managment.entity.Todo;
import com.example.todo_managment.exception.ResourseNotFoundException;
import com.example.todo_managment.repository.TodoRepository;
import com.example.todo_managment.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
//        convert Dto to todo
//        Todo todo = new Todo();
//        todo.setTitle(todoDto.getTitle());
//        todo.setDescription(todoDto.getDescription());
//        todo.setCompleted((todoDto.isCompleted()));
//
        Todo todo = modelMapper.map(todoDto,Todo.class);

//        todo jpa intity
        Todo savedTodo = todoRepository.save(todo);
//        TodoDto savedDto = new TodoDto();
//        savedDto.setId(savedTodo.getId());
//        savedDto.setTitle(savedTodo.getTitle());
//        savedDto.setDescription(savedTodo.getDescription());
//        savedDto.setCompleted((savedTodo.isCompleted()));

        TodoDto savedDto = modelMapper.map(savedTodo,TodoDto.class);
        return savedDto;
    }

    @Override
    public TodoDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                ()-> new ResourseNotFoundException("Todo not found with id: "+id)
        );

        return modelMapper.map(todo,TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodo() {
        List<Todo> allTodo = todoRepository.findAll();

        return allTodo.stream().map((todo)-> modelMapper.map(todo,TodoDto.class)).collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                ()-> new ResourseNotFoundException("Todo not found with id: "+id)
        );

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted((todoDto.isCompleted()));

        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo,TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                ()-> new ResourseNotFoundException("Todo not found with id: "+id)
        );

//        todoRepository.delete(todo);
        todoRepository.deleteById(todo.getId());
    }

    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                ()-> new ResourseNotFoundException("Todo not found with id: "+id)
        );

        todo.setCompleted(Boolean.TRUE);

        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo,TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                ()-> new ResourseNotFoundException("Todo not found with id: "+id)
        );

        todo.setCompleted(Boolean.FALSE);

        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo,TodoDto.class);
    }
}
