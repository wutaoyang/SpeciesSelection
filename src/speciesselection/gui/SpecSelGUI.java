package speciesselection.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;
import preprocessing.Options;
import preprocessing.PlotPoints;
import preprocessing.ProblemSpecies;
import speciesselection.SpeciesSelection;

/**
 * GUI for Species Selection software
 * @author mre16utu
 */
public class SpecSelGUI extends javax.swing.JFrame {

    // variable to get result of file open/save dialog
    private int fileChooserResult;
    private String workingDir;
    private volatile boolean cancelled;

    /**
     * Creates new form SpecSelGUI
     */
    public SpecSelGUI() {
        initComponents();
        jButtonCancelP.setVisible(false);
        jButtonCancelA.setVisible(false);
        workingDir = System.getProperty("user.dir");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelProcess = new javax.swing.JPanel();
        jPanelTop = new javax.swing.JPanel();
        jButtonSelectDataFileP = new javax.swing.JButton();
        jTextFieldDataFilePath = new javax.swing.JTextField();
        jButtonProcess = new javax.swing.JButton();
        jCheckBoxTruncate = new javax.swing.JCheckBox();
        jButtonCancelP = new javax.swing.JButton();
        jPanelBottom = new javax.swing.JPanel();
        jLabelProcessTime = new javax.swing.JLabel();
        jPanelAnalyse = new javax.swing.JPanel();
        jButtonSelectDataFileA = new javax.swing.JButton();
        jTextFieldDataFilePath1 = new javax.swing.JTextField();
        jSpinnerInitialSpeciesPct = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerAllowableExpDivergencePct = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldProblemSpecies = new javax.swing.JTextField();
        jButtonProblemSpecies = new javax.swing.JButton();
        jLabelProcessTimeA = new javax.swing.JLabel();
        jButtonCancelA = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaPoints = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jRadioButtonNone = new javax.swing.JRadioButton();
        jRadioButtonFinal = new javax.swing.JRadioButton();
        jRadioButtonAll = new javax.swing.JRadioButton();
        jLabelProcessCompletedTimeA = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaKey = new javax.swing.JTextArea();
        jPanelProbability = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Species Selection ");

        jPanelProcess.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanelProcess.setLayout(new java.awt.BorderLayout());

        jPanelTop.setPreferredSize(new java.awt.Dimension(782, 60));

        jButtonSelectDataFileP.setText("Select Data File");
        jButtonSelectDataFileP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectDataFilePActionPerformed(evt);
            }
        });

        jTextFieldDataFilePath.setText("Forest_D_ALL.txt");

        jButtonProcess.setText("Process");
        jButtonProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProcessActionPerformed(evt);
            }
        });

        jCheckBoxTruncate.setSelected(true);
        jCheckBoxTruncate.setText("Truncate Results");

        jButtonCancelP.setText("Cancel");
        jButtonCancelP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonSelectDataFileP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldDataFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxTruncate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonCancelP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonProcess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSelectDataFileP)
                    .addComponent(jTextFieldDataFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonProcess)
                    .addComponent(jCheckBoxTruncate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCancelP)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelProcess.add(jPanelTop, java.awt.BorderLayout.PAGE_START);

        jPanelBottom.setMinimumSize(new java.awt.Dimension(100, 30));
        jPanelBottom.setPreferredSize(new java.awt.Dimension(782, 30));

        jLabelProcessTime.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout jPanelBottomLayout = new javax.swing.GroupLayout(jPanelBottom);
        jPanelBottom.setLayout(jPanelBottomLayout);
        jPanelBottomLayout.setHorizontalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBottomLayout.createSequentialGroup()
                .addGap(0, 618, Short.MAX_VALUE)
                .addComponent(jLabelProcessTime, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelBottomLayout.setVerticalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelProcessTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanelProcess.add(jPanelBottom, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Process", jPanelProcess);

        jButtonSelectDataFileA.setText("Select Data File");
        jButtonSelectDataFileA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectDataFileAActionPerformed(evt);
            }
        });

        jTextFieldDataFilePath1.setText("Forest1.txt");

        jSpinnerInitialSpeciesPct.setModel(new javax.swing.SpinnerNumberModel(50, 14, null, 1));

        jLabel1.setText("Initial No of Species:");

        jLabel2.setText("Allowable % Exp divergence:");

        jSpinnerAllowableExpDivergencePct.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        jLabel3.setText("Problem Species:");
        jLabel3.setMaximumSize(new java.awt.Dimension(107, 14));
        jLabel3.setMinimumSize(new java.awt.Dimension(107, 14));
        jLabel3.setPreferredSize(new java.awt.Dimension(107, 14));

        jTextFieldProblemSpecies.setText("results");

        jButtonProblemSpecies.setText("Find Problem Species");
        jButtonProblemSpecies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProblemSpeciesActionPerformed(evt);
            }
        });

        jLabelProcessTimeA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelProcessTimeA.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jButtonCancelA.setText("Cancel");
        jButtonCancelA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelAActionPerformed(evt);
            }
        });

        jTextAreaPoints.setColumns(20);
        jTextAreaPoints.setRows(5);
        jScrollPane1.setViewportView(jTextAreaPoints);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel4.setText("Output for:");

        buttonGroup1.add(jRadioButtonNone);
        jRadioButtonNone.setText("None");

        buttonGroup1.add(jRadioButtonFinal);
        jRadioButtonFinal.setSelected(true);
        jRadioButtonFinal.setText("Final");

        buttonGroup1.add(jRadioButtonAll);
        jRadioButtonAll.setText("All");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonFinal)
                            .addComponent(jRadioButtonAll)
                            .addComponent(jRadioButtonNone))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonNone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonFinal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonAll))
        );

        jLabelProcessCompletedTimeA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jTextAreaKey.setEditable(false);
        jTextAreaKey.setBackground(new java.awt.Color(240, 240, 240));
        jTextAreaKey.setColumns(20);
        jTextAreaKey.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextAreaKey.setRows(5);
        jTextAreaKey.setText("Key:\nX        - Number of species in DataFile\nY        - MinSpecSetFamily (MSSF) size\nMargin   - Y / predicted Y (Exponential Curve fitting)\nDataFile - name of subset file processed\nTime     - HH:MM:SS.ss time taken to generate MSSF");
        jScrollPane2.setViewportView(jTextAreaKey);

        javax.swing.GroupLayout jPanelAnalyseLayout = new javax.swing.GroupLayout(jPanelAnalyse);
        jPanelAnalyse.setLayout(jPanelAnalyseLayout);
        jPanelAnalyseLayout.setHorizontalGroup(
            jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                        .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                                .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSpinnerInitialSpeciesPct)
                                    .addComponent(jSpinnerAllowableExpDivergencePct, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)))
                            .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                                .addComponent(jButtonSelectDataFileA)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDataFilePath1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonCancelA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonProblemSpecies, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldProblemSpecies, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelProcessTimeA, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                    .addComponent(jLabelProcessCompletedTimeA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        jPanelAnalyseLayout.setVerticalGroup(
            jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                        .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonSelectDataFileA)
                            .addComponent(jTextFieldDataFilePath1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonProblemSpecies))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jSpinnerInitialSpeciesPct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonCancelA))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jSpinnerAllowableExpDivergencePct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))
                    .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldProblemSpecies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                    .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(146, 146, 146)
                        .addComponent(jLabelProcessCompletedTimeA, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelProcessTimeA, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Analyse", jPanelAnalyse);

        javax.swing.GroupLayout jPanelProbabilityLayout = new javax.swing.GroupLayout(jPanelProbability);
        jPanelProbability.setLayout(jPanelProbabilityLayout);
        jPanelProbabilityLayout.setHorizontalGroup(
            jPanelProbabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 799, Short.MAX_VALUE)
        );
        jPanelProbabilityLayout.setVerticalGroup(
            jPanelProbabilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Probability", jPanelProbability);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProcessActionPerformed
        try {
            jButtonProcess.setEnabled(false);
            String fileName = jTextFieldDataFilePath.getText();
            if (!fileName.equals("")) {
                // Clear any existing plot
                BorderLayout layout = (BorderLayout) jPanelProcess.getLayout();
                Component center = layout.getLayoutComponent(BorderLayout.CENTER);
                if (center != null) {
                    jPanelProcess.remove(center);
                    jPanelProcess.repaint();
                }

                //Check for results truncation
                boolean truncate = !jCheckBoxTruncate.isSelected();

                String[] args = {fileName};
                //run solution and draw graph
                SpeciesSelection specSel = new SpeciesSelection(args, truncate);
                Thread t = new Thread(specSel);
                t.start();
                long startTime = System.nanoTime();
                cancelled = false;
                jButtonCancelP.setVisible(true);

                new Thread() {
                    @Override
                    public void run() {
                        while (!specSel.isFinished() && !cancelled) {
                            double seconds = (System.nanoTime() - startTime) / 1000000000.0;
                            jLabelProcessTime.setText("Processing Time: " + toTimeString(seconds));
                            try {
                                Thread.sleep(91);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(SpecSelGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        jButtonCancelP.setVisible(false);
                        jButtonProcess.setEnabled(true);
                        // If processing finished then display result graph, else the process was cancelled so stop the processing thread.
                        if (specSel.isFinished()) {
                            ArrayList<Double> result = specSel.getResult();
                            drawGraph(result);
                        } else {
                            t.interrupt();
                        }
                    }
                }.start();

            } else {
                System.out.println("No file selected");
            }
        } catch (Exception e) {
            System.out.println("Error Processing Data: " + e);
        }
        System.out.println("Process Complete");

    }//GEN-LAST:event_jButtonProcessActionPerformed

    private String toTimeString(double seconds) {
        int hours = (int) seconds / 3600;
        int minutes = ((int) seconds % 3600) / 60;
        seconds = seconds % 60;
        DecimalFormat df1 = new DecimalFormat("00");
        DecimalFormat df2 = new DecimalFormat("00.00");
        String time = df1.format(hours) + ":"
                + df1.format(minutes) + ":"
                + df2.format(seconds);
        return time;
    }

    private void drawGraph(ArrayList<Double> meanSensitivities) {
        // Draw the graph to GUI
        JPanel panel = jPanelProcess;
        Dimension dim = new Dimension(600, 400);
        GraphPanel graph = new GraphPanel(meanSensitivities, "SpecSet Size", "Minimum Mean Sensitivity");
        graph.setPreferredSize(dim);
        panel.add(graph, BorderLayout.CENTER);
        pack();
        panel.setVisible(true);
    }

    private void jButtonSelectDataFilePActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectDataFilePActionPerformed
        selectDataFile(jTextFieldDataFilePath);
    }//GEN-LAST:event_jButtonSelectDataFilePActionPerformed

    private void selectDataFile(JTextField textField) {
        try {
            System.out.println("Loading data file");

            // prompt user to select text file containing album data
            String filename = getTextFilePath("LOAD");
            // proceed if file selected (not cancelled)
            if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
                textField.setText(filename);
            }
        } catch (Exception e) {
            System.out.println("Error Loading");
        }
    }

    private void jButtonCancelPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelPActionPerformed
        cancelled = true;
        jButtonCancelP.setVisible(false);
    }//GEN-LAST:event_jButtonCancelPActionPerformed

    private void jButtonSelectDataFileAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectDataFileAActionPerformed
        selectDataFile(jTextFieldDataFilePath1);
    }//GEN-LAST:event_jButtonSelectDataFileAActionPerformed

    private void jButtonProblemSpeciesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProblemSpeciesActionPerformed
        jButtonProblemSpecies.setEnabled(false);
        String fileName = jTextFieldDataFilePath1.getText();
        if (!fileName.equals("")) {
            try {
                jTextFieldProblemSpecies.setText("...");
                File file = new File(fileName);
                jSpinnerInitialSpeciesPct.commitEdit();
                int initialNoSpecies = (Integer) jSpinnerInitialSpeciesPct.getValue();
                jSpinnerAllowableExpDivergencePct.commitEdit();
                int expMarginPct = (Integer) jSpinnerAllowableExpDivergencePct.getValue();
                ProblemSpecies problemSpec = new ProblemSpecies(file, initialNoSpecies, expMarginPct, getOption());

                Thread t = new Thread(problemSpec);
                t.start();
                long startTime = System.nanoTime();
                cancelled = false;
                jButtonCancelA.setVisible(true);

                new Thread() {
                    @Override
                    public void run() {
                        double seconds;
                        while (!problemSpec.isFinished() && !cancelled) {
                            try {
                                seconds = (System.nanoTime() - startTime) / 1000000000.0;
                                jLabelProcessTimeA.setText("Processing Time: " + toTimeString(seconds));
                                Thread.sleep(91);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(SpecSelGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        // print results to GUI
                        jTextFieldProblemSpecies.setText(problemSpec.toString());
                        
                        jButtonCancelA.setVisible(false);
                        jButtonProblemSpecies.setEnabled(true);
                        // If processing finished then display results, else the process was cancelled so stop the processing thread.
                        if (problemSpec.isFinished()) {
                            PlotPoints points = problemSpec.getPoints();
                            jTextAreaPoints.setText(points.toString());
                        } else {
                            t.interrupt();
                        }
                    }
                }.start();
                
                // Create listener to update problem species on GUI when new species added to list
                problemSpec.addPropertyChangeListener(new PropertyChangeListener(){
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        jTextFieldProblemSpecies.setText(problemSpec.toString() + "...");
                    }
                });                
                
                // Create listener to update points on GUI when new points are added to list
                problemSpec.getPoints().addPropertyChangeListener(new PropertyChangeListener(){
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        jTextAreaPoints.setText(problemSpec.getPoints().toString());
                        jLabelProcessCompletedTimeA.setText("Last Process Complete: " + toTimeString((System.nanoTime() - startTime) / 1000000000.0));
                    }
                });

            } catch (ParseException | FileNotFoundException ex) {
                Logger.getLogger(SpecSelGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jButtonProblemSpeciesActionPerformed

    private void jButtonCancelAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelAActionPerformed
        cancelled = true;
        jButtonCancelA.setVisible(false);
    }//GEN-LAST:event_jButtonCancelAActionPerformed

    // launches JChooser and returns path of file selected for load or save
    private String getTextFilePath(String option) {
        // use JFileChooser to select album text file
        try {
            String filePath;
            // launch file chooser in current working directory
            JFileChooser fileChooser = new JFileChooser(workingDir);

            //lock file chooser to working directory
            final File dirToLock = new File(workingDir);
            fileChooser.setFileView(new FileView() {
                @Override
                public Boolean isTraversable(File f) {
                    return dirToLock.equals(f);
                }
            });

            // filter for text files
            FileFilter filter = new FileNameExtensionFilter("Text File", "txt");
            fileChooser.setFileFilter(filter);

            // initiate load or save dialog according to option parameter
            Component c1 = null;
            if (option.equals("LOAD")) {
                fileChooserResult = fileChooser.showOpenDialog(c1);
            }

            // get users selected file and directory location
            File file = fileChooser.getSelectedFile();
            if (file != null) {
                filePath = file.getAbsolutePath();
                workingDir = file.getParentFile().getAbsolutePath();
                return getFilenameFromPath(filePath);
            }
        } catch (Exception e) {
            System.err.println("Error in getTextFilePath(): " + e);
        }
        return null;
    }

    // Method to exact filename and extension from absolutepath as string
    private String getFilenameFromPath(String filePath) {
        return Paths.get(filePath).getFileName().toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SpecSelGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SpecSelGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SpecSelGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SpecSelGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SpecSelGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonCancelA;
    private javax.swing.JButton jButtonCancelP;
    private javax.swing.JButton jButtonProblemSpecies;
    private javax.swing.JButton jButtonProcess;
    private javax.swing.JButton jButtonSelectDataFileA;
    private javax.swing.JButton jButtonSelectDataFileP;
    private javax.swing.JCheckBox jCheckBoxTruncate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelProcessCompletedTimeA;
    private javax.swing.JLabel jLabelProcessTime;
    private javax.swing.JLabel jLabelProcessTimeA;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelAnalyse;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelProbability;
    private javax.swing.JPanel jPanelProcess;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JRadioButton jRadioButtonAll;
    private javax.swing.JRadioButton jRadioButtonFinal;
    private javax.swing.JRadioButton jRadioButtonNone;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerAllowableExpDivergencePct;
    private javax.swing.JSpinner jSpinnerInitialSpeciesPct;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextAreaKey;
    private javax.swing.JTextArea jTextAreaPoints;
    private javax.swing.JTextField jTextFieldDataFilePath;
    private javax.swing.JTextField jTextFieldDataFilePath1;
    private javax.swing.JTextField jTextFieldProblemSpecies;
    // End of variables declaration//GEN-END:variables

    private Options getOption() {
        if(jRadioButtonNone.isSelected())
        {
            return Options.NONE;
        }
        else if(jRadioButtonAll.isSelected())
        {
            return Options.ALL;
        }
        else if(jRadioButtonFinal.isSelected())
        {
            return Options.FINAL;
        }
        else{
            System.err.println("No Output Option Selected");
            return null;
        }
    }
}
