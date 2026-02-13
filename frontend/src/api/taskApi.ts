const BASE_URL = "http://localhost:7000";

export async function getTaskLists(userId: string) {
    const response = await fetch(`${BASE_URL}/tasklists?userId=${userId}`);
    if (!response.ok) throw new Error("Error al obtener TaskLists");
    return response.json();
}

export async function createTaskList(userId: string, name: string) {
    const response = await fetch(`${BASE_URL}/tasklists`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ userId, name }),
    });

    if (!response.ok) throw new Error("Error al crear TaskList");
    return response.json();
}

export async function renameTaskList(taskListId: string, name: string) {
    const response = await fetch(`${BASE_URL}/tasklists/${taskListId}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ name }),
    });

    if (!response.ok) throw new Error("Error al renombrar TaskList");
}

export async function deleteTaskList(taskListId: string) {
    const response = await fetch(`${BASE_URL}/tasklists/${taskListId}`, {
        method: "DELETE",
    });

    if (!response.ok) throw new Error("Error al eliminar TaskList");
}
