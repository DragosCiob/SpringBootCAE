package ro.siit.SpringBootCAE.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name="ProjectTask")
public class ProjectTask extends Request{


   @Column(name ="projectTask_name",nullable = false, length = 32)
    private String projectTaskName;

    @Column(name ="start_date",nullable = false)
    private LocalDate startDate;

    @Column(name ="deadline",nullable = false)
    private LocalDate deadline;

    @Column(name ="status",nullable = false)
    boolean ongoing;

    public ProjectTask(UUID id, String requestName, String text, Project project, LocalDate startDate, LocalDate deadline) {
        super(id, requestName, text, project);
        this.startDate = startDate;
        this.deadline = deadline;

      projectTaskName = requestName;
      ongoing=true;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getProjectTaskName() {
        return projectTaskName;
    }

    public void setProjectTaskName(String projectTaskName) {
        this.projectTaskName = projectTaskName;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    public ProjectTask(){};

    @Override
    public String toString() {

        return getProject().getProjectName();
    }
}
