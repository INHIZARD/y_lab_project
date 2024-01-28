package org.example.in.services;

import org.example.in.models.Counters;
import org.example.in.models.User;

import java.util.*;

public class MainService {
    private final Calendar date;
    private final Map<String, Integer> indications;

    public MainService() {
        this.date = new GregorianCalendar();
        this.indications = new HashMap<>(Map.of(
                "Отопление", 0,
                "Холодная вода", 0,
                "Горячая вода", 0));
    }

    public boolean loginUser(String command, List<User> users) {
        String[] line = command.split(" ");
        User user = new User(line[1], line[2], new Counters(indications));
        return users.contains(user) && Objects.equals(users.get(users.indexOf(user)).getPassword(), user.getPassword());
    }

    public boolean signupUser(String command, List<User> users) {
        String[] line = command.split(" ");
        User newUser = new User(line[1], line[2], new Counters(this.indications));
        if (!users.contains(newUser)) {
            users.add(newUser);
            return true;
        }
        return false;
    }

    public User getUserWithPassword(String command, List<User> users) {
        String[] line = command.split(" ");
        User user = new User(line[1], line[2], new Counters(indications));
        for (User someUser : users) {
            if (Objects.equals(user.getUsername(), someUser.getUsername())
                    && Objects.equals(user.getPassword(), someUser.getPassword())) {
                return someUser;
            }
        }
        return null;
    }

    public boolean save(User user) {
        if (date.get(Calendar.MONTH) == user.getDateOfLastSubmit().get(Calendar.MONTH)
                && date.get(Calendar.YEAR) == user.getDateOfLastSubmit().get(Calendar.YEAR)) {
            return false;
        }
        Counters saveIndications = new Counters(user.getIndications());
        user.setDateOfLastSubmit((Calendar) date.clone());
        user.getSavedIndications().put((Calendar) date.clone(), saveIndications);
        return true;
    }

    public Optional<Counters> hasDataAt(User user, String command) {
        int month = Integer.parseInt(command.split(" ")[1].split("\\.")[0]);
        int year = Integer.parseInt(command.split(" ")[1].split("\\.")[1]);
        Map<Calendar, Counters> indications = user.getSavedIndications();
        Optional<Counters> result = Optional.empty();
        for (Calendar calendar : indications.keySet()) {
            if (calendar.get(Calendar.MONTH) + 1 == month && calendar.get(Calendar.YEAR) == year) {
                result = Optional.ofNullable(indications.get(calendar));
                break;
            }
        }
        return result;
    }

    public String addTime(String command, List<User> users) {
        for (User user : users) {
            Counters indications = user.getIndications();
            for (String title : indications.getCounters().keySet()) {
                indications.getCounters()
                        .put(title,
                                (indications.getCounters().get(title) + Math.abs(new Random().nextInt()) % 100) % 1000);
            }
        }

        date.add(Calendar.DAY_OF_MONTH, Integer.parseInt(command.split(" ")[1]));
        return date.get(Calendar.DAY_OF_MONTH) + "."
                + (date.get(Calendar.MONTH) + 1) + "."
                + date.get(Calendar.YEAR);
    }

    public User getUserWithoutPassword(String command, List<User> users) {
        String[] line = command.split(" ");
        for (User someUser : users) {
            if (Objects.equals(line[1], someUser.getUsername())) {
                return someUser;
            }
        }
        return null;
    }

    public void audit(User user, String command) {
        user.getAudit().add(
                date.get(Calendar.DAY_OF_MONTH) + "."
                        + (date.get(Calendar.MONTH) + 1) + "."
                        + date.get(Calendar.YEAR)
                        + " - " + command);
    }

    public void setIndication(String command, List<User> users) {
        String title = command.substring("indication".length() + 1);
        for (User user : users) {
            user.getIndications().getCounters().put(title, 0);
        }
        indications.put(title, 0);
    }

    public Counters getIndications() {
        return new Counters(indications);
    }

    public boolean setAdmin(String command, List<User> users) {
        User user = getUserWithoutPassword(command, users);
        if (user.isStatus()) {
            return false;
        }
        user.setStatus(true);
        return true;
    }

    public boolean deleteAdmin(String command, List<User> users, User admin) {
        User user = getUserWithoutPassword(command, users);
        if (!user.isStatus() || Objects.equals(user.getUsername(), admin.getUsername())) {
            return false;
        }
        user.setStatus(false);
        return true;
    }
}
