public class Lamp implements Connectable{

    //atributo añadido
    private boolean on = false;

    //metodos
    public void turnOn() {
        on = true;
        System.out.println("Lamp is on");
    }
    public void turnOff() {
        on = false;
    }
    public boolean isOn() {
        return on;
    }
}
