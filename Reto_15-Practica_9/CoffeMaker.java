public class CoffeMaker {

    //atributo añadido
    private boolean off = true;

    //metodos
    public void On() {
        off = false;
        System.out.println("CoffeMaker is on");
    }
    public void Off() {
        off = true;
    }
    public boolean isOff() {
        return off;
    } 
}
