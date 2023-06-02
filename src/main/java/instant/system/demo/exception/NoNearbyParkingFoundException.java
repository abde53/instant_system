package instant.system.demo.exception;

// Exception déclenché en cas ou aucun parking n'est trouvé
public class NoNearbyParkingFoundException extends RuntimeException{
    public NoNearbyParkingFoundException(String msg) {
        super(msg);
    }

}
