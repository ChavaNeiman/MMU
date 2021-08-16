package main.java.com.company.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;


public class CacheUnitView {

    private final JFrame frame;
    private final CacheUnitPanel panel;
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private final JTextArea textArea = new JTextArea();
    private final JLabel textAreaLabel = new JLabel();
    private final String textAreaStr ="\n\n           Welcome to our MMU Project! \n"
            + "           Press 'Load a Request' to load your Json File, \n"
            + "           OR Press 'Show Statistics' \n"
            + "           o see the Server's Statistics So far.";

    public CacheUnitView() {
        frame = new JFrame();
        panel = new CacheUnitPanel();
    }

    public void start() {
        panel.run();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        changeSupport.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        changeSupport.removePropertyChangeListener(pcl);
    }

    public <T> void updateUIData(T t)
    {
        if (t.toString().equals("true"))
        {
            textArea.setForeground(Color.green);
            textArea.setFont(new Font("Tahoma", Font.BOLD, 30));
            textArea.setText("\n\n                   Succeeded :) ");
            textAreaLabel.setIcon(new ImageIcon("images/suc~2.png"));
        }
        else if (t.toString().equals("false") || t.toString().equals("Empty") )
        {
            textArea.setForeground(Color.red);
            textArea.setFont(new Font("Tahoma", Font.BOLD, 20));
            textArea.setText("\n\n\t                Failed :( \n"
                    + "\tCheck your data and try again ");
            textAreaLabel.setIcon(new ImageIcon("images/error~2.png"));
        }
        else
        {
            textArea.setFont(new Font("Tahoma", Font.PLAIN, 17));
            textArea.setForeground(Color.black);
            String[] res = ((String) t).split(",");
            textArea.setText("\n    Algorithm: " + res[0] + "\n\n    Capacity: " + res[1] +
                    "\n\n    Total Numbers Of Requests: " + res[3] +
                    "\n\n    Total Number Of DataModel Swaps(From Cache To Disk): " + res[2] +
                    "\n\n    Total Number Of DataModels(GET/DELETE/UPDATE Requests): " + res[4]);
            textAreaLabel.setIcon(new ImageIcon("images/analytics~2.png"));
        }

        textArea.validate();
        textAreaLabel.validate();
        panel.revalidate();
        panel.repaint();
    }

    public class CacheUnitPanel extends JPanel implements java.awt.event.ActionListener
    {
        private static final long serialVersionUID = 1L;

        JButton button1;
        JButton button2;
        JLabel wp;
        JLabel label2;

        @Override
        public void actionPerformed(ActionEvent arg0) {}

//        public <T> void updateUIData(T t) {
//            if (t.toString().equals("true")){
//                textArea.setText("Succeeded");
//                textArea.setSelectedTextColor(Color.GREEN);
//            }
//            else if (t.toString().equals("false")) {
//                textArea.setText("Failed");
//                textArea.setSelectedTextColor(Color.RED);
//            }
//            else textArea.setText(t.toString());  // stat
//            textArea.invalidate();
//        }

        public void run() {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("MMU Project");
            frame.setBounds(700, 700, 700, 700);
            panel.setBorder(new EmptyBorder(5, 5, 5, 5));
            frame.setContentPane(panel);
            panel.setLayout(null);
            panel.setBackground(Color.darkGray);
            textArea.setBounds(65, 208, 560, 410);
            textAreaLabel.setBounds(305,278,710,450);
            textAreaLabel.setIcon(new ImageIcon("images/computer~1.png"));
            textArea.setSelectedTextColor(Color.WHITE);
            textArea.setForeground(Color.black);
            textArea.setText(textAreaStr);
            textArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
            panel.add(textAreaLabel);
            panel.add(textArea);
            //mmu-headline
            label2 = new JLabel("MMU");
            label2.setForeground(Color.white);
            label2.setBounds(290, 11, 313, 68);
            label2.setFont(new Font("Tahoma", Font.BOLD, 42));
            panel.add(label2);
            //stats
            button1 = new JButton("Show Statistics");
            button1.setFont(new Font("Tahoma", Font.PLAIN, 24));
            button1.setIcon(new ImageIcon("images/stat~3.png"));
            button1.setBackground(Color.white);
            button1.setBounds(365, 79, 260, 78);
            button1.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    PropertyChangeEvent change;
                    change = new PropertyChangeEvent(CacheUnitView.this, "stats", null, "{ \"headers\":{\"action\":\"STATS\"},\"body\":[]}");
                    changeSupport.firePropertyChange(change);
                }
            });
            panel.add(button1);

            //request
            button2 = new JButton("Load a Request");
            button2.setFont(new Font("Tahoma", Font.PLAIN, 24));
            button2.setIcon(new ImageIcon("images/upload~2.png"));
            button2.setBackground(Color.WHITE);
            button2.setBounds(65,79,260, 78);
            button2.setHorizontalAlignment(SwingConstants.RIGHT);
            button2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    JFileChooser fc = new JFileChooser();
                    fc.setCurrentDirectory(new File("src/main/java/com/company/resources"));
                    int result = fc.showOpenDialog(new JFrame());
                    if (result == JFileChooser.APPROVE_OPTION)
                    {
                        File selectedFile = fc.getSelectedFile();
                        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                        if (selectedFile != null) {
                            try
                            {
                                PropertyChangeEvent change;
                                change = new PropertyChangeEvent(CacheUnitView.this,"load",null,ParseFileToString(selectedFile.getPath()));
                                changeSupport.firePropertyChange(change);

                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                }

            });
            panel.add(button2);
            //background
//            wp = new JLabel("");
//            wp.setIcon(new ImageIcon("images/bg.png"));
//            wp.setBounds(0, 0, screenSize.width, screenSize.height);
//            panel.add(wp);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        public String ParseFileToString(String filePath) throws IOException
        {
            String readLine= null;
            BufferedReader reader = null;
            StringBuilder str = new StringBuilder();
            File file = new File(filePath);
            reader = new BufferedReader(new FileReader(file));
            readLine = reader.readLine();
            while (readLine != null)
            {
                str.append(readLine);
                readLine = reader.readLine();
            }
            reader.close();
            return str.toString();
        }
    }
}