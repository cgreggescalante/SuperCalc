import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class DoubleInputVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            showMessageDialog(null, "Field : must be a valid input");
        }
        return false;
    }
}
