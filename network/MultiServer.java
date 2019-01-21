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
	ServerSocket server;// ������ ������ ����
	int port = 8888;
	Thread serverThread;// main Thread�� �����¿� ������ �ʰ�
						// �ϱ� ���� ������...

	public MultiServer() {
		p_north = new JPanel();
		t_port = new JTextField(Integer.toString(port), 8);
		bt = new JButton("��������");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		// ������ ���� �� ������!!
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
		area.setFont(new Font("����", Font.BOLD, 23));

		// ��ư�� ������ ����
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
			server = new ServerSocket(port);// ���� ����
			area.append("���� ����\n");

			while (true) {
				Socket client = server.accept();// ���� ����(�����ڸ� ��ٸ�)
				String ip = client.getInetAddress().getHostAddress();
				area.append(ip + "�� ����\n");
				
				//���Ӱ� ���ÿ� ��ȭ�� ���� �ƹ�Ÿ ����!!!
				ServerThread st = new ServerThread(this, client);
				st.start();//run�� ȣ��
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MultiServer();
	}
}
