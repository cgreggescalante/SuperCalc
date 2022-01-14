import javax.swing.*;
import java.awt.event.ActionEvent;

public interface View {
    JPanel getRoot();
    void returnToMenu(ActionEvent event);
}
