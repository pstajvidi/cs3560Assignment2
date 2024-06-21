import java.util.ArrayList;
import java.util.List;

//notifys when something has changed, using the observor interface
public class Observable {
    private List<Observer> observers;

    public Observable() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}

