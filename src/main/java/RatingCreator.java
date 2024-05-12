import commit.Commit;
import ranking.ContributionRanker;
import reader.FileReader;
import uploader.ResultUploader;

import java.util.List;

public class RatingCreator {
    // Количество призовых мест
    private static final int NUMBER_OF_AWARDEES = 3;

    private RatingCreator() {}

    public static void main(String[] args) {
        // Получение пути до файла с коммитами
        String commitsPath = FileReader.readPathFromProperties("commits.path");
        // Получение коммитов из вышеупомянутого файла
        List<Commit> commits = FileReader.readCommits(commitsPath);

        // Получение списка пользователей, отсортированного на основе количества сделанных ими корректных коммитов
        List<String> contributorsRanked = ContributionRanker.getContributorsRanked(commits, NUMBER_OF_AWARDEES);

        // Получение пути до файла с результатами
        String resultPath = FileReader.readPathFromProperties("result.path");
        // Передача списка пользователей для записи в файл с результатами
        ResultUploader.uploadResult(contributorsRanked, NUMBER_OF_AWARDEES, resultPath);
    }
}
