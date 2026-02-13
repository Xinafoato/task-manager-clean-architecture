package com.marti.domain.model;

public class TaskTagRelation {

    private String taskId;
    private String tagId;

    public TaskTagRelation(String taskId, String tagId) {
        this.taskId = taskId;
        this.tagId = tagId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTagId() {
        return tagId;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TaskTagRelation other = (TaskTagRelation) obj;
        return taskId.equals(other.taskId) && tagId.equals(other.tagId);
    }

    @Override
    public int hashCode() {
        return taskId.hashCode() * 31 + tagId.hashCode();
    }

    @Override
    public String toString() {
        return "TaskTagRelation{" +
                "taskId='" + taskId + '\'' +
                ", tagId='" + tagId + '\'' +
                '}';
    }
}
