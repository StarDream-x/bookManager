package edu.whu.demo.service;

import edu.whu.demo.dao.TodoJPARepository;
import edu.whu.demo.entity.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiaxy
 * 待办事项业务服务类。
 */
@Service
public class TodoService {
    @Autowired
    TodoJPARepository todoRepository;

    public TodoItem addTodo(TodoItem todo) {
        return todoRepository.saveAndFlush(todo);
    }

    public TodoItem getTodo(long id) {
        return todoRepository.getById(id);
    }


    public void updateTodo(long id, TodoItem todo) {
        todoRepository.save(todo);
    }

    public void deleteTodo(long id) {
        todoRepository.deleteById(id);
    }

    public List<TodoItem> findTodos(String name, Boolean complete) {
        //动态构造查询条件，name和complete不为null时作为条件
        Specification<TodoItem> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (name != null) {
                predicateList.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if ((complete != null)) {
                predicateList.add(criteriaBuilder.equal(root.get("complete"), complete));
            }
            Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
            return criteriaBuilder.and(predicates);
        };

        List<TodoItem> result = todoRepository.findAll(specification);
        return result;
    }

}
