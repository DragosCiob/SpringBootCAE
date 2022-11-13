package ro.siit.SpringBootCAE.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name="ProjectTask")
public class ProjectTask extends Request{


    @Column(name ="start_date",nullable = false)
    private LocalDate startDate;

    @Column(name ="deadline",nullable = false)
    private LocalDate deadline;

    public ProjectTask(UUID id, String requestName, String text, Project project, LocalDate startDate, LocalDate deadline) {
        super(id, requestName, text, project);
        this.startDate = startDate;
        this.deadline = deadline;
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

    public ProjectTask(){};
}
