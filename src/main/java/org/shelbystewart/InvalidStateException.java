package org.shelbystewart;

public class InvalidStateException extends WizardException {
    public InvalidStateException(String message) {
        super(message);
    }
}