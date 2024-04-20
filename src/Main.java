import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/imagedb";
        String username = "root";
        String password = "root";
        String folder_path = "C:\\Users\\DELL\\Pictures\\Linux\\";
        String query = "select image_data from image_table where image_id = (?)";

        // Loading drivers which are necessary to connect database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Establishing connection & converting image to binary format
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established successfully.");

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            // To retrieve data
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                byte[] image_data = resultSet.getBytes("image_data");
                String image_path = folder_path + "i1.jpg";
                OutputStream outputStream = new FileOutputStream(image_path);
                outputStream.write(image_data);
                System.out.println("Image successfully extracted!");
                outputStream.close();

            } else {
                System.out.println("Image not found!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}