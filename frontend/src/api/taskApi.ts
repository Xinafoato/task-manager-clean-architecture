const BASE_URL = "http://localhost:7000";

export interface Task {
    id: string;
    title: string;
    description: string;
    status: 'TODO' | 'IN_PROGRESS' | 'DONE';
    priority: 'LOW' | 'MEDIUM' | 'HIGH';
    dueDate?: string; // Date comes as string from JSON
}

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

/* ================= TASKS API ================= */

export async function getTasks(taskListId: string): Promise<Task[]> {
    const response = await fetch(`${BASE_URL}/tasks?taskListId=${taskListId}`);
    if (!response.ok) throw new Error("Error fetching tasks");
    return response.json();
}

export async function createTask(userId: string, taskListId: string, title: string, description: string = "", dueDate?: string) {
    const response = await fetch(`${BASE_URL}/tasks`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            userId,
            taskListId,
            title,
            description,
            status: "TODO",
            priority: "MEDIUM",
            dueDate
        }),
    });
    if (!response.ok) throw new Error("Error creating task");
    return response.json();
}



export async function deleteTask(taskListId: string, taskId: string) {
    const response = await fetch(`${BASE_URL}/tasks/${taskId}?taskListId=${taskListId}`, {
        method: "DELETE",
    });
    if (!response.ok) throw new Error("Error deleting task");
}

export async function startTask(taskListId: string, taskId: string) {
    const response = await fetch(`${BASE_URL}/tasks/${taskId}/start?taskListId=${taskListId}`, {
        method: "POST",
    });
    if (!response.ok) throw new Error("Error starting task");
}

export async function completeTask(taskListId: string, taskId: string) {
    const response = await fetch(`${BASE_URL}/tasks/${taskId}/complete?taskListId=${taskListId}`, {
        method: "POST",
    });
    if (!response.ok) throw new Error("Error completing task");
}

export async function reopenTask(taskListId: string, taskId: string) {
    const response = await fetch(`${BASE_URL}/tasks/${taskId}/reopen?taskListId=${taskListId}`, {
        method: "POST",
    });
    if (!response.ok) throw new Error("Error reopening task");
}

export async function updateTask(
    taskListId: string,
    taskId: string,
    updates: {
        title?: string;
        description?: string;
        priority?: string;
        dueDate?: Date;
    }
) {
    const response = await fetch(`${BASE_URL}/tasks/${taskId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            taskListId,
            taskId,
            newTitle: updates.title,
            newDescription: updates.description,
            newPriority: updates.priority,
            newDueDate: updates.dueDate ? updates.dueDate.toISOString() : undefined
        }),
    });
    if (!response.ok) throw new Error("Error updating task");
}

