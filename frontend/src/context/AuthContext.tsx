import { createContext, useContext, useState, useEffect } from "react";
import type { ReactNode } from "react";
import { login as apiLogin, signup as apiSignup } from "../api/authApi";
import type { User } from "../api/authApi";

interface AuthContextType {
    user: User | null;
    isAuthenticated: boolean;
    login: (email: string, password: string) => Promise<void>;
    signup: (username: string, email: string, password: string) => Promise<void>;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
    const [user, setUser] = useState<User | null>(null);
    // Simple persistence using localStorage
    useEffect(() => {
        const storedUser = localStorage.getItem("task_manager_user");
        if (storedUser) {
            try {
                setUser(JSON.parse(storedUser));
            } catch (e) {
                console.error("Failed to parse user from local storage", e);
                localStorage.removeItem("task_manager_user");
            }
        }
    }, []);

    const login = async (email: string, password: string) => {
        const user = await apiLogin(email, password);
        setUser(user);
        localStorage.setItem("task_manager_user", JSON.stringify(user));
    };

    const signup = async (username: string, email: string, password: string) => {
        const user = await apiSignup(username, email, password);
        setUser(user);
        localStorage.setItem("task_manager_user", JSON.stringify(user));
    };

    const logout = () => {
        setUser(null);
        localStorage.removeItem("task_manager_user");
    };

    return (
        <AuthContext.Provider value={{ user, isAuthenticated: !!user, login, signup, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (context === undefined) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
}
