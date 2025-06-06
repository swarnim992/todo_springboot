package com.example.todo_managment.controller;

import com.example.todo_managment.dto.TodoDto;
import com.example.todo_managment.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
public class TodoController {
    private TodoService todoService;

    @PostMapping("addTodo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TodoDto> addTodo (@RequestBody TodoDto todoDto){

        TodoDto savedTodo = todoService.addTodo(todoDto);

        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<TodoDto> findById(@PathVariable("id") Long id){
        TodoDto savedTodo = todoService.getTodo(id);

        return new ResponseEntity<>(savedTodo, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("getAll")
    public ResponseEntity<List<TodoDto>> getAll(){
        List<TodoDto> todoDtos = todoService.getAllTodo();

        return ResponseEntity.ok(todoDtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/{id}")
    public  ResponseEntity<TodoDto> update(@RequestBody TodoDto todoDto,@PathVariable("id") Long id){
        TodoDto updatedDto =  todoService.updateTodo(todoDto,id);

        return ResponseEntity.ok(updatedDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        todoService.deleteTodo(id);

        return ResponseEntity.ok("Todo deleted");
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("complete/{id}")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long id){
        TodoDto updatedDto = todoService.completeTodo(id);

        return ResponseEntity.ok(updatedDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("inComplete/{id}")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long id){
        TodoDto updatedDto = todoService.inCompleteTodo(id);

        return ResponseEntity.ok(updatedDto);
    }
}
