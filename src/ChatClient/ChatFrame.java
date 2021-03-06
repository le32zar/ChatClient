/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatClient;

import ChatServer.Message;
import ChatServer.MessageType;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author julia
 */
public class ChatFrame extends javax.swing.JFrame {
    
    private DefaultListModel roomListModel;
    private DefaultListModel peopleListModel;
    
    public Client clientInstance;
    
    /**
     * Creates new form ChatFrame
     */
    public ChatFrame() {
        roomListModel= new DefaultListModel();
        peopleListModel= new DefaultListModel();
        initComponents();
        this.setTitle("ChatClient: Username");
    }
    
    public static String getTime(boolean includeSeconds) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm" + ((includeSeconds)?":ss":"") );
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public void clearArea(){
        textAreaChat.setText("");
    }
    
    public void printChatArea(String msg){
        textAreaChat.append(msg + "\n");
    }
    
    public void updateList(){
        labelRoomName.setText(clientInstance.RoomName);
        roomListModel.clear();
        
        for(String room: clientInstance.RoomMap.keySet()){
            roomListModel.addElement(room);
        }   
        
        peopleListModel.clear();
       
        for (String person : clientInstance.RoomMap.get(clientInstance.RoomName)){
            peopleListModel.addElement(person);
        }
    }
    
    public void errorMsg(String text){
        JOptionPane.showMessageDialog(null, text, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        textAreaChat = new javax.swing.JTextArea();
        textFieldMessage = new javax.swing.JTextField();
        labelRoomName = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        userList = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        roomList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        buttonChangeRoom = new javax.swing.JButton();
        buttonSendMessage = new javax.swing.JButton();
        buttonOpenPrivate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel1.setText("Chatroom:");

        textAreaChat.setEditable(false);
        textAreaChat.setColumns(20);
        textAreaChat.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        textAreaChat.setRows(5);
        jScrollPane.setViewportView(textAreaChat);

        textFieldMessage.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        labelRoomName.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        labelRoomName.setToolTipText("");

        userList.setModel(peopleListModel );
        userList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(userList);

        roomList.setModel(roomListModel  );
        roomList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(roomList);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Rooms:");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Users in current chatroom:");

        buttonChangeRoom.setText("Change Room");
        buttonChangeRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChangeRoomActionPerformed(evt);
            }
        });

        buttonSendMessage.setText("Send");
        buttonSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSendMessageActionPerformed(evt);
            }
        });

        buttonOpenPrivate.setText("Open Private");
        buttonOpenPrivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOpenPrivateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelRoomName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonSendMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(buttonOpenPrivate)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(buttonChangeRoom))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelRoomName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonChangeRoom)
                            .addComponent(buttonOpenPrivate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonSendMessage))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       LoginDialog login = new LoginDialog(this, true);
       login.setVisible(true);
       
       if(clientInstance != null && clientInstance.ClientName != null) {
        this.setTitle("ChatClient: " + clientInstance.ClientName);
       }
    }//GEN-LAST:event_formWindowOpened

    private void buttonChangeRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonChangeRoomActionPerformed
        if(roomList.getSelectedIndex()==-1 || roomList.getSelectedValue().equals(clientInstance.RoomName)){
            return;
        }
        String roomName= roomList.getSelectedValue();
        Message msgRoom = new Message( MessageType.INTERNAL, clientInstance.ClientName, "server", "REQUEST_CHANGE_ROOM", roomName);
        clientInstance.sendMessage(msgRoom);
    }//GEN-LAST:event_buttonChangeRoomActionPerformed

    private void buttonSendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSendMessageActionPerformed
        String message = textFieldMessage.getText();
        if(!message.equals("")){
            Message msg = new Message(MessageType.ROOM, clientInstance.ClientName, clientInstance.RoomName, message);
            clientInstance.sendMessage(msg);
            clientInstance.printMessage(msg);
            textFieldMessage.setText("");
        }
    }//GEN-LAST:event_buttonSendMessageActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            clientInstance.close();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ChatFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void buttonOpenPrivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOpenPrivateActionPerformed
        ArrayList<String> partnerList = new ArrayList<>();
        
        for(String key : clientInstance.RoomMap.keySet()) {            
            for(String name : clientInstance.RoomMap.get(key)) {
                if(!name.equals(clientInstance.ClientName)) partnerList.add(name);
            }
        }
        
        OpenPrivateDialog dialog = new OpenPrivateDialog(this, true, partnerList);
        dialog.setVisible(true);
        
        if(dialog.SelectedPartner.equals("")) return;
        clientInstance.openPrivate(dialog.SelectedPartner);
    }//GEN-LAST:event_buttonOpenPrivateActionPerformed
    
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
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonChangeRoom;
    private javax.swing.JButton buttonOpenPrivate;
    private javax.swing.JButton buttonSendMessage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelRoomName;
    private javax.swing.JList<String> roomList;
    private javax.swing.JTextArea textAreaChat;
    private javax.swing.JTextField textFieldMessage;
    private javax.swing.JList<String> userList;
    // End of variables declaration//GEN-END:variables
}
