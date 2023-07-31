import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
class ServerText extends JFrame
{
private ObjectOutputStream output;
private ObjectInputStream input;
private Socket connection;
private ServerSocket server;
private int totalClients = 50;
private int port = 8888;
String message="";
int n=0;
JFrame jf;
JPanel jPanel1;
JLabel status,jl;
JButton jButton1;
ServerText()
{
jf=new JFrame();
jf.setTitle("Server");
jf.setVisible(true);
jPanel1=new JPanel();
jButton1=new JButton();
status=new JLabel();
status.setVisible(true);
jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
jf.setResizable(false);
jPanel1.setBackground(new Color(238, 236, 236));
jPanel1.setLayout(null);
status.setForeground(new Color(216, 10,10));
status.setText("...");
jl=new JLabel("Pending");
jl.setBounds(200,250,100,100);
jl.setBackground(new Color(153, 255, 204));
jl.setFont(new Font("Times New Roman", 1, 21));
jf.add(jl);
jPanel1.add(status);
status.setBounds(10, 50, 300, 40);
status.setFont(new Font("Times New Roman", 1, 14));
jf.setSize(508, 441);
jf.setLocationRelativeTo(null);
jf.add(jPanel1);
}
public void startRunning()
{
try
{
server=new ServerSocket(port, totalClients);
while(true)
{
try
{
status.setText(" Waiting for Someone to Connect...");
connection=server.accept();		
status.setText(" Now Connected to  "+connection.getInetAddress().
getHostName());
output = new ObjectOutputStream(connection.getOutputStream());
output.flush();
input = new ObjectInputStream(connection.getInputStream());
while(true)
{
whileGenerating();
}
}catch(EOFException eofException){}
}
}
catch(IOException ioException)
{
ioException.printStackTrace();
}
}
private void whileGenerating() throws IOException
{ 
Random r=new Random();
int length=5;
StringBuffer captchaStringBuffer=new StringBuffer();
for(int i=0;i<length;i++)
{
int captchaNumber=Math.abs(r.nextInt())%60;
int charNumber=0;
if(captchaNumber<26)
{
charNumber=65+captchaNumber;
}
else if(captchaNumber<52)
{
charNumber=97+(captchaNumber-26);
}
else
{
charNumber=48+(captchaNumber-52);
}
captchaStringBuffer.append((char)charNumber);
}
message=captchaStringBuffer.toString();
sendMessage(message);	
}
private void sendMessage(String message)
{
try
{
String str="";
if(n!=0)
{
try
{
str=(String)input.readObject();
jl.setText(str);
}
catch(Exception e){}
}
if(!(str.equals("Verified"))){
output.writeObject(message);
n++;
}
}
catch(IOException ioException)
{
}
}
public static void main(String[] args) 
{
ServerText s=new ServerText();
s.startRunning();
}
}

