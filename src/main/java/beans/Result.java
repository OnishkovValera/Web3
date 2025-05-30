package beans;

import beans.mbeans.MXBeanImpl.PointsDistance;
import beans.mbeans.MXBeanImpl.PointsStatistics;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import utils.AreaValidator;
import utils.DataValidator;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

@Named
@SessionScoped
public class Result implements Serializable {
    private Point point = new Point();

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    private ArrayList<Point> points = new ArrayList<>();
    @Inject
    DataValidator dataValidator;
    private PointsStatistics pointsStatistics;
    private PointsDistance pointsDistance;
    private String sessionId;
    private Configuration configuration = new Configuration().configure();
    private SessionFactory sessionFactory = configuration.buildSessionFactory();

    @PostConstruct
    public void loadPoints(){
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(true);
        this.sessionId = session.getId();
        points = loadPointsFromDB(sessionId);

        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

            // Регистрация MBean для статистики точек
            pointsStatistics = new PointsStatistics();
            ObjectName statsName = new ObjectName("beans:type=PointsStatistics");
            mbs.registerMBean(pointsStatistics, statsName);

            // Регистрация MBean для расстояний между точками
            pointsDistance = new PointsDistance();
            ObjectName distanceName = new ObjectName("beans:type=PointsDistance");
            mbs.registerMBean(pointsDistance, distanceName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        points.clear();
    }
    public ArrayList<Point> sendData(){
        return points;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public ArrayList<Point> loadPointsFromDB(String sessionId){
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        System.out.println(sessionId);
        Query<Point> points = session.createQuery("from Point where sessionId = :sessionId", Point.class).setParameter("sessionId", sessionId);
        session.getTransaction().commit();
        ArrayList<Point> array = (ArrayList<Point>) points.list();
        session.close();
        return array;
    }
    public void addPoint(Point point){
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.persist(point);
        session.getTransaction().commit();
        session.close();
        System.out.println(points);

    }
    public boolean process(){
        long timer = System.nanoTime();

        if(sessionId == null){
            FacesContext fCtx = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(true);
            this.sessionId = session.getId();

        }
        if(dataValidator.isDataCorrect(point.getX(), point.getY(), point.getR())){
            Point newPoint = new Point(point.getX(), point.getY(), point.getR());
            boolean isHit = AreaValidator.isHit(point.getX(), point.getY(), point.getR());
            newPoint.setHit(isHit);
            pointsStatistics.addPoint(isHit);
            pointsDistance.addPoint(newPoint);
            newPoint.setSessionId(sessionId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String currentTime = formatter.format(LocalDateTime.now(ZoneOffset.UTC));
            long scriptTime = (long) ((System.nanoTime() - timer) * 0.001);
            newPoint.setExecutionTime(scriptTime);
            newPoint.setCurrentTime(currentTime);
            points.add(newPoint);
            addPoint(newPoint);
            point.setX(null);
            point.setY(null);
            for(Point point: points){
                System.out.println(point);
            }
            return true;
        }else{
            return false;
        }

    }

    public Boolean fromAsync(){
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        point.setX(Double.parseDouble(params.get("x")));
        point.setY(Double.parseDouble(params.get("y")));
        point.setR(Integer.parseInt(params.get("r")));
        System.out.println(points);
        return process();
    }
    public Result() {
    }


}
