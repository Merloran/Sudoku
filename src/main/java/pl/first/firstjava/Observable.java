package pl.first.firstjava;

public interface Observable {
    void setObserver(Observer observer);

    void notifyObservers(int x);
}
