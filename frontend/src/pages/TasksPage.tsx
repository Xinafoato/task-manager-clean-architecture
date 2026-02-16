import { useEffect, useState, useMemo } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import styles from "./TasksPage.module.css";
import CalendarWidget from "../components/CalendarWidget";
import {
    getTasks,
    createTask,
    deleteTask,
    completeTask,
    reopenTask,
    updateTask,
    type Task
} from "../api/taskApi";
import { useAuth } from "../context/AuthContext";

export default function TasksPage() {
    const { listId } = useParams<{ listId: string }>();
    const navigate = useNavigate();
    const location = useLocation();
    const listName = (location.state as { listName?: string })?.listName || 'Tasks';
    const [tasks, setTasks] = useState<Task[]>([]);
    const [newTaskDesc, setNewTaskDesc] = useState("");
    const [newDueDate, setNewDueDate] = useState("");
    const [sortBy, setSortBy] = useState<'priority' | 'date'>('priority');

    const sortedTasks = useMemo(() => {
        return [...tasks].sort((a, b) => {
            if (sortBy === 'priority') {
                const weight: Record<string, number> = { HIGH: 3, MEDIUM: 2, LOW: 1 };
                return (weight[b.priority] || 2) - (weight[a.priority] || 2);
            }
            // sort by date
            if (!a.dueDate && !b.dueDate) return 0;
            if (!a.dueDate) return 1;
            if (!b.dueDate) return -1;
            return new Date(a.dueDate).getTime() - new Date(b.dueDate).getTime();
        });
    }, [tasks, sortBy]);

    useEffect(() => {
        if (listId) {
            loadTasks();
        }
    }, [listId]);

    const loadTasks = async () => {
        if (!listId) return;
        try {
            const data = await getTasks(listId);
            setTasks(data);
        } catch (error) {
            console.error("Failed to load tasks", error);
        }
    };

    const { user } = useAuth();
    const userId = user!.id;

    const handleCreate = async () => {
        if (!newTaskDesc.trim() || !listId) return;
        try {
            await createTask(userId, listId, newTaskDesc, "", newDueDate || undefined);
            setNewTaskDesc("");
            setNewDueDate("");
            loadTasks();
        } catch (error) {
            console.error("Failed to create task", error);
        }
    };

    const handleDelete = async (taskId: string) => {
        if (!listId) return;
        try {
            await deleteTask(listId, taskId);
            loadTasks();
        } catch (error) {
            console.error("Failed to delete task", error);
        }
    };

    const toggleComplete = async (task: Task) => {
        if (!listId) return;
        try {
            if (task.status !== 'DONE') {
                await completeTask(listId, task.id);
            } else {
                await reopenTask(listId, task.id);
            }
            loadTasks();
        } catch (error) {
            console.error("Failed to toggle task", error);
        }
    };

    const handlePriorityClick = async (e: React.MouseEvent, task: Task) => {
        e.stopPropagation();
        if (!listId) return;

        const priorities = ['LOW', 'MEDIUM', 'HIGH'];
        const currentIndex = priorities.indexOf(task.priority || 'MEDIUM');
        const nextPriority = priorities[(currentIndex + 1) % priorities.length];

        try {
            await updateTask(listId, task.id, { priority: nextPriority });
            loadTasks();
        } catch (error) {
            console.error("Failed to update priority", error);
        }
    };

    return (
        <div className={styles.container}>
            <div className={styles.headerSection}>
                <button className={styles.backButton} onClick={() => navigate("/")}>
                    ← Back to Dashboard
                </button>
                <h1 className={styles.title}>{listName}</h1>

                <div className={styles.inputGroup}>
                    <input
                        className={styles.input}
                        value={newTaskDesc}
                        onChange={e => setNewTaskDesc(e.target.value)}
                        placeholder="Add a new task..."
                        onKeyDown={(e) => e.key === 'Enter' && handleCreate()}
                    />
                    <input
                        type="date"
                        className={styles.inputDate}
                        value={newDueDate}
                        onChange={e => setNewDueDate(e.target.value)}
                        title="Set Due Date"
                    />
                    <button
                        className={styles.addButton}
                        onClick={handleCreate}
                    >
                        Add Task
                    </button>
                </div>

                <div className={styles.sortControls}>
                    <span className={styles.sortLabel}>Sort by:</span>
                    <button
                        className={`${styles.sortButton} ${sortBy === 'priority' ? styles.sortActive : ''}`}
                        onClick={() => setSortBy('priority')}
                    >
                        🔥 Priority
                    </button>
                    <button
                        className={`${styles.sortButton} ${sortBy === 'date' ? styles.sortActive : ''}`}
                        onClick={() => setSortBy('date')}
                    >
                        📅 Due Date
                    </button>
                </div>
            </div>

            <div className={styles.contentGrid}>
                <div className={styles.taskList}>
                    {sortedTasks.map(task => {
                        const isCompleted = task.status === 'DONE';
                        // Determine priority class for border color
                        const priorityClass = task.priority ? styles[`priority${task.priority}`] : styles.priorityMEDIUM;

                        return (
                            <div
                                key={task.id}
                                className={`${styles.taskItem} ${isCompleted ? styles.completed : ''} ${priorityClass}`}
                            >
                                <div className={styles.taskContent} onClick={() => toggleComplete(task)}>
                                    <div className={`${styles.checkbox} ${isCompleted ? styles.checked : ''}`}>
                                        {isCompleted && "✓"}
                                    </div>
                                    <div className={styles.taskDetails}>
                                        <span className={`${styles.taskTitle} ${isCompleted ? styles.completedText : ''}`}>
                                            {task.title}
                                        </span>
                                        {task.description && (
                                            <span className={styles.taskDescription}>
                                                {task.description}
                                            </span>
                                        )}
                                        <div className={styles.metaTags}>
                                            <span
                                                className={`${styles.badge} ${styles[`badgePriority${task.priority}`]}`}
                                                onClick={(e) => handlePriorityClick(e, task)}
                                                title="Click to change priority"
                                            >
                                                {task.priority}
                                            </span>
                                            {task.dueDate && (
                                                <span className={`${styles.dueDate} ${(!isCompleted && new Date(task.dueDate) < new Date()) ? styles.overdue : ''}`}>
                                                    📅 {new Date(task.dueDate).toLocaleDateString()}
                                                </span>
                                            )}
                                        </div>
                                    </div>
                                </div>
                                <button
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        handleDelete(task.id);
                                    }}
                                    className={styles.deleteButton}
                                    title="Delete Task"
                                >
                                    🗑️
                                </button>
                            </div>
                        );
                    })}

                    {tasks.length === 0 && (
                        <div className={styles.emptyState}>
                            No tasks yet. Add one above! ✨
                        </div>
                    )}
                </div>

                <div className={styles.calendarSection}>
                    <CalendarWidget tasks={tasks} />
                </div>
            </div>
        </div>
    );
}
