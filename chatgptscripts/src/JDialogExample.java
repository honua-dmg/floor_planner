import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JDialogExample {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Main Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        // Button to open the dialog
        JButton openDialogButton = new JButton("Open Dialog");
        openDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and display the dialog
                showDialog(frame);
            }
        });

        frame.add(openDialogButton);
        frame.setVisible(true);
    }

    private static void showDialog(JFrame parent) {
        // Create the dialog
        JDialog dialog = new JDialog(parent, "Dialog Example", true); // true for modal
        dialog.setSize(200, 150);
        dialog.setLayout(new FlowLayout());

        // Add components to the dialog
        dialog.add(new JLabel("This is a dialog box!"));

        // OK button to close the dialog
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Close the dialog
            }
        });

        dialog.add(okButton);

        // Set the dialog location relative to the parent
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true); // Show the dialog
    }
}
