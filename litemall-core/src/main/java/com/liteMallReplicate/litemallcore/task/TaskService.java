package com.liteMallReplicate.litemallcore.task;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.util.concurrent.Executors;
import java.util.concurrent.DelayQueue;

@Component
public class TaskService {
    private TaskService taskService;
    private DelayQueue<Task> delayQueue = new DelayQueue<Task>();

    @PostConstruct
    private void init() {
        taskService = this;

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Task task = delayQueue.take();
                        task.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void addTask(Task task) {
        if (delayQueue.contains(task)) {
            return;
        }
        delayQueue.add(task);
    }

    public void removeTask(Task task) { delayQueue.remove(task); }

}
