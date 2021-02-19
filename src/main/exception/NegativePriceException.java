package exception;

public class NegativePriceException extends Exception {
    public NegativePriceException() {

        System.out.println("The price must not be a negative number");
    }
}
