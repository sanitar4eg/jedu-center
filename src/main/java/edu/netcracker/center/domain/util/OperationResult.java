package edu.netcracker.center.domain.util;

import java.util.Objects;

public class OperationResult {

    public OperationResult () {}

    public OperationResult(String identifier, String message, String description) {
        this.identifier = identifier;
        this.message = message;
        this.description = description;
    }

    private String identifier;

    private String message;

    private String description;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OperationResult result = (OperationResult) o;
        if (result.identifier == null || identifier == null) {
            return false;
        }
        return Objects.equals(identifier, result.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifier);
    }

    @Override
    public String toString() {
        return "OperationResult{" +
            "identifier=" + identifier +
            ", message='" + message + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
