package instant.system.demo.exception;

public class RefreshTokenIsMissedException extends RuntimeException{

    public RefreshTokenIsMissedException(String msg) {
        super(msg);
    }

}
