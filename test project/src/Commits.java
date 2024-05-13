import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Commits
{
    private final String nickname;
    private LocalDateTime commitDate;
    private int commitsCount;

    public Commits(String n, LocalDateTime date)
    {
        this.nickname = n;
        this.commitDate = date;
        this.commitsCount = 1;
    }

    public void setCommitsCount (int i) { this.commitsCount = i; }
    public void setCommitDate (LocalDateTime date)
    {
        if (date.isAfter(this.commitDate)) this.commitDate = date;
    }
    public int getCommitsCount() { return commitsCount; }

    public LocalDateTime getCommitDate() { return commitDate; }
    public String getNickname() { return nickname; }
}
