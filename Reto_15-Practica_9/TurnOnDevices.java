public class TurnOnDevices {
    public static void main(String[] args) {
        turnOnDevice(new Lamp());
        turnOnDevice(new Computer());
        //nueva linea
        turnOnDevice(new CoffeAdapter(new CoffeMaker()));
        }

        private static void turnOnDevice(Connectable device) {
        device.turnOn();
        System.out.println(device.isOn());
        }
}
