package com.thihahtetkyaw.booking.service;

import com.thihahtetkyaw.booking.entity.Classes;
import com.thihahtetkyaw.booking.job.RefundJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final Scheduler scheduler;
    private final ClassesService classesService;

    public void refund(long classId, LocalDateTime endTime) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(RefundJob.class)
                    .withIdentity("classEndJob" + classId, "classGroup")
                    .usingJobData("classId", classId)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("classEndTrigger" + classId, "classGroup")
                    .startAt(Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant()))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 60000)
    public void checkNewClasses() {
        List<Classes> newClasses = classesService.findNewClass();
        System.out.println("quartz-scheduler is running...");
        for (Classes classDetails : newClasses) {
            System.out.printf("Created refund job will trigger at %s for %s.%n", classDetails.getName(), classDetails.getEndTime());
            refund(classDetails.getId(), classDetails.getEndTime());
        }
    }

}
