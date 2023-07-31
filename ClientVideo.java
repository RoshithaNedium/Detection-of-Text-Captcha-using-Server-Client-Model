import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
public class ClientVideo extends JFrame implements ActionListener
{
private final JFXPanel jfxPanel = new JFXPanel();
MediaPlayer oracleVid;
private ObjectOutputStream output;
private ObjectInputStream input;
private String serverIP;
private Socket connection;
private int port=8888;
JPanel jPanel1;
JButton PR,jb;
JLabel jLabel2,jLabel3,tlabel,tTlabel,Elabel;
JSlider VolumeSlider,bP;
JTextField aft;
boolean control1=false;
String str,m,s;
String rand;
public ClientVideo()
{
initComponents();
try{
try{
connection = new Socket(InetAddress.getByName(serverIP),port);
}
catch(IOException ioEception) {
JOptionPane.showMessageDialog(null,"Server Might Be Down!","Warning",JOptionPane.WARNING_MESSAGE);
}
output = new ObjectOutputStream(connection.getOutputStream());
output.flush(); 
input = new ObjectInputStream(connection.getInputStream());
try{
s=(String)input.readObject();  
}
catch(Exception e){}
}catch(IOException ioException) {
ioException.printStackTrace(); 
}
str="C://Users//dell//Videos//"+s+".MP4";
File file=new File(str);
System.out.println(file.isFile());
Media media=new Media(file.toURI().toString());
createScene(media);
serverIP="127.0.0.1";
jPanel1.setLayout(new BorderLayout());
jPanel1.add(jfxPanel,BorderLayout.CENTER);
setPreferredSize(new Dimension(600, 600));
setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
setTitle("Video Client");
setResizable(false);
setLocationRelativeTo(null);
}
Thread thread=new Thread(){
@Override
public void run(){
try{
while(true){
Thread.sleep(1000);
setValues();
}
}catch(InterruptedException e){}
}
};
public void setValues(){
double sec=oracleVid.getCurrentTime().toSeconds();
int val=(int)sec;
bP.setValue(val);
tTlabel.setText(arrange(val));
}
@SuppressWarnings("unchecked")
void initComponents()
{
jPanel1=new JPanel();
PR=new JButton();
jb=new JButton("Submit");
bP=new JSlider();
VolumeSlider=new JSlider();
tTlabel=new JLabel();
tlabel=new JLabel();
jLabel3=new JLabel();
Elabel=new JLabel("What can you see in the video?");
Elabel.setFont(new Font("Times New Roman", 1, 17)); 
aft=new JTextField(20);
add(Elabel);
Elabel.setBounds(20,380,350,30);
add(aft);
aft.setBounds(20,420,100,30);
PR.setText("Pause");
tTlabel.setText("00:00:00");
tlabel.setText("00:00:00");
jLabel3.setText("Volume");
jLabel2=new JLabel();
jLabel2.setFont(new Font("Times New Roman", 1,17));
jLabel2.setForeground(new Color(51, 0, 51));
jLabel2.setText("Pending..");
jLabel2.setToolTipText("");
add(jLabel2);
jLabel2.setBounds(240, 510, 150, 40);
jb.setBounds(230,480,100,35);
jb.setFont(new Font("Times New Roman", 1, 20)); 
jb.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
jb.addActionListener(this);
add(jb);
PR.addMouseListener(new java.awt.event.MouseAdapter() {
public void mousePressed(java.awt.event.MouseEvent evt) {
PRMousePressed(evt);
}
});
bP.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseReleased(java.awt.event.MouseEvent evt) {
bPMouseReleased(evt);
}
});
VolumeSlider.addMouseListener(new java.awt.event.MouseAdapter() {
public void mouseReleased(java.awt.event.MouseEvent evt) {
VolumeSliderMouseReleased(evt);
}
});
addWindowListener(new java.awt.event.WindowAdapter() {
public void windowClosed(java.awt.event.WindowEvent evt) {
formWindowClosed(evt);
}
});
jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
jPanel1.setSize(600,600);
GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
jPanel1.setLayout(jPanel1Layout);
jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 500, Short.MAX_VALUE));
jPanel1Layout.setVerticalGroup(
jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 500, Short.MAX_VALUE));
GroupLayout layout = new GroupLayout(getContentPane());
getContentPane().setLayout(layout);
layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addGroup(layout.createSequentialGroup()
.addContainerGap()
.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
.addComponent(tTlabel)
.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addComponent(PR, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
.addGap(70, 70, 70)
.addComponent(jLabel3)
.addGap(10, 10, 10)
.addComponent(VolumeSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
.addGap(40, 40, 40)
.addComponent(tlabel))
.addComponent(bP, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))    
.addContainerGap()));
layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
.addGroup(layout.createSequentialGroup()
.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
.addComponent(bP, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
.addComponent(PR)
.addComponent(tlabel)
.addComponent(jLabel3))
.addComponent(tTlabel)
.addComponent(VolumeSlider, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
.addContainerGap(7, Short.MAX_VALUE)
));
pack();
}
private void createScene(Media file)
{
Platform.runLater(new Runnable(){
@Override
public void run(){
StackPane root=new StackPane();
oracleVid=new MediaPlayer(file);
oracleVid.setAutoPlay(true);
MediaView theView=new MediaView(oracleVid);
root.getChildren().add(theView);
Scene Scene=new Scene(root,500,300);
jfxPanel.setScene(Scene);
}
});
setComponentValues();
}
private void setComponentValues()
{
try{
Thread.sleep(1000);
VolumeSlider.setMinimum(0);
VolumeSlider.setMaximum(10);
double sec=oracleVid.getTotalDuration().toSeconds();
int val=(int)sec;
bP.setMaximum(val);
tlabel.setText(arrange(val)+"");
thread.start();
} catch (InterruptedException ex) {
Logger.getLogger(ClientVideo.class.getName()).log(Level.SEVERE, null, ex);
}
}
public String arrange(int val)
{
int hours=0;
int minutes=0;
while(val>59){
if(val>59){
val-=60;
minutes++;
}
if(minutes>59){
minutes-=60;
hours++;
}
}
String hr=hours+"";
String min=minutes+"";
String sec=val+"";
if(hours<10){
hr="0"+hr;
}
if(minutes<10){
min="0"+min;
}
if(val<10){
sec="0"+sec;
}
return (hr+":"+min+":"+sec);
}
private void bPMouseReleased(java.awt.event.MouseEvent evt) {
int val = bP.getValue();
oracleVid.seek(Duration.seconds(val));
}
private void VolumeSliderMouseReleased(java.awt.event.MouseEvent evt) {
double val = VolumeSlider.getValue();
val *= 0.1;
oracleVid.setVolume(val);
}
boolean control=true;
private void PRMousePressed(java.awt.event.MouseEvent evt) {
if (control) {
PR.setText("Resume");
oracleVid.pause();
control = false;
} else if (!control) {
PR.setText("Pause");
oracleVid.play();
control= true;
}
}
public void actionPerformed(ActionEvent ae)
{
String sen=aft.getText();
if(s.equals("Time")&&sen.equals("Time Never Stops"))
{
jLabel2.setText("Verified");
}
else if(s.equals("Tower")&&sen.equals("Paris"))
{
jLabel2.setText("Verified");
}
else if(s.equals("Spoons")&&sen.equals("Spoons"))
{
jLabel2.setText("Verified");
}
else if(s.equals("Parachute")&&sen.equals("Parachute"))
{
jLabel2.setText("Verified");
}
else
{
jLabel2.setText("Invalid user");
}
try{
String ver=jLabel2.getText();
output.writeObject(ver);
}
catch(Exception e){}
}
public void formWindowClosed(java.awt.event.WindowEvent evt)
{
oracleVid.dispose();
}
public static void main(String args[])
{
/*EventQueue.invokeLater(new Runnable() {
public void run() {
ClientVideo c=new ClientVideo();
c.setVisible(true);
}
});*/
ClientVideo c=new ClientVideo();
c.setVisible(true);
}
}
