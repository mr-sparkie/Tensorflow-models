package com.hightech.services;

import com.hightech.bean.Task;
import com.hightech.dao.TaskDAO;

import java.util.List;

public class TaskManagerService {
    private TaskDAO taskDAO;

    public TaskManagerService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public boolean addTask(Task task) {
        return taskDAO.addTask(task);
    }

    public boolean updateTask(Task task) {
        return taskDAO.updateTask(task);
    }

    public boolean deleteTask(int taskId) {
        return taskDAO.deleteTask(taskId);
    }

    public Task getTaskById(int taskId) {
        return taskDAO.getTaskById(taskId);
    }

    public List<Task> getTasksByUserId(int userId) {
        return taskDAO.getTasksByUserId(userId);
    }

//    public List<Task> getAllTasks() {
//        return taskDAO.getAllTasks();
//    }
}
