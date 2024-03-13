package utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ApplicationScoped
public class DataValidator implements Serializable {
    public boolean isDataCorrect(Double x, Double y, Integer r) {
        return (r == 1 || r == 2 || r == 3 || r == 4)
                && y >= -5 && y <= 3
                && (x >= -2 && x <= 2 );
    }

}
