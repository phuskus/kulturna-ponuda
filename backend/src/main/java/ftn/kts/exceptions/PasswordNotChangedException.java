package ftn.kts.exceptions;

public class PasswordNotChangedException extends Exception {

    String jwt;

    public PasswordNotChangedException(String message, String jwt) {
        super(message);
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
