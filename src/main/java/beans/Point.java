package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

@Named
@SessionScoped
@Entity
public class Point implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Double x;
    private Double y;
    private Integer r = 4;
    private Boolean isHit;
    private String currentTime;
    private Long executionTime;
    private String sessionId;

    public Boolean getHit() {
        return isHit;
    }

    public void setHit(Boolean hit) {
        isHit = hit;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public Point(Double x, Double y, Integer r, boolean isHit, String currentTime, Long executionTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isHit = isHit;
        this.currentTime = currentTime;
        this.executionTime = executionTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public Point(Double x, Double y, Integer r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
    public Point(Double x, Double y, Integer r, Boolean isHit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isHit = isHit;
    }

    public Point() {
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", "+ r + ", " + isHit + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(id, point.id) && Objects.equals(x, point.x) && Objects.equals(y, point.y) && Objects.equals(r, point.r) && Objects.equals(isHit, point.isHit) && Objects.equals(currentTime, point.currentTime) && Objects.equals(executionTime, point.executionTime) && Objects.equals(sessionId, point.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, r, isHit, currentTime, executionTime, sessionId);
    }
}
