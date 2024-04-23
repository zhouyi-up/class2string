package com.liuujun.class2dml.utils;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

/**
 * 通知
 */
public class NotificationUtils {

    private static NotificationUtils notificationUtils;

    private NotificationGroup notificationGroup;
    private Project project;

    private NotificationUtils(Project project){
        this.project = project;
        notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("CLASS2DML_GROUP");
    }

    public static NotificationUtils getInstance(Project project){
        if (notificationUtils == null){
            notificationUtils = new NotificationUtils(project);
        }
        return notificationUtils;
    }

    public void info(String title, String content){
        Notification notification = notificationGroup.createNotification(title,content, NotificationType.INFORMATION);
        notification.notify(project);
    }

    public void error(String title, String content){
        Notification notification = notificationGroup.createNotification(title,content, NotificationType.ERROR);
        notification.notify(project);
    }

    public void warning(String title, String content){
        Notification notification = notificationGroup.createNotification(title,content, NotificationType.WARNING);
        notification.notify(project);
    }
}
