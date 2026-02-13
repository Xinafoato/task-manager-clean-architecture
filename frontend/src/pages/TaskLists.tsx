import { useEffect, useState } from "react";
import {
    getTaskLists,
    createTaskList,
    renameTaskList,
    deleteTaskList
} from "../api/taskApi";

interface TaskList {
    id: string;
    name: string;
}

export default function TaskLists() {
    const [taskLists, setTaskLists] = useState<TaskList[]>([]);
    const [newName, setNewName] = useState("");
    const [editingId, setEditingId] = useState<string | null>(null);
    const [editingName, setEditingName] = useState("");

    const userId = "user1";

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
        <div style={{ padding: "20px" }}>
            <h1>Task Lists</h1>

            <div style={{ marginBottom: "20px" }}>
                <input
                    value={newName}
                    onChange={e => setNewName(e.target.value)}
                    placeholder="Nueva lista"
                />
                <button onClick={handleCreate}>Crear</button>
            </div>

            {taskLists.map(tl => (
                <div key={tl.id} style={{ marginBottom: "10px" }}>
                    {editingId === tl.id ? (
                        <>
                            <input
                                value={editingName}
                                onChange={e => setEditingName(e.target.value)}
                            />
                            <button onClick={() => handleRename(tl.id)}>
                                Guardar
                            </button>
                            <button onClick={() => setEditingId(null)}>
                                Cancelar
                            </button>
                        </>
                    ) : (
                        <>
                            <span style={{ marginRight: "10px" }}>
                                {tl.name}
                            </span>
                            <button
                                onClick={() => {
                                    setEditingId(tl.id);
                                    setEditingName(tl.name);
                                }}
                            >
                                Editar
                            </button>
                            <button
                                onClick={() => handleDelete(tl.id)}
                                style={{ marginLeft: "5px" }}
                            >
                                Eliminar
                            </button>
                        </>
                    )}
                </div>
            ))}
        </div>
    );
}
