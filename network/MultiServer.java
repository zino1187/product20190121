package p0121.network;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MultiServer extends JFrame {
	JPanel p_north;
	JTextField t_port;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	ServerSocket server;// 접속자 감지용 소켓
	int port = 8888;
	Thread serverThread;// main Thread가 대기상태에 빠지지 않게
						// 하기 위한 쓰레드...

	public MultiServer() {
		p_north = new JPanel();
		t_port = new JTextField(Integer.toString(port), 8);
		bt = new JButton("서버가동");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		// 쓰레드 생성 및 재정의!!
		serverThread = new Thread() {
			public void run() {
				runServer();
			}
		};

		p_north.add(t_port);
		p_north.add(bt);
		add(p_north, BorderLayout.NORTH);
		add(scroll);

		// font size
		area.setFont(new Font("돋움", Font.BOLD, 23));

		// 버튼에 리스너 연결
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverThread.start();
			}
		});

		setSize(400, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void runServer() {
		port = Integer.parseInt(t_port.getText());
		try {
			server = new ServerSocket(port);// 서버 생성
			area.append("서버 가동\n");

			while (true) {
				Socket client = server.accept();// 서버 가동(접속자를 기다림)
				String ip = client.getInetAddress().getHostAddress();
				area.append(ip + "님 접속\n");
				
				//접속과 동시에 대화를 나눌 아바타 생성!!!
				ServerThread st = new ServerThread(this, client);
				st.start();//run을 호출
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MultiServer();
	}
}
