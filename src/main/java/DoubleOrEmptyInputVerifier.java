import javax.swing.*;

public class DoubleOrEmptyInputVerifier extends DoubleInputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        if (text.isEmpty()) {
            return true;
        }
        return super.verify(input);
    }
}
