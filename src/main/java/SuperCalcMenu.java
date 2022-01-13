import javax.swing.*;
import java.awt.event.ActionEvent;

public class SuperCalcMenu implements View {
    private JPanel root;
    private JButton calculatorButton;
    private JButton probabilityButton;

    SuperCalcApp parent;

    public SuperCalcMenu(SuperCalcApp parent) {
        this.parent = parent;

        calculatorButton.addActionListener(this::buttonPressed);
        probabilityButton.addActionListener(this::buttonPressed);
    }

    private void buttonPressed(ActionEvent event) {
        if (event.getSource() == calculatorButton) {
            parent.setPanel("calculator");
        } else if (event.getSource() == probabilityButton) {
            parent.setPanel("probability");
        }
    }

    @Override
    public JPanel getRoot() {
        return root;
    }
}
