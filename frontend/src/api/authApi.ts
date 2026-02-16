const BASE_URL = "http://localhost:7000";

export interface User {
    id: string;
    username: string;
    email: string;
}

// Backend returns userId, not id
interface BackendUserInfo {
    userId: string;
    username: string;
    email: string;
}

interface BackendAuthResponse {
    userInfo: BackendUserInfo;
}

function mapUser(info: BackendUserInfo): User {
    return { id: info.userId, username: info.username, email: info.email };
}

export const login = async (email: string, password: string): Promise<User> => {
    const response = await fetch(`${BASE_URL}/users/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, passwordHash: password }),
    });

    if (!response.ok) {
        let errorMessage = "Login failed";
        try {
            const errorData = await response.json();
            errorMessage = errorData.details || errorData.title || errorMessage;
        } catch (e) {
            // ignore
        }
        throw new Error(errorMessage);
    }

    const data: BackendAuthResponse = await response.json();
    return mapUser(data.userInfo);
};

export const signup = async (username: string, email: string, password: string): Promise<User> => {
    const response = await fetch(`${BASE_URL}/users/signup`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, email, passwordHash: password }),
    });

    if (!response.ok) {
        let errorMessage = "Signup failed";
        try {
            const errorData = await response.json();
            errorMessage = errorData.details || errorData.title || errorMessage;
        } catch (e) {
            // ignore
        }
        throw new Error(errorMessage);
    }

    const data: BackendAuthResponse = await response.json();
    return mapUser(data.userInfo);
};
