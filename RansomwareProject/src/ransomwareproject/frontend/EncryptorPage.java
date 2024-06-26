package ransomwareproject.frontend;

import aes.service.EncryptionService;
import aes.service.FileService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class EncryptorPage {

    private static final EncryptionService encryptionService = new EncryptionService();
    private static final FileService fileService = new FileService();

    // Constructor privado para evitar instanciación directa
    private EncryptorPage() {}

    // Método estático público para crear y mostrar la GUI
    public static void createAndShowGUI() {
        // Establecer estilo para los botones
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Button.background", Color.BLACK);
            UIManager.put("Button.foreground", Color.BLACK);
            UIManager.put("Button.font", new Font("Consolas", Font.PLAIN, 12));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Encriptador de Archivos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        Container container = frame.getContentPane();
        container.setLayout(new GridLayout(1, 1));

        JButton encryptButton = new JButton("Encriptar Archivo");
        JButton decryptButton = new JButton("Desencriptar Archivo");

        container.add(encryptButton);
        container.add(decryptButton);

        // Ajustar estilos de botón después de añadirlos al contenedor
        encryptButton.setFont(new Font("Consolas", Font.PLAIN, 12));
        decryptButton.setFont(new Font("Consolas", Font.PLAIN, 12));
        

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleFileOperation(true);
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleFileOperation(false);
            }
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private static void handleFileOperation(boolean isEncrypt) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String inputFilePath = selectedFile.getAbsolutePath();

            try {
                fileService.checkFileAccess(inputFilePath);
                String fileContent = fileService.readFile(inputFilePath);
                String outputFilePath;
                String resultContent;

                if (isEncrypt) {
                    resultContent = encryptionService.encrypt(fileContent);
                    outputFilePath = inputFilePath + ".encryptado.txt"; // Renombrar archivo encriptado
                } else {
                    resultContent = encryptionService.decrypt(fileContent);
                    outputFilePath = inputFilePath + ".desencryptado.txt"; // Renombrar archivo desencriptado
                }

                fileService.writeFile(outputFilePath, resultContent);
                JOptionPane.showMessageDialog(null, "Archivo procesado guardado en: " + outputFilePath);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}

