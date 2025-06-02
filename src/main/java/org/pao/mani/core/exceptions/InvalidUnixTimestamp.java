package org.pao.mani.core.exceptions;

public class InvalidUnixTimestamp extends RuntimeException {
    public InvalidUnixTimestamp() {
        super("Invalid Unix Timestamp");
    }
}
