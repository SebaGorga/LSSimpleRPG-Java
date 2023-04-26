package persistence.exceptions;

public class MonsterFileNotFoundException extends Exception{
    public MonsterFileNotFoundException(String message) {
        super(message);
    }
    @Override
    public String getMessage(){
        return super.getMessage();
    }
}
