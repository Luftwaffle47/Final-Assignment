

import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class bmi_server
{
public JFrame mainFrame; static TextArea TxtArea = null;
  
bmi_server()
{
mainFrame = new JFrame("Server");
mainFrame.setSize(600,600);
mainFrame.setLayout(new GridLayout(3, 1));
mainFrame.addWindowListener(new WindowAdapter() {
public void windowClosing(WindowEvent windowEvent){
System.exit(0);
}
});   
TxtArea = new TextArea();
TxtArea.setBounds(20, 20, 450, 300);
mainFrame.setLayout(null);
mainFrame.add(TxtArea);
mainFrame.setLocationRelativeTo(null);
mainFrame.setVisible(true);
}
  
public static void main(String args[])
{
	Socket socket = null;
	try {
		new bmi_server();
		ServerSocket serverSocket = new ServerSocket(2525);
		TxtArea.append("\nServer Started at: "+new Date());
		
		while(true) //server will run 24/7
		{
			
			socket = serverSocket.accept();
			TxtArea.append("\nConnected to a client at: "+new Date());
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String WeightHeight = br.readLine();
			System.out.println(WeightHeight);

			
			Double w = Double.parseDouble(WeightHeight.substring(0, WeightHeight.indexOf(" ")));
			Double h = Double.parseDouble(WeightHeight.substring(WeightHeight.indexOf(" ")+1,WeightHeight.length()));
			TxtArea.append("\nWeight: "+w);
			TxtArea.append("\nHeight: "+h);

			w = w * 1;
			h = h * 1;
			Double sq = (h * h);
			DecimalFormat df = new DecimalFormat(".##");
			Double result = Double.parseDouble(df.format(w/sq));
			String message = "";
			if(result > 30){message = "Obese";}
			if(result > 25 && result < 29.9){message = "Overweight";}
			if(result > 18.5 && result < 24.9){message = "Normal";}
			if(result < 18.5){message = "Underweight";}

			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write("BMI is "+result+". "+message+"\n");
			TxtArea.append("\nBMI is "+result+". "+message);
			bw.flush();
		}
			} 	catch (Exception ex) {
		Logger.getLogger(bmi_server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}