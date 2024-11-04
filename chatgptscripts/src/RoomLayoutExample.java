import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomLayoutExample extends JFrame {
    private JPanel roomPanel;
    private int roomCount = 0;

    public RoomLayoutExample() {
        setTitle("Room Layout Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        roomPanel = new JPanel(new GridBagLayout());
        add(new JScrollPane(roomPanel), BorderLayout.CENTER);

        JButton addButton = new JButton("Add Room");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoom();
            }
        });
        add(addButton, BorderLayout.SOUTH);
    }

    private void addRoom() {
        roomCount++;
        JPanel room = new JPanel();
        room.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        room.setPreferredSize(new Dimension(100, 100));
        room.add(new JLabel("Room " + roomCount));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Calculate the position based on the room count
        int gridX = (roomCount - 1) % 3; // Arrange rooms in 3 columns
        int gridY = (roomCount - 1) / 3; // Each row will contain 3 rooms

        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        roomPanel.add(room, gbc);
        roomPanel.revalidate();
        roomPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RoomLayoutExample example = new RoomLayoutExample();
            example.setVisible(true);
        });
    }
}
