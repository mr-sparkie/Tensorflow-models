package com.hightech.services;

import com.hightech.bean.Task;
import com.hightech.dao.exception.CustomDAOException;

import java.util.List;

public interface TaskService {
    Task getTaskById(int taskId) throws CustomDAOException;
    List<Task> getAllTasks() throws CustomDAOException;
    List<Task> getTasksByUserId(int userId) throws CustomDAOException;
    boolean addTask(Task task) throws CustomDAOException;
    boolean updateTask(Task task) throws CustomDAOException;
    boolean deleteTask(int taskId) throws CustomDAOException;
}
