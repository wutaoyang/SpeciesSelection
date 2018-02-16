/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package speciesselection.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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
import preprocessing.ProblemSpecies;
import speciesselection.Species;
import speciesselection.SpeciesSelection;

/**
 *
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
        jButtonCancel.setVisible(false);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelMain = new javax.swing.JPanel();
        jPanelTop = new javax.swing.JPanel();
        jButtonSelectDataFile = new javax.swing.JButton();
        jTextFieldDataFilePath = new javax.swing.JTextField();
        jButtonProcess = new javax.swing.JButton();
        jCheckBoxTruncate = new javax.swing.JCheckBox();
        jButtonCancel = new javax.swing.JButton();
        jPanelBottom = new javax.swing.JPanel();
        jLabelProcessTime = new javax.swing.JLabel();
        jPanelAnalyse = new javax.swing.JPanel();
        jButtonSelectDataFile1 = new javax.swing.JButton();
        jTextFieldDataFilePath1 = new javax.swing.JTextField();
        jSpinnerInitialSpeciesPct = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerAllowableExpDiverencePct = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldProblemSpecies = new javax.swing.JTextField();
        jButtonProblemSpecies = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Species Selection ");

        jPanelMain.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanelMain.setLayout(new java.awt.BorderLayout());

        jPanelTop.setPreferredSize(new java.awt.Dimension(782, 60));

        jButtonSelectDataFile.setText("Select Data File");
        jButtonSelectDataFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectDataFileActionPerformed(evt);
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

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonSelectDataFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldDataFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxTruncate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonProcess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSelectDataFile)
                    .addComponent(jTextFieldDataFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonProcess)
                    .addComponent(jCheckBoxTruncate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelMain.add(jPanelTop, java.awt.BorderLayout.PAGE_START);

        jPanelBottom.setMinimumSize(new java.awt.Dimension(100, 30));
        jPanelBottom.setPreferredSize(new java.awt.Dimension(782, 30));

        jLabelProcessTime.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout jPanelBottomLayout = new javax.swing.GroupLayout(jPanelBottom);
        jPanelBottom.setLayout(jPanelBottomLayout);
        jPanelBottomLayout.setHorizontalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBottomLayout.createSequentialGroup()
                .addGap(0, 616, Short.MAX_VALUE)
                .addComponent(jLabelProcessTime, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelBottomLayout.setVerticalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelProcessTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanelMain.add(jPanelBottom, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Process", jPanelMain);

        jButtonSelectDataFile1.setText("Select Data File");
        jButtonSelectDataFile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectDataFile1ActionPerformed(evt);
            }
        });

        jTextFieldDataFilePath1.setText("Forest_D_ALL.txt");

        jSpinnerInitialSpeciesPct.setModel(new javax.swing.SpinnerNumberModel(14, 14, null, 1));

        jLabel1.setText("Initial No of Species:");

        jLabel2.setText("Allowable % Exp divergence:");

        jSpinnerAllowableExpDiverencePct.setModel(new javax.swing.SpinnerNumberModel(50, 1, 100, 1));

        jLabel3.setText("Problem Species:");

        jTextFieldProblemSpecies.setText("results");

        jButtonProblemSpecies.setText("Find Problem Species");
        jButtonProblemSpecies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProblemSpeciesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAnalyseLayout = new javax.swing.GroupLayout(jPanelAnalyse);
        jPanelAnalyse.setLayout(jPanelAnalyseLayout);
        jPanelAnalyseLayout.setHorizontalGroup(
            jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                        .addComponent(jButtonSelectDataFile1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDataFilePath1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                        .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSpinnerInitialSpeciesPct)
                            .addComponent(jSpinnerAllowableExpDiverencePct, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)))
                    .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldProblemSpecies, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonProblemSpecies))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanelAnalyseLayout.setVerticalGroup(
            jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAnalyseLayout.createSequentialGroup()
                .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSelectDataFile1)
                    .addComponent(jTextFieldDataFilePath1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jSpinnerInitialSpeciesPct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSpinnerAllowableExpDiverencePct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(jButtonProblemSpecies)
                .addGap(39, 39, 39)
                .addGroup(jPanelAnalyseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldProblemSpecies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(223, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Analyse", jPanelAnalyse);

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
                BorderLayout layout = (BorderLayout) jPanelMain.getLayout();
                Component center = layout.getLayoutComponent(BorderLayout.CENTER);
                if (center != null) {
                    jPanelMain.remove(center);
                    jPanelMain.repaint();
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
                jButtonCancel.setVisible(true);

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

                        jButtonCancel.setVisible(false);
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
        JPanel panel = jPanelMain;
        Dimension dim = new Dimension(600, 400);
        GraphPanel graph = new GraphPanel(meanSensitivities, "SpecSet Size", "Minimum Mean Sensitivity");
        graph.setPreferredSize(dim);
        panel.add(graph, BorderLayout.CENTER);
        pack();
        panel.setVisible(true);
    }

    private void jButtonSelectDataFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectDataFileActionPerformed
        selectDataFile(jTextFieldDataFilePath);
    }//GEN-LAST:event_jButtonSelectDataFileActionPerformed

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

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        cancelled = true;
        jButtonCancel.setVisible(false);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonSelectDataFile1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectDataFile1ActionPerformed
        selectDataFile(jTextFieldDataFilePath1);
    }//GEN-LAST:event_jButtonSelectDataFile1ActionPerformed

    private void jButtonProblemSpeciesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProblemSpeciesActionPerformed
        String fileName = jTextFieldDataFilePath1.getText();
        if (!fileName.equals("")) {
            try {
                File file = new File(fileName);
                jSpinnerInitialSpeciesPct.commitEdit();
                int initialNoSpecies = (Integer) jSpinnerInitialSpeciesPct.getValue();
                jSpinnerAllowableExpDiverencePct.commitEdit();
                int expMarginPct = (Integer) jSpinnerAllowableExpDiverencePct.getValue();
                ProblemSpecies problemSpec = new ProblemSpecies(file, initialNoSpecies, expMarginPct);
                                
                // print results to GUI
                jTextFieldProblemSpecies.setText(problemSpec.toString());
            } 
            catch (ParseException | FileNotFoundException ex) {
                Logger.getLogger(SpecSelGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_jButtonProblemSpeciesActionPerformed

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
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonProblemSpecies;
    private javax.swing.JButton jButtonProcess;
    private javax.swing.JButton jButtonSelectDataFile;
    private javax.swing.JButton jButtonSelectDataFile1;
    private javax.swing.JCheckBox jCheckBoxTruncate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelProcessTime;
    private javax.swing.JPanel jPanelAnalyse;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JSpinner jSpinnerAllowableExpDiverencePct;
    private javax.swing.JSpinner jSpinnerInitialSpeciesPct;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldDataFilePath;
    private javax.swing.JTextField jTextFieldDataFilePath1;
    private javax.swing.JTextField jTextFieldProblemSpecies;
    // End of variables declaration//GEN-END:variables
}
