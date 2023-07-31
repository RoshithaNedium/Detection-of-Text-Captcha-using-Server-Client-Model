import java.io.File;
import javafx.scene.media.Media;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class ServerVideo extends JFrame implements MouseListener {
private ObjectOutputStream output;
private ObjectInputStream input;
private Socket connection;
private ServerSocket server;
private int totalClients = 50;
private int port = 8888;
String str="";
String ans;
JLabel jLabel2;
int n=1;
JFrame jf;
JButton jButton1;
public ServerVideo() 
{
jf=new JFrame("Server");
jf.setVisible(true);
jf.setSize(400,400);
jf.setResizable(false);
jButton1 = new JButton();
jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
jButton1.setText("Send");
jButton1.setBounds(150,150,100,40);
jButton1.addMouseListener(this);
jf.add(jButton1);
jLabel2=new JLabel();
jLabel2.setFont(new Font("Times New Roman", 1,17));
jLabel2.setForeground(new Color(51, 0, 51));
jLabel2.setText("Waiting for Client");
jf.add(jLabel2);
jLabel2.setBounds(100,350,200, 40);
jf.setLocationRelativeTo(null);
pack();
try
{
server=new ServerSocket(port, totalClients);
while(true)
{
try
{
connection=server.accept();
output = new ObjectOutputStream(connection.getOutputStream());
output.flush();
input = new ObjectInputStream(connection.getInputStream());
}
catch(EOFException eofException)
{
}
}
}
catch(IOException ioException)
{
ioException.printStackTrace();
}
}
public void mousePressed(MouseEvent evt) 
{
System.out.println("Sent to Client");
Random random=new Random();
int rand;
String str1=jLabel2.getText();
rand=random.nextInt(5);
switch(rand)
{
case 1:
{
str="Time";
break;
}
case 2:
{
str="Tower";
break;
}
case 3:
{
str="Spoons";
break;
}
case 4:
{
str="Parachute";
break;
}
}
try
{
output.writeObject(str);
System.out.println(str);
ans=(String)input.readObject();
jLabel2.setText(ans);
}
catch(Exception e){}
str1=jLabel2.getText();
try {
for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
if ("Nimbus".equals(info.getName())) {
javax.swing.UIManager.setLookAndFeel(info.getClassName());
break;
}
}
} catch (ClassNotFoundException ex) {
java.util.logging.Logger.getLogger(ServerVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (InstantiationException ex) {
java.util.logging.Logger.getLogger(ServerVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (IllegalAccessException ex) {
java.util.logging.Logger.getLogger(ServerVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (javax.swing.UnsupportedLookAndFeelException ex) {
java.util.logging.Logger.getLogger(ServerVideo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
}
}

public void mouseClicked(MouseEvent me){};
public void mouseEntered(MouseEvent me){};
public void mouseExited(MouseEvent me){};
public void mouseReleased(MouseEvent me){};

public static void main(String args[])
{
ServerVideo l=new ServerVideo();
}
}
