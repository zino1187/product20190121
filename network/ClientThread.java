/* 이 클래스 정의 목적!!
 * 클라이언트가 서버로부터 메시지를 받는 타이밍은 
 * 메세지를 보낼때 뿐만 아니라, 가만이 있어도 메시지를 받아야 하므로,
 * 무한루프 및 비동기방식으로 처리해야 한다..
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
	Socket client;//대화용 소켓
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
	//서버에 메시지 보내기 
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();//flush() 를 출력에만 적용한다!!
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//서버의 메세지 받기!! (입력)
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









