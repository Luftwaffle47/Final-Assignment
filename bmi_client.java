import java.awt.GridLayout;
	import java.awt.TextArea;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.WindowAdapter;
	import java.awt.event.WindowEvent;
	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.OutputStream;
	import java.io.OutputStreamWriter;
	import java.net.InetAddress;
	import java.net.Socket;
	import java.net.UnknownHostException;
	import java.util.logging.Level;
	import java.util.logging.Logger;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JTextField;

	public class bmi_client
	{	//Instructions: run the bmi_server.java program first to launch the server for connection, then follow the instructions on the GUI
		//First input your weight in Kilograms and in the second box input your height in meters, then hit submit and BMI will be calculated
		//Lines 27-65 setup the GUI of this program to have an interface for the user to implement their height and weight
		public JFrame mainFrame; static TextArea TxtArea = null;
		bmi_client()
		{
			mainFrame = new JFrame("Client");
			mainFrame.setSize(600,600);
			mainFrame.setLayout(new GridLayout(3, 1));
			mainFrame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent windowEvent)
				{
					System.exit(0);
				}
	});  
			
			JLabel w = new JLabel();
			w.setText("Weight (in Kg): ");//Prompts the user to input weight in Kilograms
			w.setBounds(20, 20, 200, 20);
			JTextField wt = new JTextField(20);
			wt.setBounds(130, 20, 200, 20);
		JLabel h = new JLabel();
		h.setText("Height (in Meters): ");//Prompts the user to input height in Meters
		h.setBounds(20, 60, 200, 20);
		JTextField ht = new JTextField(20);
		ht.setBounds(130, 60, 200, 20);
		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(200, 100, 100, 20);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect(wt.getText(),ht.getText()); //pass all the form parameters here
			}
		});
		
		TxtArea = new TextArea();
		TxtArea.setBounds(20, 130, 450, 300);
		mainFrame.setLayout(null);
		mainFrame.add(w); mainFrame.add(wt);
		mainFrame.add(h); mainFrame.add(ht);
		mainFrame.add(submitButton); mainFrame.add(TxtArea);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		}
	  
	public static void main(String args[])
	{
		new bmi_client();
	}
	  
		public static void connect(String w, String h)
	{
			try {
				TxtArea.append("\nWeight: "+w);
				TxtArea.append("\nHeight: "+h);
				Socket socket = null;
				String host = "localhost";
				InetAddress address = InetAddress.getByName(host);
				socket = new Socket(address, 8000);


				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(osw);
				String WeightHeight = w+" "+h+"\n";
				bw.write(WeightHeight);
				bw.flush();

	
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String message = br.readLine();
				TxtArea.append("\n"+message);
	} 
			catch (UnknownHostException ex) {
				Logger.getLogger(bmi_client.class.getName()).log(Level.SEVERE, null, ex);
			} 
			catch (Exception ex) {
				Logger.getLogger(bmi_client.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}


