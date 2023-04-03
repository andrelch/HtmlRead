import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class HtmlRead implements ActionListener {

    ArrayList <String> htmlList = new ArrayList<String>();
    ArrayList <String> searchList = new ArrayList<String>();

    private int WIDTH=800;
    private int HEIGHT=600;

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel urlLabel;
    private JLabel searchLabel;
    private JTextArea outputTextArea;
    private JPanel barPanel;
    private JPanel buttonPanel;
    private JPanel outputPanel;
    private JPanel outputPanelIn;
    private JPanel textPanel;
    private JPanel textPanelIn;
    private JMenuBar menuBar;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea textAreaURL;
    private JTextArea textAreaSearch;
    private JScrollPane scrollPane;
    private String textURL;
    private String textSearch;

    public HtmlRead() {
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("HTML Reader");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(2, 1));

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

        textAreaURL = new JTextArea();
        textAreaURL.setBorder(BorderFactory.createTitledBorder("Enter URL:"));
        //textAreaURL.setBounds(50, 5, WIDTH-100, HEIGHT-50);
        textAreaSearch = new JTextArea();
        textAreaSearch.setBorder(BorderFactory.createTitledBorder("Enter Keyword:"));
        textPanel = new JPanel(new BorderLayout());
        textPanelIn = new JPanel(new GridLayout(1,2));
        textPanelIn.add(textAreaURL);
        textPanelIn.add(textAreaSearch);
        buttonPanel = new JPanel(new FlowLayout());
        textPanel.add(textPanelIn, BorderLayout.CENTER);
        textPanel.add(buttonPanel,BorderLayout.SOUTH);

        headerLabel = new JLabel("", JLabel.CENTER);
        urlLabel = new JLabel("", JLabel.CENTER);
        searchLabel = new JLabel("", JLabel.CENTER);
        outputTextArea = new JTextArea();
        scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output:"));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //frame.getContentPane().add(scrollableTextArea);

        outputPanelIn = new JPanel(new GridLayout(1,2));
        outputPanelIn.add(urlLabel);
        outputPanelIn.add(searchLabel);
        outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(outputPanelIn, BorderLayout.NORTH);
        outputPanel.add(scrollPane,BorderLayout.CENTER);

        mainFrame.add(menuBar);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.add(textPanel);
        mainFrame.add(outputPanel);
        mainFrame.setVisible(true);
        //statusLabel.setSize(350, 100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        //barPanel = new JPanel(new GridLayout(2,1));
        //barPanel.add(buttonPanel);

        // mainFrame.add(headerLabel);
        //mainFrame.add(buttonPanel);
    }

    private void showHtml() {
        headerLabel.setText("Control in action: Button");

        JButton okButton = new JButton("Submit");
        JButton submitButton = new JButton("Print");
        JButton clearButton = new JButton("Clear");

        okButton.setActionCommand("Submit");
        submitButton.setActionCommand("Print");
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

            if (command.equals("Submit")) {

                textURL = textAreaURL.getText();
                urlLabel.setText(textAreaURL.getText() + " is entered as the URL");
                textSearch = textAreaSearch.getText();
                searchLabel.setText(textAreaSearch.getText() + " is entered as the Keyword");

                //https://www.milton.edu/

                try {
                    System.out.println();
                    URL url = new URL(textURL);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(url.openStream())
                    );
                    String line;
                    while ( (line = reader.readLine()) != null ) {
                        System.out.println(line);

                        if (line.contains("href=\"")) {
                            int indexStart = line.indexOf("href", 0) + 6;
                            int indexEnd = line.indexOf("\"", indexStart);
                            System.out.println(indexStart +" " + indexEnd);
                            if (indexStart >= 0 && indexEnd >= 0) {
                                //htmlList.add(line.substring(indexStart, indexEnd));
                                System.out.println(line.substring(indexStart, indexEnd));
                                System.out.println(textSearch);
                                if(line.substring(indexStart, indexEnd).contains(textSearch)){
                                    htmlList.add(line.substring(indexStart, indexEnd));
//                                    System.out.println(line.substring(indexStart, indexEnd));

                                }
//                                System.out.println(line.substring(indexStart, indexEnd));
                                //System.out.println(htmlList);
                            }

                        }

                        if (line.contains("src='https") && htmlList.contains(textSearch)) {
                            int indexStart = line.indexOf("src", 0) + 5;
                            int indexEnd = line.indexOf("'", indexStart);
                            if (indexStart >= 0 && indexEnd >= 0) {
                                htmlList.add(line.substring(indexStart, indexEnd));
                                //System.out.println(line.substring(indexStart, indexEnd));
                                //System.out.println(htmlList);
                            }
                        }

                        //System.out.println(htmlList);

                    }
                    reader.close();
                } catch(Exception ex) {
                    System.out.println(ex);
                }
            }
            else if (command.equals("Print")) {
//                if(htmlList.contains(textSearch)){
//
//                    System.out.println("1");
//                    System.out.println(htmlList);
//                    outputLabel.setText(String.valueOf(htmlList));
//                }
                //System.out.println(htmlList);
                //outputTextArea.setText(String.valueOf(htmlList));

                for (int i = 0; i < htmlList.size(); i++){
                    String output = String.valueOf(htmlList.get(i));
                    outputTextArea.append(output + "\n");
                    System.out.println(htmlList.get(i));
                }
            }
            else if (command.equals("Clear")) {
                htmlList.clear();

                //outputTextArea.setText("Clear Button clicked.");
                textAreaURL.setText("");
                textAreaSearch.setText("");
                urlLabel.setText("");
                searchLabel.setText("");

            }
        }
    }
}
