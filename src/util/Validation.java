package util;

import Exceptions.ValidationException;

@FunctionalInterface
public interface Validation<T> {
    void validate(T value) throws ValidationException; // T means Generic type which works as a placeholder which tells the java that here the value is not fixed,this functional interface can work for multiple datatypes like string ,double etc
}
