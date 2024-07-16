package com.hightech.dao;

import com.hightech.bean.Task;
import com.hightech.dao.exception.CustomDAOException;

import java.sql.SQLException;
import java.util.List;

public interface TaskDAO {
    Task getTaskById(int taskId) throws CustomDAOException;
    List<Task> getAllTasks() throws CustomDAOException, SQLException;
    List<Task> getTasksByUserId(int userId) throws CustomDAOException,SQLException;
    boolean addTask(Task task) throws CustomDAOException,SQLException;
    boolean updateTask(Task task) throws CustomDAOException,SQLException;
    boolean deleteTask(int taskId) throws CustomDAOException,SQLException;
}
