package beans.mbeans.MXBeanImpl;


import beans.mbeans.PointsStatisticsMXBean;

import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;

public class PointsStatistics extends NotificationBroadcasterSupport
        implements PointsStatisticsMXBean, Serializable {

    private int totalPoints = 0;
    private int missedPoints = 0;
    private long sequenceNumber = 1;

    public void addPoint(boolean isHit) {
        totalPoints++;
        if (!isHit) {
            missedPoints++;
        }

        if (totalPoints % 15 == 0) {
            Notification notification = new Notification(
                    "points.count.multiple.15",
                    this,
                    sequenceNumber++,
                    "Количество точек кратно 15: " + totalPoints
            );
            sendNotification(notification);
        }
    }

    @Override
    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public int getMissedPoints() {
        return missedPoints;
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[]{
                new MBeanNotificationInfo(
                        new String[]{"points.count.multiple.15"},
                        Notification.class.getName(),
                        "Уведомление о достижении кратности 15"
                )
        };
    }
}