package org.example.in.models;

import java.util.*;

public class User {
    private String username;
    private String password;
    private boolean status;
    private Counters indications;
    private Calendar dateOfLastSubmit;
    private final Map<Calendar, Counters> savedIndications;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.status = false;
        this.indications = new Counters(new HashMap<>(Map.of(
                "Отопление", Math.abs(new Random().nextInt()) % 100,
                "Холодная вода", Math.abs(new Random().nextInt()) % 100,
                "Горячая вода", Math.abs(new Random().nextInt()) % 100)));
        this.dateOfLastSubmit = new GregorianCalendar(2000, Calendar.JANUARY, 1);
        this.savedIndications = new HashMap<>();
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
