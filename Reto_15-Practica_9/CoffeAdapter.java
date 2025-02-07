public class CoffeAdapter implements Connectable{

    //atributo añadido
    private CoffeMaker coffeMaker;

    //Constructor
    public CoffeAdapter(CoffeMaker coffeMaker) {
        this.coffeMaker = coffeMaker;
    }

    //metodos
    public void turnOn() {
        coffeMaker.On();
    }
    public void turnOff() {
        coffeMaker.Off();
    }
    public boolean isOn() {
        return !coffeMaker.isOff();
    }
}
