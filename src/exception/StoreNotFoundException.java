package exception;

public class StoreNotFoundException extends Exception{
    public StoreNotFoundException(){
        super("Loja não cadastrada");
    }
}
