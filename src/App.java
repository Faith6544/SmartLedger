import gui.LoginWindow;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }

        SwingUtilities.invokeLater(() -> {
            LoginWindow login = new LoginWindow();
            login.setVisible(true);
        });
    }
}
