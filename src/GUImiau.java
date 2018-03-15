
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.Registro;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ARENDONCL
 */
public final class GUImiau extends javax.swing.JFrame {

    String posicionMonstruo;
    Registro comp;
    int puntos = 1;
    
    public GUImiau() throws RemoteException, UnknownHostException {

        initComponents();
        listenUD hilo = new listenUD();
        String name = "Registro";
        Registry registry = LocateRegistry.getRegistry("localhost"); // server's ip address
        try {
            this.comp = (Registro) registry.lookup(name);
            try {
                comp.registrar(Inet4Address.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Tengo el registro");
        } catch (NotBoundException ex) {
            Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex) {
            Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        hilo.start();

    }

    class listenUD extends Thread {

        @Override
        public void run() {
            javax.swing.JCheckBox[][] botones = {{cb00, cb01, cb02}, {cb10, cb11, cb12}, {cb20, cb21, cb22}};
            MulticastSocket s = null;
            try {
                InetAddress group = InetAddress.getByName("228.5.6.7"); // destination multicast group 
                s = new MulticastSocket(6789);
                
               
            
            
                byte[] buffer = new byte[1000];
                int ant0 = 0;
                int ant1 = 0;
                s.joinGroup(group);
                for (int i = 0; i < 100; i++) {
                    
                    System.out.println("Waiting for messages");
                    DatagramPacket messageIn
                            = new DatagramPacket(buffer, buffer.length);
                    s.receive(messageIn);
                    String posicionRecibida = new String(messageIn.getData());
                    posicionMonstruo = posicionRecibida;
                    char[] splited = posicionRecibida.toCharArray();
                    int pos0 = Character.getNumericValue(splited[0]);
                    int pos1 = Character.getNumericValue(splited[1]);
                    botones[pos0][pos1].setSelected(true);
                    if (i != 0 && (ant0 != pos0 || ant1 != pos1)) {
                        botones[ant0][ant1].setSelected(false);
                    }
                    ant0 = pos0;
                    ant1 = pos1;

                    System.out.println("Posiciones: x=" + pos0 + " y=" + pos1);
                    //    System.out.println(posicionRecibida);
                   
                }
                 s.leaveGroup(group);
                
            } catch (SocketException e) {
                System.out.println("Socket: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
            } finally {
                if (s != null) {
                    s.close();
                }
            }

        }
    }


    public void startButtons(String quienSoy) {
        Socket s = null;
        try {
            
            
            int serverPort = 7896;

            s = new Socket("localhost", serverPort);
            //   s = new Socket("127.0.0.1", serverPort);    
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out
                    = new DataOutputStream(s.getOutputStream());
            
            comp.sumarPuntos(quienSoy);
            out.writeUTF(quienSoy);        	// UTF is a string encoding 
             
            String data = in.readUTF();
            System.out.println("Received: " + data);
        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
    }
    
    public boolean ganas(String posicionActual){
        return posicionActual.equals(posicionMonstruo.trim());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cb00 = new javax.swing.JCheckBox();
        cb01 = new javax.swing.JCheckBox();
        cb02 = new javax.swing.JCheckBox();
        cb10 = new javax.swing.JCheckBox();
        cb11 = new javax.swing.JCheckBox();
        cb12 = new javax.swing.JCheckBox();
        cb20 = new javax.swing.JCheckBox();
        cb21 = new javax.swing.JCheckBox();
        cb22 = new javax.swing.JCheckBox();
        label1 = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        label3 = new javax.swing.JLabel();
        label4 = new javax.swing.JLabel();

        label2.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));

        cb00.setBackground(new java.awt.Color(255, 153, 153));
        cb00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb00ActionPerformed(evt);
            }
        });

        cb01.setBackground(new java.awt.Color(255, 153, 153));
        cb01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb01ActionPerformed(evt);
            }
        });

        cb02.setBackground(new java.awt.Color(255, 153, 153));
        cb02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb02ActionPerformed(evt);
            }
        });

        cb10.setBackground(new java.awt.Color(255, 153, 153));
        cb10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb10ActionPerformed(evt);
            }
        });

        cb11.setBackground(new java.awt.Color(255, 153, 153));
        cb11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb11ActionPerformed(evt);
            }
        });

        cb12.setBackground(new java.awt.Color(255, 153, 153));
        cb12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb12ActionPerformed(evt);
            }
        });

        cb20.setBackground(new java.awt.Color(255, 153, 153));
        cb20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb20ActionPerformed(evt);
            }
        });

        cb21.setBackground(new java.awt.Color(255, 153, 153));
        cb21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb21ActionPerformed(evt);
            }
        });

        cb22.setBackground(new java.awt.Color(255, 153, 153));
        cb22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb22ActionPerformed(evt);
            }
        });

        label.setText("jLabel1");

        label3.setText("jLabel1");

        label4.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cb20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cb21))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cb10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cb11))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cb00)
                                .addGap(46, 46, 46)
                                .addComponent(cb01)))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb02)
                            .addComponent(cb12)
                            .addComponent(cb22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label4)
                    .addComponent(label3)
                    .addComponent(label))
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb01)
                    .addComponent(cb00)
                    .addComponent(cb02))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb10)
                    .addComponent(cb11)
                    .addComponent(cb12))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cb20)
                            .addComponent(cb21)
                            .addComponent(cb22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(label3)
                                .addGap(18, 18, 18)
                                .addComponent(label)
                                .addGap(0, 20, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label4)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cb22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb22ActionPerformed
        // TODO aDatagramSocket aSocket = null;
        
        if(ganas("22")){
            try {
                startButtons(Inet4Address.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            cb22.setSelected(false);
        }
    }//GEN-LAST:event_cb22ActionPerformed

    private void cb02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb02ActionPerformed
        // TODO add your handling code here:
       
        if(ganas("02")){
            try {
                startButtons(Inet4Address.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            cb02.setSelected(false);
        }

    }//GEN-LAST:event_cb02ActionPerformed

    private void cb12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb12ActionPerformed
        // TODO add your handling code here:
        
         if(ganas("12")){
            try {
                startButtons(Inet4Address.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            cb12.setSelected(false);
        }

    }//GEN-LAST:event_cb12ActionPerformed

    private void cb00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb00ActionPerformed
        // TODO add your handling code here:
        
         if(ganas("00")){
            try {
                startButtons(Inet4Address.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            cb00.setSelected(false);
        }

    }//GEN-LAST:event_cb00ActionPerformed

    private void cb01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb01ActionPerformed
        // TODO add your handling code here:
        
         if(ganas("01")){
            try {
                startButtons(Inet4Address.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            cb01.setSelected(false);
        }

    }//GEN-LAST:event_cb01ActionPerformed

    private void cb10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb10ActionPerformed
        // TODO add your handling code here:
        
         if(ganas("10")){
            try {
                startButtons(Inet4Address.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            cb10.setSelected(false);
        }

    }//GEN-LAST:event_cb10ActionPerformed

    private void cb11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb11ActionPerformed
        // TODO add your handling code here:
        
         if(ganas("11")){
            try {
                startButtons(Inet4Address.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            cb11.setSelected(false);
        }

    }//GEN-LAST:event_cb11ActionPerformed

    private void cb20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb20ActionPerformed
        // TODO add your handling code here:
        
         if(ganas("20")){
            try {
                startButtons(Inet4Address.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            cb20.setSelected(false);
        }

    }//GEN-LAST:event_cb20ActionPerformed

    private void cb21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb21ActionPerformed
        // TODO add your handling code here:
        
        if(ganas("21")){
            try {
                startButtons(Inet4Address.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            cb21.setSelected(false);
        }

    }//GEN-LAST:event_cb21ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GUImiau().setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(GUImiau.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cb00;
    private javax.swing.JCheckBox cb01;
    private javax.swing.JCheckBox cb02;
    private javax.swing.JCheckBox cb10;
    private javax.swing.JCheckBox cb11;
    private javax.swing.JCheckBox cb12;
    private javax.swing.JCheckBox cb20;
    private javax.swing.JCheckBox cb21;
    private javax.swing.JCheckBox cb22;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel label4;
    // End of variables declaration//GEN-END:variables
}
