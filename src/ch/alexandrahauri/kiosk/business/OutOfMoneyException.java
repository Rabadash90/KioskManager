package ch.alexandrahauri.kiosk.business;

/**
 * OutOfMoneyException - Own Exception in case Kiosk doesnt have enough Money
 *
 * @author: Alexandra
 * @since: 22.06.2018
 **/
public class OutOfMoneyException extends Exception {
    public OutOfMoneyException() {
        super();
    }
    public OutOfMoneyException(String message) {
        super(message);
    }
    public OutOfMoneyException(String message, Throwable throwable) {
        super(message,throwable);
    }
}
