import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//Clase ICentro
public class prueba extends JFrame {
    JPanel panel;
    JTextField descripcionField, cantidadField, costoField, fechaField, facturaField, cedulaField;

    public prueba() {
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Reporte y Control de Equipos en Centro de Investigación");
        setLocationRelativeTo(null);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);

        colocarEtiqueta();
        colocarCampos();
        colocarBotones();
    }

    private void colocarEtiqueta() {
        JLabel etiqueta = new JLabel("Ingrese data del equipo");
        etiqueta.setBounds(10, 10, 300, 30);
        panel.add(etiqueta);
    }

    private void colocarCampos() {
        JLabel descripcionLabel = new JLabel("Descripción:");
        descripcionLabel.setBounds(10, 50, 100, 30);
        panel.add(descripcionLabel);

        descripcionField = new JTextField();
        descripcionField.setBounds(110, 50, 360, 30);
        panel.add(descripcionField);

        JLabel cantidadLabel = new JLabel("Cantidad:");
        cantidadLabel.setBounds(10, 100, 100, 30);
        panel.add(cantidadLabel);

        cantidadField = new JTextField();
        cantidadField.setBounds(110, 100, 50, 30);
        panel.add(cantidadField);

        JLabel costoLabel = new JLabel("Costo Unitario (Bs):");
        costoLabel.setBounds(180, 100, 150, 30);
        panel.add(costoLabel);

        costoField = new JTextField();
        costoField.setBounds(330, 100, 140, 30);
        panel.add(costoField);

        JLabel fechaLabel = new JLabel("Fecha de adquisición:");
        fechaLabel.setBounds(10, 150, 150, 30);
        panel.add(fechaLabel);

        fechaField = new JTextField("dd/mm/aaaa");
        fechaField.setBounds(160, 150, 100, 30);
        panel.add(fechaField);

        JLabel facturaLabel = new JLabel("Nro. de Factura:");
        facturaLabel.setBounds(280, 150, 120, 30);
        panel.add(facturaLabel);

        facturaField = new JTextField();
        facturaField.setBounds(400, 150, 70, 30);
        panel.add(facturaField);

        JLabel cedulaLabel = new JLabel("C.I del Responsable:");
        cedulaLabel.setBounds(10, 200, 150, 30);
        panel.add(cedulaLabel);

        cedulaField = new JTextField();
        cedulaField.setBounds(160, 200, 120, 30);
        panel.add(cedulaField);
    }

    private void colocarBotones() {
        JButton botonRegistrar = new JButton("Registrar data");
        botonRegistrar.setBounds(200, 270, 130, 30);
        panel.add(botonRegistrar);

        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarData();
            }
        });

        JButton botonReporte = new JButton("Generar Reporte");
        botonReporte.setBounds(340, 270, 130, 30);
        panel.add(botonReporte);

        botonReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaReporte();
            }
        });
    }

    private void registrarData() {
        String descripcion = descripcionField.getText();
        String cantidad = cantidadField.getText();
        String costo = costoField.getText();
        String fecha = fechaField.getText();
        String factura = facturaField.getText();
        String cedula = cedulaField.getText();

        if (descripcion.isEmpty() || cantidad.isEmpty() || costo.isEmpty() || fecha.isEmpty() || factura.isEmpty() || cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] fechaPartes = fecha.split("/");
        if (fechaPartes.length != 3 || fechaPartes[0].length() != 2 || fechaPartes[1].length() != 2 || fechaPartes[2].length() != 4) {
            JOptionPane.showMessageDialog(this, "El formato de la fecha debe ser dd/mm/aaaa.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String linea = String.format("%s#%s#%s#\"%s\"/\"%s\"/\"%s\"#%s#%s\n", 
            descripcion, cantidad, costo, fechaPartes[0], fechaPartes[1], fechaPartes[2], factura, cedula);
            limpiar();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventario.txt", true))) {
            writer.write(linea);
            JOptionPane.showMessageDialog(this, "Datos registrados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al escribir en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {
        descripcionField.setText("");
        cantidadField.setText("");
        costoField.setText("");
        fechaField.setText("");
        facturaField.setText("");
        cedulaField.setText("");
    }

    public static void main(String[] args) {
        prueba v1 = new prueba();
        v1.setVisible(true);
    }

        class VentanaReporte extends JFrame {
        public VentanaReporte() {
            setSize(600, 500);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setTitle("Generar Reporte");
            setLocationRelativeTo(null);
            iniciarComponentes();
            setVisible(true);
        }

        private void iniciarComponentes() {
            JPanel panelReporte = new JPanel();
            panelReporte.setLayout(null);
            this.getContentPane().add(panelReporte);

            // RadioBotones
            JRadioButton reporteGeneral = new JRadioButton("Reporte General");
            reporteGeneral.setBounds(30, 30, 150, 30);
            panelReporte.add(reporteGeneral);

            JRadioButton reporteIndividual = new JRadioButton("Reporte Individual");
            reporteIndividual.setBounds(200, 30, 150, 30);
            panelReporte.add(reporteIndividual);

            ButtonGroup grupo = new ButtonGroup();
            grupo.add(reporteGeneral);
            grupo.add(reporteIndividual);

            // Campo para C.I. y su botón
            JLabel ciLabel = new JLabel("C.I. del responsable de equipos:");
            ciLabel.setBounds(30, 80, 200, 30);
            panelReporte.add(ciLabel);

            JTextField ciField = new JTextField();
            ciField.setBounds(230, 80, 150, 30);
            panelReporte.add(ciField);

            JButton botonTotalizar = new JButton("Totalizar");
            botonTotalizar.setBounds(400, 80, 100, 30);
            panelReporte.add(botonTotalizar);

            // Tabla para mostrar datos generales
            String[] columnas = { "C.I. Responsable", "Cantidad equipos", "Monto Total" };
            JTable tabla = new JTable(new Object[0][3], columnas);
            JScrollPane scrollPane = new JScrollPane(tabla);
            scrollPane.setBounds(30, 150, 520, 200);
            panelReporte.add(scrollPane);

            // Área para mostrar totales
            JTextArea resultadoArea = new JTextArea();
            resultadoArea.setBounds(30, 370, 300, 60);
            resultadoArea.setEditable(false);
            panelReporte.add(resultadoArea);

            // Botón "Continuar"
            JButton botonContinuar = new JButton("Continuar");
            botonContinuar.setBounds(450, 400, 100, 30);
            panelReporte.add(botonContinuar);

            botonTotalizar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (reporteIndividual.isSelected()) {
                        String ci = ciField.getText();
                        if (ci.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Por favor, ingrese la C.I.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        totalizarPorCI(ci, resultadoArea, tabla);
                    } else if (reporteGeneral.isSelected()) {
                        generarReporteGeneral(resultadoArea, tabla);
                    } else {
                        JOptionPane.showMessageDialog(null, "Seleccione un modo de reporte.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            botonContinuar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }

        private void totalizarPorCI(String ci, JTextArea resultadoArea, JTable tabla) {
            try (BufferedReader reader = new BufferedReader(new FileReader("inventario.txt"))) {
                String linea;
                int totalEquipos = 0;
                double costoTotal = 0;

                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split("#");
                    if (partes.length == 7 && partes[6].equals(ci)) {
                        totalEquipos += Integer.parseInt(partes[1]);
                        costoTotal += Integer.parseInt(partes[1]) * Double.parseDouble(partes[2]);
                    }
                }

                resultadoArea.setText("Total de equipos: " + totalEquipos + "\nCosto total: Bs " + costoTotal);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void generarReporteGeneral(JTextArea resultadoArea, JTable tabla) {
            try (BufferedReader reader = new BufferedReader(new FileReader("inventario.txt"))) {
                String linea;
                int totalEquipos = 0;
                double costoTotal = 0;

                // Para calcular totales por C.I.
                Map<String, int[]> datosCI = new HashMap<>(); // {CI: [cantidad, monto total]}

                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split("#");
                    if (partes.length == 7) {
                        int cantidad = Integer.parseInt(partes[1]);
                        double costo = cantidad * Double.parseDouble(partes[2]);
                        String ci = partes[6];

                        totalEquipos += cantidad;
                        costoTotal += costo;

                        datosCI.putIfAbsent(ci, new int[2]);
                        datosCI.get(ci)[0] += cantidad;
                        datosCI.get(ci)[1] += costo;
                    }
                }

                // Mostrar totales generales
                resultadoArea.setText("Total de equipos: " + totalEquipos + "\nCosto total: Bs " + costoTotal);

                // Mostrar datos en tabla
                Object[][] datosTabla = new Object[datosCI.size()][3];
                int index = 0;
                for (Map.Entry<String, int[]> entry : datosCI.entrySet()) {
                    datosTabla[index][0] = entry.getKey();
                    datosTabla[index][1] = entry.getValue()[0];
                    datosTabla[index][2] = entry.getValue()[1];
                    index++;
                }

                tabla.setModel(new DefaultTableModel(datosTabla, new String[] { "C.I. Responsable", "Cantidad equipos", "Monto Total" }));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
}

