import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Создаю экземпляр класса DataAnalytics, чтобы получить доступ к методу getAnalytics
        var data = new DataAnalytics();
        // Вывожу строку с результатом в консоль
        System.out.println(data.getAnalytics());
    }
}