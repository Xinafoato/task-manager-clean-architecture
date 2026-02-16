import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./TaskLists.module.css";
import {
    getTaskLists,
    createTaskList,
    renameTaskList,
    deleteTaskList
} from "../api/taskApi";
import { useAuth } from "../context/AuthContext";

interface TaskList {
    id: string;
    name: string;
}

export default function TaskLists() {
    const navigate = useNavigate();
    const { user, logout } = useAuth();
    const [taskLists, setTaskLists] = useState<TaskList[]>([]);
    const [newName, setNewName] = useState("");
    const [editingId, setEditingId] = useState<string | null>(null);
    const [editingName, setEditingName] = useState("");

    const userId = user!.id;

    const loadTaskLists = async () => {
        const data = await getTaskLists(userId);
        setTaskLists(data);
    };

    useEffect(() => {
        loadTaskLists();
    }, []);

    const handleCreate = async () => {
        if (!newName.trim()) return;
        await createTaskList(userId, newName);
        setNewName("");
        loadTaskLists();
    };

    const handleRename = async (id: string) => {
        if (!editingName.trim()) return;
        await renameTaskList(id, editingName);
        setEditingId(null);
        setEditingName("");
        loadTaskLists();
    };

    const handleDelete = async (id: string) => {
        await deleteTaskList(id);
        loadTaskLists();
    };

    return (
        <div className={styles.container}>
            <div className={styles.headerSection}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <h1 className={styles.title}>Dashboard</h1>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '16px' }}>
                        <span style={{ color: '#636e72', fontSize: '0.9rem' }}>👤 {user?.username}</span>
                        <button
                            onClick={() => { logout(); navigate('/login'); }}
                            style={{
                                background: 'none',
                                border: '1px solid #e1e4e8',
                                borderRadius: '8px',
                                padding: '8px 16px',
                                cursor: 'pointer',
                                color: '#636e72',
                                fontWeight: 600,
                                fontSize: '0.85rem',
                                transition: 'all 0.2s',
                            }}
                        >
                            Logout
                        </button>
                    </div>
                </div>
                <div className={styles.inputGroup}>
                    <input
                        className={styles.input}
                        value={newName}
                        onChange={e => setNewName(e.target.value)}
                        placeholder="Create a new task list..."
                        onKeyDown={(e) => e.key === 'Enter' && handleCreate()}
                    />
                    <button
                        className={styles.createButton}
                        onClick={handleCreate}
                    >
                        Create
                    </button>
                </div>
            </div>

            <div className={styles.grid}>
                {taskLists.map(tl => (
                    <div
                        key={tl.id}
                        className={styles.card}
                        onClick={() => navigate(`/lists/${tl.id}`, { state: { listName: tl.name } })}
                        style={{ cursor: "pointer" }}
                        title="Double click to view tasks"
                    >
                        {editingId === tl.id ? (
                            <div className={styles.editContainer}>
                                <input
                                    className={styles.editInput}
                                    value={editingName}
                                    onChange={e => setEditingName(e.target.value)}
                                    onKeyDown={(e) => e.key === 'Enter' && handleRename(tl.id)}
                                    autoFocus
                                    onClick={(e) => e.stopPropagation()} // Prevent navigation when clicking input
                                />
                                <div className={styles.editActions}>
                                    <button
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            setEditingId(null);
                                        }}
                                        className={styles.cancelButton}
                                    >
                                        Cancel
                                    </button>
                                    <button
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            handleRename(tl.id);
                                        }}
                                        className={styles.saveButton}
                                    >
                                        Save
                                    </button>
                                </div>
                            </div>
                        ) : (
                            <div className={styles.cardHeader}>
                                <h3 className={styles.cardTitle}>{tl.name}</h3>
                                <div style={{ display: 'flex', gap: '4px' }}>
                                    <button
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            setEditingId(tl.id);
                                            setEditingName(tl.name);
                                        }}
                                        className={styles.iconButton}
                                        title="Edit"
                                    >
                                        ✏️
                                    </button>
                                    <button
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            handleDelete(tl.id);
                                        }}
                                        className={`${styles.iconButton} ${styles.deleteButton}`}
                                        title="Delete"
                                    >
                                        🗑️
                                    </button>
                                </div>
                            </div>
                        )}
                    </div>
                ))}
                {taskLists.length === 0 && (
                    <div className={styles.emptyState}>
                        No task lists found. Create your first list above to get started! 🚀
                    </div>
                )}
            </div>
        </div>
    );
}



