package org.example.in.services;

import org.example.in.models.Counters;
import org.example.in.models.User;

import java.util.*;

public class MainService {
    private final Calendar date;

    public MainService() {
        this.date = new GregorianCalendar();
    }

    public boolean loginUser(String command, List<User> users) {
        String[] line = command.split(" ");
        User user = new User(line[1], line[2]);
        return users.contains(user) && Objects.equals(users.get(users.indexOf(user)).getPassword(), user.getPassword());
    }

    public boolean signupUser(String command, List<User> users) {
        String[] line = command.split(" ");
        User newUser = new User(line[1], line[2]);
        if (!users.contains(newUser)) {
            users.add(newUser);
            return true;
        }
        return false;
    }

    public User getUser(String command, List<User> users) {
        String[] line = command.split(" ");
        User user = new User(line[1], line[2]);
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
}
