package entornos_desarrollo_practica;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import java.awt.Color;



public class Agenda extends JFrame implements ActionListener {

    private ArrayList<Contacto> contactos;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaContactos;
    private JButton botonAgregar;
    private JButton botonBorrar;
    private JButton botonEditar;
    private JButton botonBuscar;
    private JTextField campoNombre;
    private JTextField campoNumero;

    public Agenda() {
        super("Agenda");

        // Lista de contactos
        contactos = new ArrayList<Contacto>();

        // Modelo para la lista
        modeloLista = new DefaultListModel<String>();

        // Lista de contactos con el color
        listaContactos = new JList<String>(modeloLista);
        listaContactos.setBackground(new Color(228, 217, 21));

        //  Barra de desplazamiento
        JScrollPane scrollLista = new JScrollPane(listaContactos);
        scrollLista.setBounds(0, 43, 644, 268);

        //  Botones
        botonAgregar = new JButton("Agregar");
        botonBorrar = new JButton("Borrar");
        botonEditar = new JButton("Editar");
        botonEditar.setEnabled(false); // El botón "Editar" está desactivado al inicio
        botonBuscar = new JButton("Buscar");

        // Campos de nombre y numero
        campoNombre = new JTextField(20);
        campoNumero = new JTextField(20);

        // Etiquetas de los campos
        JLabel etiquetaNombre = new JLabel("Nombre:");
        JLabel etiquetaNumero = new JLabel("Numero:");

        // Paneles para botones y campos
        JPanel panelBotones = new JPanel();
        panelBotones.setBounds(0, 0, 644, 43);
        panelBotones.setBackground(new Color(255, 26, 5));
        panelBotones.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        JPanel panelCampos = new JPanel();
        panelCampos.setBounds(0, 311, 644, 36);
        panelCampos.setBackground(new Color(55, 232, 37));

        // Botones y campos para paneles
        panelBotones.add(botonAgregar);
        panelBotones.add(botonBorrar);
        panelBotones.add(botonEditar);
        panelBotones.add(botonBuscar);
        panelCampos.add(etiquetaNombre);
        panelCampos.add(campoNombre);
        panelCampos.add(etiquetaNumero);
        panelCampos.add(campoNumero);
        getContentPane().setLayout(null);

        // Agregar los paneles a la ventana
        getContentPane().add(panelBotones);
        getContentPane().add(scrollLista);
        getContentPane().add(panelCampos);
        
        botonAgregar.addActionListener(this);
        botonBorrar.addActionListener(this);
        botonEditar.addActionListener(this);
        botonBuscar.addActionListener(this);
        listaContactos.addListSelectionListener(e -> {
        // Si se selecciona un contacto de la lista es visible el boton
        botonEditar.setEnabled(!listaContactos.isSelectionEmpty());
        });

        // Ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(644, 375);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Agenda();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonAgregar) {
            // Obtener el nombre y  numero del contacto
            String numero = campoNumero.getText();
            String nombre = campoNombre.getText();

            // Verificar que se haya ingresado un nombre
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un nombre.");
                return;
            }

            // Agregar el contacto a la lista
            Contacto nuevoContacto = new Contacto(nombre, numero);
            contactos.add(nuevoContacto);

            // Actualizar la lista de contactos
            actualizarLista();

            // Limpiar los campos de texto
            campoNombre.setText("");
            campoNumero.setText("");
        } else if (e.getSource() == botonBorrar) {
            // Obtener el índice del contacto seleccionado
            int indiceSeleccionado = listaContactos.getSelectedIndex();

            // Verificar que se haya seleccionado un contacto
            if (indiceSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un contacto.");
                return;
            }

            // Borrar el contacto de la lista
            contactos.remove(indiceSeleccionado);

            // Actualizar la lista de contactos
            actualizarLista();
        } else if (e.getSource() == botonEditar) {
            // Obtener el indice del contacto seleccionado
            int indiceSeleccionado = listaContactos.getSelectedIndex();

            // Obtener el contacto seleccionado
            Contacto contactoSeleccionado = contactos.get(indiceSeleccionado);

            // Crear una ventana emergente para editar el contacto seleccionado
            JTextField campoNombreEditar = new JTextField(contactoSeleccionado.getNombre());
            JTextField campoNumeroEditar = new JTextField(contactoSeleccionado.getNumero());
            Object[] camposEditar = {
                    "Nombre:", campoNombreEditar,
                    "Numero:", campoNumeroEditar
            };
            int resultado = JOptionPane.showConfirmDialog(this, camposEditar, "Editar contacto", JOptionPane.OK_CANCEL_OPTION);
            if (resultado == JOptionPane.OK_OPTION) {
                // Actualizar el nombre y numero del contacto
                contactoSeleccionado.setNombre(campoNombreEditar.getText());
                contactoSeleccionado.setNotas(campoNumeroEditar.getText());

                // Actualizar la lista de contactos
                actualizarLista();
            }
        } else if (e.getSource() == botonBuscar) {
            // Obtener el nombre del contacto a buscar
            String nombreBuscar = campoNombre.getText();

            // Verificar que se haya ingresado un nombre
            if (nombreBuscar.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un nombre.");
                return;
            }

            // Buscar el contacto en la lista
            int indiceEncontrado = -1;
            for (int i = 0; i < contactos.size(); i++) {
                if (contactos.get(i).getNombre().equals(nombreBuscar)) {
                    indiceEncontrado = i;
                    break;
                }
            }
         // Mostrar el resultado de la búsqueda
            if (indiceEncontrado != -1) {
                listaContactos.setSelectedIndex(indiceEncontrado);
                listaContactos.ensureIndexIsVisible(indiceEncontrado);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún contacto con el nombre especificado.");
            }
        }
    }

    private void actualizarLista() {
        // Obtener la lista de nombres de los contactos
        String[] nombres = new String[contactos.size()];
        for (int i = 0; i < contactos.size(); i++) {
            nombres[i] = contactos.get(i).getNombre();
        }

        // Actualizar la lista de contactos
        listaContactos.setListData(nombres);
    }

    private class Contacto {
        private String nombre;
        private String numero;

        public Contacto(String nombre, String numero) {
            this.nombre = nombre;
            this.numero = numero;
        }

        public String getNombre() {
            return nombre;
        }
     

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getNumero() {
            return numero;
        }

        public void setNotas(String numero) {
            this.numero = numero;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }
}