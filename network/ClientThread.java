/* �� Ŭ���� ���� ����!!
 * Ŭ���̾�Ʈ�� �����κ��� �޽����� �޴� Ÿ�̹��� 
 * �޼����� ������ �Ӹ� �ƴ϶�, ������ �־ �޽����� �޾ƾ� �ϹǷ�,
 * ���ѷ��� �� �񵿱������� ó���ؾ� �Ѵ�..
 * */
package p0121.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientThread extends Thread{
	ChatClient chatClient;
	Socket client;//��ȭ�� ����
	BufferedReader buffr=null;
	BufferedWriter buffw=null;
	
	public ClientThread(ChatClient chatClient,Socket client){
		this.chatClient=chatClient;
		try {
			buffr=new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	//������ �޽��� ������ 
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();//flush() �� ��¿��� �����Ѵ�!!
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//������ �޼��� �ޱ�!! (�Է�)
	public void listen() {
		try {
			String msg=buffr.readLine();
			chatClient.area.append(msg+"\n");
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









