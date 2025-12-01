package game;

//Interface du sujet
public interface Sujet {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);
}
