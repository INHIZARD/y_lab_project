package org.example.in.services;

import org.example.in.models.Counters;
import org.example.in.models.User;

import java.util.*;


/**
 * {@code MainService} реализует работу с пользователями.
 * Основная логика всех действий прописана в этом классе
 */
public class MainService {
    /**
     * Поле для хранения текущего времени в программе в виде объекта класса {@code Calendar}
     */
    private final Calendar date;
    /**
     * Поле для хранения счетчиков, по умолчанию доступны три счетчика (отопление, холодная вода и горячая вода)
     * @see Counters
     */
    private final Map<String, Integer> indications;

    public MainService() {
        this.date = new GregorianCalendar();
        this.indications = new HashMap<>(Map.of(
                "Отопление", 0,
                "Холодная вода", 0,
                "Горячая вода", 0));
    }

    /**
     * Метод проверяющий авторизацию пользователя на корректный ввод данных
     * @param command команда содержащая {@code username} и {@code password} пользователя
     * @param users список всех пользователей
     * @return authorisation success
     */
    public boolean loginUser(String command, List<User> users) {
        String[] line = command.split(" ");
        User user = new User(line[1], line[2], new Counters(indications));
        return users.contains(user) && Objects.equals(users.get(users.indexOf(user)).getPassword(), user.getPassword());
    }

    /**
     * Метод проверяющий валидность регистрации пользователя.
     * Также вносит данные нового пользователя при корректной регистрации
     * @param command команда содержащая {@code username} и {@code password} пользователя
     * @param users список всех пользователей
     * @return registration success
     */
    public boolean signupUser(String command, List<User> users) {
        String[] line = command.split(" ");
        User newUser = new User(line[1], line[2], new Counters(this.indications));
        if (!users.contains(newUser)) {
            users.add(newUser);
            return true;
        }
        return false;
    }

    /**
     * Метод возвращающий пользователя на основе его {@code username} и {@code password}
     * @param command команда содержащая {@code username} и {@code password} пользователя
     * @param users список всех пользователей
     * @return user
     */
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

    /**
     * Метод, сохраняющий показатели пользователя.
     * Если пользователь уже сохранял показания, то метод возвращает false.
     * Иначе показания сохраняются и метод возвращает true
     * @param user пользователь
     * @return success saved
     */
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

    /**
     * Метод возвращающий возможные изменения на основе даты
     * @param user пользователь
     * @param command команда, содержащая дату
     * @return {@code Optional<Counters>}
     */
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

    /**
     * Метод добавляющий к текущей дате определенное количество дней, прописанных в {@code command}.
     * Метод также увеличивает показания счетчиков пользователей
     * @param command команда, содержащая количество дней
     * @param users пользователи
     * @return String newTime
     */
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

    /**
     * Метод возвращающий пользователя на основе его {@code username}
     * @param command команда содержащая {@code username} пользователя
     * @param users список всех пользователей
     * @return user
     */
    public User getUserWithoutPassword(String command, List<User> users) {
        String[] line = command.split(" ");
        for (User someUser : users) {
            if (Objects.equals(line[1], someUser.getUsername())) {
                return someUser;
            }
        }
        return null;
    }

    /**
     * Метод сохранения действий пользователя в аудит
     * @param user пользователь
     * @param command команда пользователя
     */
    public void audit(User user, String command) {
        user.getAudit().add(
                date.get(Calendar.DAY_OF_MONTH) + "."
                        + (date.get(Calendar.MONTH) + 1) + "."
                        + date.get(Calendar.YEAR)
                        + " - " + command);
    }

    /**
     * Метод для создания нового счетчика
     * @param command команда, содержащая название нового счетчика
     * @param users список пользователей
     */
    public void setIndication(String command, List<User> users) {
        String title = command.substring("indication".length() + 1);
        for (User user : users) {
            user.getIndications().getCounters().put(title, 0);
        }
        indications.put(title, 0);
    }

    /**
     * Метод возвращающий объект класса {@code Counters} со всеми счетчиками
     * @return indications
     */
    public Counters getIndications() {
        return new Counters(indications);
    }

    /**
     * Метод, который дает права администратора.
     * Возвращает true, если пользователь стал администратором,
     * или возвращает false, если пользователь уже был администратором
     * @param command команда содержащая {@code username} пользователя
     * @param users список пользователей
     * @return was admin
     */
    public boolean setAdmin(String command, List<User> users) {
        User user = getUserWithoutPassword(command, users);
        if (user.isStatus()) {
            return false;
        }
        user.setStatus(true);
        return true;
    }

    /**
     * Метод, который забирает права администратора.
     * Возвращает true, если пользователь потерял права,
     * или возвращает false, если пользователь уже не имел права или пользователь пытается забрать права у себя
     * @param command команда содержащая {@code username} пользователя
     * @param users список пользователей
     * @return was not admin
     */
    public boolean deleteAdmin(String command, List<User> users, User admin) {
        User user = getUserWithoutPassword(command, users);
        if (!user.isStatus() || Objects.equals(user.getUsername(), admin.getUsername())) {
            return false;
        }
        user.setStatus(false);
        return true;
    }
}
