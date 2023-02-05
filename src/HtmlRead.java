import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class HtmlRead implements ActionListener {

    private int WIDTH=800;
    private int HEIGHT=600;

    private JFrame mainFrame;

    private JLabel headerLabel;
    private JLabel statusLabel;
    private JLabel resultLabel;
    private JPanel barPanel;
    private JPanel buttonPanel;
    private JPanel outputPanel;
    private JMenuBar menuBar;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea textArea;

    public HtmlRead() {
        try {
            System.out.println();
            URL url = new URL("https://www.milton.edu/");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ( (line = reader.readLine()) != null ) {
                //System.out.println(line);

                if (line.contains("href=\"https")) {
                    int indexStart = line.indexOf("href", 0) + 6;
                    int indexEnd = line.indexOf("\"", indexStart);
                    if (indexStart >= 0 && indexEnd >= 0) {
                        System.out.println(line.substring(indexStart, indexEnd));
                    }

                }

                if (line.contains("src='https")) {
                    int indexStart = line.indexOf("src", 0) + 5;
                    int indexEnd = line.indexOf("'", indexStart);
                    if (indexStart >= 0 && indexEnd >= 0) {
                        System.out.println(line.substring(indexStart, indexEnd));
                    }
                }
            }
            reader.close();
        } catch(Exception ex) {
            System.out.println(ex);
        }

        prepareGUI();

    }

    private void prepareGUI() {
        mainFrame = new JFrame("HTML Reader");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(3, 1));

        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

        menuBar = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);

        textArea = new JTextArea();
        textArea.setBounds(50, 5, WIDTH-100, HEIGHT-50);
        mainFrame.add(menuBar);
        mainFrame.add(textArea);
        mainFrame.setJMenuBar(menuBar);

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        resultLabel = new JLabel("", JLabel.CENTER);
        //statusLabel.setSize(350, 100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        barPanel = new JPanel(new GridLayout(2,1));
        buttonPanel = new JPanel(new FlowLayout());
        barPanel.add(buttonPanel);
        barPanel.add(statusLabel);

        outputPanel = new JPanel(new GridLayout(2,1));
        outputPanel.add(resultLabel);

        // mainFrame.add(headerLabel);
        mainFrame.add(buttonPanel);
        mainFrame.add(outputPanel);
        mainFrame.setVisible(true);
    }

    private void showHtml() {
        headerLabel.setText("Control in action: Button");

        JButton okButton = new JButton("OK");
        JButton submitButton = new JButton("Submit");
        JButton clearButton = new JButton("Clear");

        okButton.setActionCommand("OK");
        submitButton.setActionCommand("Submit");
        clearButton.setActionCommand("Clear");

        okButton.addActionListener(new ButtonClickListener());
        submitButton.addActionListener(new ButtonClickListener());
        clearButton.addActionListener(new ButtonClickListener());

        buttonPanel.add(okButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);

        mainFrame.setVisible(true);

    }

    public static void main(String[] args) {
        HtmlRead html = new HtmlRead();
        html.showHtml();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("OK")) {
                statusLabel.setText("Ok Button clicked.");
            }
            else if (command.equals("Submit")) {
                statusLabel.setText("Submit Button clicked.");

                String output = "hello";
                resultLabel.setText(output);
            }
            else if (command.equals("Clear")) {
                statusLabel.setText("Clear Button clicked.");
            }
        }
    }
}
