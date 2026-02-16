import { useState } from 'react';
import styles from './CalendarWidget.module.css';
import type { Task } from '../api/taskApi';

interface CalendarWidgetProps {
    tasks: Task[];
}

export default function CalendarWidget({ tasks }: CalendarWidgetProps) {
    const [currentDate, setCurrentDate] = useState(new Date());

    const getDaysInMonth = (date: Date) => {
        const year = date.getFullYear();
        const month = date.getMonth();
        const daysInMonth = new Date(year, month + 1, 0).getDate();
        const firstDayOfMonth = new Date(year, month, 1).getDay(); // 0 = Sunday

        const days = [];

        // Add empty slots for previous month
        for (let i = 0; i < firstDayOfMonth; i++) {
            days.push({ day: null, fullDate: null });
        }

        // Add days of current month
        for (let i = 1; i <= daysInMonth; i++) {
            days.push({
                day: i,
                fullDate: new Date(year, month, i)
            });
        }

        return days;
    };

    const days = getDaysInMonth(currentDate);
    const monthNames = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];

    const changeMonth = (offset: number) => {
        const newDate = new Date(currentDate.setMonth(currentDate.getMonth() + offset));
        setCurrentDate(new Date(newDate));
    };

    const isToday = (date: Date | null) => {
        if (!date) return false;
        const today = new Date();
        return date.getDate() === today.getDate() &&
            date.getMonth() === today.getMonth() &&
            date.getFullYear() === today.getFullYear();
    };

    const getTasksForDate = (date: Date | null) => {
        if (!date) return [];
        return tasks.filter(task => {
            if (!task.dueDate) return false;
            const taskDate = new Date(task.dueDate);
            return taskDate.getDate() === date.getDate() &&
                taskDate.getMonth() === date.getMonth() &&
                taskDate.getFullYear() === date.getFullYear();
        });
    };

    return (
        <div className={styles.calendarContainer}>
            <div className={styles.header}>
                <button className={styles.navButton} onClick={() => changeMonth(-1)}>←</button>
                <h2 className={styles.title}>
                    {monthNames[currentDate.getMonth()]} {currentDate.getFullYear()}
                </h2>
                <button className={styles.navButton} onClick={() => changeMonth(1)}>→</button>
            </div>

            <div className={styles.grid}>
                {['S', 'M', 'T', 'W', 'T', 'F', 'S'].map((day, i) => (
                    <div key={i} className={styles.dayName}>{day}</div>
                ))}

                {days.map((item, index) => {
                    const dayTasks = getTasksForDate(item.fullDate);
                    const isCurrentDay = isToday(item.fullDate);

                    return (
                        <div
                            key={index}
                            className={`${styles.dayCell} ${!item.day ? styles.otherMonth : ''} ${isCurrentDay ? styles.today : ''}`}
                            title={dayTasks.map(t => t.title).join('\n')}
                        >
                            {item.day}
                            <div className={styles.taskDotsContainer}>
                                {dayTasks.map(task => (
                                    <div
                                        key={task.id}
                                        className={`${styles.taskDot} ${styles[`priority${task.priority || 'MEDIUM'}`]}`}
                                    />
                                ))}
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}
