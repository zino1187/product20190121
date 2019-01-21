/*
 * ������ Ŭ���̾�Ʈ�� ���� ���������� �޽����� �ְ� ��������
 * �ϳ��� ���α׷������� ���������� ���డ���� ��������� 
 * �����尡 �ʿ��ϴ�!!
 * */
package p0121.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

//�� Ŭ������ �����ϴ� Ŭ���̾�Ʈ���� 1:1 �����Ͽ� ��ȭ�� ����
//�ƹ�Ÿ�� ����!! ���� ��ȭ�� �ʿ��� ����� ��Ʈ���� �����ؾ� �ϰ�
//�� ��Ʈ���� ���Ӱ� �Բ� �����Ǵ� �������� ���� ���;� �ϱ⶧����
//���ϵ� �����ؾ� �Ѵ�..
public class ServerThread extends Thread{
	MultiServer multiServer;
	Socket client;
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public ServerThread(MultiServer multiServer,Socket client) {
		this.multiServer=multiServer;
		try {
			buffr = new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	//��� 
	public void listen() {
		try {
			String msg=buffr.readLine();
			send(msg);//Ŭ���̾�Ʈ�� ����
			multiServer.area.append(msg+"\n");//��� �����!!
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//���ϱ�
	public void send(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		while(true) {
			listen();
		}
	}
}









