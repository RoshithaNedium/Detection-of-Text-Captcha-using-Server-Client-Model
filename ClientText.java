import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class ClientText extends JFrame implements ActionListener
{
private ObjectOutputStream output;
private ObjectInputStream input;
private String message="";
private String m="";
private String serverIP;
private Socket connection;
private int port = 8888;
int n=0;
JFrame jf;
private JButton jButton1;
private JLabel jLabel1;
private JLabel jLabel2;
private JScrollPane jScrollPane1;
private JTextField jTextField1;
private JPanel jPanel1;
private JTextArea chatArea;
private JLabel status;
ClientText(String s)
{
jf=new JFrame();
jPanel1 = new JPanel();
jScrollPane1 = new JScrollPane();
chatArea = new JTextArea();
jTextField1 = new JTextField();
jButton1 = new JButton();
status = new JLabel();
jLabel2 = new JLabel();
jLabel1 = new JLabel();
jf.setTitle("Client");
jf.setVisible(true);
status.setVisible(true);
serverIP = s;
jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
jf.setResizable(false);
jPanel1.setBackground(new Color(238, 236, 236));
jPanel1.setLayout(null);
chatArea.setColumns(7);
chatArea.setRows(1);
chatArea.setFont(new Font("Times New Roman", 1, 18));
jScrollPane1.setViewportView(chatArea);
jPanel1.add(jScrollPane1);
jScrollPane1.setBounds(10, 150, 125, 30);
jPanel1.add(jTextField1);
jTextField1.setBounds(10, 200, 130, 30);
jTextField1.setFont(new Font("Times New Roman", 1, 18));
jButton1.setBackground(new Color(153, 204, 255));
jButton1.setFont(new Font("Times New Roman", 1, 20)); // NOI18N
jButton1.setText("Submit");
jButton1.setBounds(180,285,135,35);
jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
jButton1.addActionListener(this);
jPanel1.add(jButton1);
status.setForeground(new Color(216, 10,10));
status.setText("...");
jPanel1.add(status);
status.setBounds(10, 30, 300, 40);
jLabel2.setFont(new Font("Times New Roman", 1, 20));
jLabel2.setForeground(new Color(51, 0, 51));
jLabel2.setText("Pending..");
jLabel2.setToolTipText("");
jPanel1.add(jLabel2);
jLabel2.setBounds(210, 340, 150, 40);
jLabel1.setBackground(new Color(153, 255, 204));
jLabel1.setFont(new Font("Times New Roman", 1, 21));
jLabel1.setText("Enter captcha"); 
jPanel1.add(jLabel1);
jLabel1.setBounds(10,90, 200, 40);
jf.setSize(508, 441);
jf.setLocationRelativeTo(null);
jf.add(jPanel1);
}
public void actionPerformed(ActionEvent ae)
{
String str=jTextField1.getText();
if(str.equals(message))
{
jLabel2.setForeground(new Color(0, 100, 0));
jLabel2.setText("Verified");
}
else
{
n=n+1;
jLabel2.setForeground(new Color(100, 0, 0));
jLabel2.setText("Wrong input");
jTextField1.setText("");
}
m=jLabel2.getText();
try
{
output.writeObject(m);
}
catch(Exception e){}
}
public void startRunning()
{
try
{
status.setText("Attempting Connection ...");
try
{
connection = new Socket(InetAddress.getByName(serverIP),port);
}catch(IOException ioEception)
{
JOptionPane.showMessageDialog(null,"Server Might Be Down!","Warning",JOptionPane.WARNING_MESSAGE);
}
status.setText("Connected to: " + connection.getInetAddress().getHostName());
output = new ObjectOutputStream(connection.getOutputStream());
output.flush();
input = new ObjectInputStream(connection.getInputStream());
try
{
output.writeObject(m);
}
catch(Exception e){}
whileReceiving();
}
catch(IOException ioException)
{
ioException.printStackTrace();
}
}
private void whileReceiving() throws IOException
{
String s="";   
jTextField1.setEditable(true);
try{
do{
message = (String) input.readObject();
chatArea.setText(message);
if(n>0)
{
jLabel1.setText("Re-Enter captcha");
}
s=jLabel2.getText();
}while(!(s=="Verified"));
}
catch(Exception e)
{
}
}
public static void main(String[] args) 
{
ClientText c=new ClientText("127.0.0.1");
c.startRunning();
}
}
