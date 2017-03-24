package Model;

public interface IView {
    //called by model whenever it changes state
    //overwriten by view classes

    public void updateView();
}
