package org.example.in.models;

import java.util.*;

/**
 * Класс реализующий пользователя
 */
public class User {
    // Имя пользователя
    private String username;
    // Пароль пользователя
    private String password;
    // Статус пользователя (false - обычный пользователь, true - администратор)
    private boolean status;
    // Показатели счетчиков пользователя
    private Counters indications;
    // Дата последней подачи показаний пользователя
    private Calendar dateOfLastSubmit;
    // Все поданные ранее показания
    private final Map<Calendar, Counters> savedIndications;
    // Аудит пользователя
    private final List<String> audit;

    public User(String username, String password, Counters counters) {
        this.username = username;
        this.password = password;
        this.status = false;
        this.indications = new Counters(counters);
        this.dateOfLastSubmit = new GregorianCalendar(2000, Calendar.JANUARY, 1);
        this.savedIndications = new HashMap<>();
        this.audit = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Counters getIndications() {
        return indications;
    }

    public void setIndications(Counters indications) {
        this.indications = indications;
    }

    public Calendar getDateOfLastSubmit() {
        return dateOfLastSubmit;
    }

    public void setDateOfLastSubmit(Calendar dateOfLastSubmit) {
        this.dateOfLastSubmit = dateOfLastSubmit;
    }

    public Map<Calendar, Counters> getSavedIndications() {
        return savedIndications;
    }

    public List<String> getAudit() {
        return audit;
    }

    /**
     * Метод {@code equals} сравнивает пользователей только по {@code username}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
