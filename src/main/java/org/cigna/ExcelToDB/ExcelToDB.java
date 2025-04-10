package org.cigna.ExcelToDB;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;

public class ExcelToDB {
    private JPanel panelMain;
    private JTextField textFieldFilePath;
    private JButton btnBrowseFile;
    private JComboBox<String> tableChoserLst;
    private JButton startTransactionButton;
    private JLabel fileNameLbl;
    private JLabel chooseTableLbl;

    private ExcelReader reader;
    private List<String> sheetNamesList;

    public ExcelToDB() {
        // Initialize panel and layout
        panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout(10, 10));
        panelMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize components
        fileNameLbl = new JLabel("Excel File:");
        chooseTableLbl = new JLabel("Select Sheet:");
        textFieldFilePath = new JTextField();
        textFieldFilePath.setEditable(false);
        btnBrowseFile = new JButton("Browse File");
        tableChoserLst = new JComboBox<>();
        startTransactionButton = new JButton("Import Sheet");

        // Use a sub-panel for file selection
        JPanel fileChooserPanel = new JPanel(new BorderLayout(5, 5));
        fileChooserPanel.add(fileNameLbl, BorderLayout.WEST);
        fileChooserPanel.add(textFieldFilePath, BorderLayout.CENTER);
        fileChooserPanel.add(btnBrowseFile, BorderLayout.EAST);

        // Use another sub-panel for sheet selection and import
        JPanel tableToInsertPanel = new JPanel(new BorderLayout(5, 10));
        tableToInsertPanel.add(chooseTableLbl, BorderLayout.WEST);
        tableToInsertPanel.add(tableChoserLst, BorderLayout.CENTER);
        tableToInsertPanel.add(startTransactionButton, BorderLayout.EAST);

        // Add sub-panels to main panel
        panelMain.add(fileChooserPanel, BorderLayout.NORTH);
        panelMain.add(tableToInsertPanel, BorderLayout.SOUTH);

        // Browse Button Action
        btnBrowseFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xls", "xlsx"));

            int result = fileChooser.showOpenDialog(panelMain);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                textFieldFilePath.setText(selectedFile.getAbsolutePath());
                String filePath = selectedFile.getAbsolutePath();

                try {
                    reader = new ExcelReader(filePath);
                    sheetNamesList = reader.getAllSheetNames();

                    // Clear and repopulate the combo box
                    tableChoserLst.removeAllItems();
                    for (String sheetName : sheetNamesList) {
                        tableChoserLst.addItem(sheetName);
                    }

                    if (!sheetNamesList.isEmpty()) {
                        tableChoserLst.setSelectedIndex(0);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelMain, "Error reading Excel file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Import Button Action
        startTransactionButton.addActionListener(e -> {
            String selectedSheet = (String) tableChoserLst.getSelectedItem();
            String filePath = textFieldFilePath.getText();

            if (filePath.isEmpty() || selectedSheet == null) {
                JOptionPane.showMessageDialog(panelMain, "Please select an Excel file and a sheet name.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                SheetData sheetData = reader.readSheet(selectedSheet);
                String sql = SQLBuilder.buildInsertQuery(selectedSheet, sheetData.getColumns());
                DatabaseInserter.insertData(selectedSheet, sheetData, sql);

                JOptionPane.showMessageDialog(panelMain, "Data loading from sheet '" + selectedSheet + "' has been started. Please check after some time");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelMain, "Import failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public JPanel getMainPanel() {
        return panelMain;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Excel to DB Importer");
            frame.setContentPane(new ExcelToDB().getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(650, 150);
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
        });
    }
}
