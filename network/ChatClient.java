package p0121.network;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class ChatClient extends JFrame{
	JPanel p_north;
	Choice ch_ip;
	JTextField t_port;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	JTextField t_input;
	int port=8888;
	Socket client;//대화용 소켓
	ClientThread ct;
	
	public ChatClient() {
		p_north = new JPanel();
		ch_ip=new Choice(); 
		t_port = new JTextField( Integer.toString(port),8);
		bt = new JButton("접속");
		area = new JTextArea();
		scroll  =new JScrollPane(area);
		t_input = new JTextField();
		
		//사용할 ip
		ch_ip.add("127.0.0.1");//나 자신
		ch_ip.add("192.168.0.200");//교사 pc
		
		p_north.add(ch_ip);
		p_north.add(t_port);
		p_north.add(bt);
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		add(t_input, BorderLayout.SOUTH);
		
		//font size 
		area.setFont(new Font("돋움",Font.BOLD,23));
		
		//버튼과 리스너 연결 
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		//텍스트필드와 리스너 연결 
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				
				if(key ==KeyEvent.VK_ENTER) {
					ct.send(t_input.getText());
					t_input.setText("");
				}
			}
		});
		
		setSize(400,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void connect() {
		String ip=ch_ip.getSelectedItem();
		port=Integer.parseInt(t_port.getText());
		
		//소켓 생성시 접속이 발생!!
		try {
			client = new Socket(ip, port);
			//대화용 쓰레드 생성!!!
			ct = new ClientThread(this,client);
			ct.start();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	public static void main(String[] args) {
		new ChatClient();
	}
}







