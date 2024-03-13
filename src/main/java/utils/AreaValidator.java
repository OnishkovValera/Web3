package utils;

public class AreaValidator {
    static public Boolean isHit(Double x, Double y, Integer r){
        if(x > 0 & y > 0){
            return false;
        }else if(x <= 0 & y >= 0){
            return y <= x + r;
        }else if(x <= 0 & y <= 0) {
            return x * x + y * y <= (double) (r * r);
        } else if (x >= 0 & y <= 0){
            return x <= r & Math.abs(y) <= (double) r/2;
        }else{
            return false;
        }
    }
}
