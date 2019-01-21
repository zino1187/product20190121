/*
 * 접속한 클라이언트간 서로 독립적으로 메시지를 주고 받으려면
 * 하나의 프로그램내에서 독립적으로 수행가능한 실행단위인 
 * 쓰레드가 필요하다!!
 * */
package p0121.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

//이 클래스는 접속하는 클라이언트마다 1:1 대응하여 대화를 나눌
//아바타와 같다!! 따라서 대화에 필요한 재료인 스트림을 보유해야 하고
//이 스트림은 접속과 함께 생성되는 소켓으로 부터 얻어와야 하기때문에
//소켓도 보유해야 한다..
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
	
	//듣기 
	public void listen() {
		try {
			String msg=buffr.readLine();
			send(msg);//클라이언트에 전송
			multiServer.area.append(msg+"\n");//기록 남기기!!
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//말하기
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









