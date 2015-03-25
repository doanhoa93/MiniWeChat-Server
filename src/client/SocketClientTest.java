package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import protocol.ProtoHead;
import protocol.Msg.AddFriendMsg;
import protocol.Msg.DeleteFriendMsg;
import protocol.Msg.GetUserInfoMsg;
import protocol.Msg.LoginMsg;
import protocol.Msg.LogoutMsg;
import protocol.Msg.PersonalSettingsMsg;
import protocol.Msg.RegisterMsg;
import server.NetworkMessage;
import tools.DataTypeTranslater;
import tools.Debug;

public class SocketClientTest {

	public static final int HEAD_INT_SIZE = 4;
	public Socket socket;
	public InputStream inputStream;
	public OutputStream outputStream;

<<<<<<< HEAD
	//String host = "192.168.45.17"; // 要连接的服务端IP地址
//	String host = "192.168.45.11"; // 要连接的服务端IP地址
	String host = "192.168.45.34"; // 要连接的服务端IP地址
=======
//	String host = "192.168.45.17"; // 要连接的服务端IP地址
	String host = "192.168.45.11"; // 要连接的服务端IP地址
//	String host = "192.168.45.34"; // 要连接的服务端IP地址
>>>>>>> 【修改缺陷】修改Bug

	int port = 8080; // 要连接的服务端对应的监听端口


	public static void main(String args[]) throws IOException {
		new SocketClientTest();
	}

	public SocketClientTest() throws UnknownHostException, IOException {
		// 为了简单起见，所有的异常都直接往外抛
		String host = "192.168.45.34"; // 要连接的服务端IP地址
		// String host = "192.168.45.37"; // 要连接的服务端IP地址
		int port = 8080; // 要连接的服务端对应的监听端口
		// 与服务端建立连接
		// 测心跳
		// testKeepAlive();
		// socket = new Socket(host, port);
		// inputStream = socket.getInputStream();
		// outputStream = socket.getOutputStream();


		// ������
//		 testKeepAlive();
		// ��ע��
		// testRegister();
		// ���½
//		 testLogin();
		// ���Ը�������
		// testPersonalSettings();

//		 测心跳
		testKeepAlive();
		// 测注册
//		testRegister();
		
		// 测登陆
<<<<<<< HEAD
		//testLogin();
		
=======
//		testLogin();
>>>>>>> 【修改缺陷】修改Bug
		// 测试个人设置
		//testPersonalSettings();
		
		//测查看用户个人信息
		//testGetUserInfo();
		
		//测添加好友
		//testAddFriend();

		//测删除好友
		//testDeleteFriend();
		
		//测退出登录
		testLogout();

		// new Thread(new readThread()).start();
	}

	/**
	 * ���ӷ�����
	 * @throws UnknownHostException
	 * @throws IOException
	 * @author Feng
	 */
	public void link() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
	}


	// 处理服务器回复问题
	public byte[] readFromServer() throws IOException {

		// inputStream = socket.getInputStream();
		byte[] byteArray = new byte[200];
		// System.out.println(in.read(byteArray));
		inputStream.read(byteArray);
		byteArray = cutResult(byteArray);

		// System.out.println("client �յ�Server ������ �� " + byteArray);
		// inputStream.close();

		// System.out.println("client 收到Server 发来的 ： " + byteArray);
//		inputStream.close();

		return byteArray;
	}

	public byte[] readFromServer(InputStream inputStream) throws IOException {
		byte[] byteArray = new byte[200];
		inputStream.read(byteArray);
		return byteArray;
	}

	public void writeToServer(byte[] arrayBytes) throws IOException {
		// outputStream = socket.getOutputStream();
		outputStream.write(arrayBytes);
		// outputStream.close();
	}

	public void writeToServer(OutputStream outputStreams, byte[] arrayBytes) throws IOException {
		// outputStream = socket.getOutputStream();
		outputStream.write(arrayBytes);
		// outputStream.close();
	}

	/**
	 * 永久读线程
	 * 
	 * @author Feng
	 * 
	 */
	class readThread implements Runnable {
		@Override
		public void run() {
			try {
				// socket = new Socket(host, port);
				while (true) {
					Thread.sleep(1000);
					byte[] arrayBytes = readFromServer();
					System.out.println("client 收到Server 发来的 ： " + arrayBytes);

					System.out.println("size:" + DataTypeTranslater.bytesToInt(arrayBytes, 0));
					System.out.println("Type:"
							+ ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(arrayBytes, HEAD_INT_SIZE))
									.toString());

					// 发回去
					writeToServer(arrayBytes);

				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 测试心跳功能
	 */
	public void testKeepAlive() {
		System.out.println("Start Test KeepAliveSyc!");
		try {
			socket = new Socket(host, port);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			byte[] byteArray1, byteArray2;
			// outputStream = socket.getOutputStream();
			// writeToServer(byteArray);

			// inputStream = socket.getInputStream();
			while (true) {
				byteArray1 = readFromServer();
				int size = DataTypeTranslater.bytesToInt(byteArray1, 0);
				System.out.println("size: " + size);

				ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray1,
						HEAD_INT_SIZE));
				System.out.println("Type : " + type.toString());

				if (type == ProtoHead.ENetworkMessage.KEEP_ALIVE_SYNC) {
					byteArray2 = new byte[size];
					for (int i = 0; i < size; i++)
						byteArray2[i] = byteArray1[i];

					Debug.log("回复心跳包");
					writeToServer(byteArray2);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试注册功能
	 * 
	 * @author Feng
	 */
	public void testRegister() {
		RegisterMsg.RegisterReq.Builder builder = RegisterMsg.RegisterReq.newBuilder();
		builder.setUserId("a3");
		builder.setUserPassword("aa");
		builder.setUserName("aaa3");
		System.out.println("Start Test Register!");
		try {
			socket = new Socket(host, port);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			byte[] byteArray = NetworkMessage.packMessage(ProtoHead.ENetworkMessage.REGISTER_REQ.getNumber(), builder.build()
					.toByteArray());
			System.out.println("MessageID : " + NetworkMessage.getMessageID(byteArray));
			writeToServer(byteArray);

			while (true) {
				byteArray = readFromServer();
				int size = DataTypeTranslater.bytesToInt(byteArray, 0);
				System.out.println("size: " + size);

				ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
						HEAD_INT_SIZE));
				System.out.println("Type : " + type.toString());

				if (type == ProtoHead.ENetworkMessage.REGISTER_RSP) {
					byte[] objBytes = new byte[size - NetworkMessage.getMessageObjectStartIndex()];
					for (int i = 0; i < objBytes.length; i++)
						objBytes[i] = byteArray[NetworkMessage.getMessageObjectStartIndex() + i];

					RegisterMsg.RegisterRsp response = RegisterMsg.RegisterRsp.parseFrom(objBytes);

					System.out.println("Response : "
							+ RegisterMsg.RegisterRsp.ResultCode.valueOf(response.getResultCode().getNumber()));
					System.out.println("MessageID : " + NetworkMessage.getMessageID(byteArray));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试注册功能(由JUnit调用)
	 * @author Feng
	 * @return
	 * @throws IOException
	 */
	public byte[] testRegister_JUint(String userId, String userPassword, String userName) throws IOException {
		RegisterMsg.RegisterReq.Builder builder = RegisterMsg.RegisterReq.newBuilder();
		builder.setUserId(userId);
		builder.setUserPassword(userPassword);
		builder.setUserName(userName);

		byte[] byteArray = NetworkMessage.packMessage(ProtoHead.ENetworkMessage.REGISTER_REQ.getNumber(), builder.build()
				.toByteArray());
		// System.out.println("MessageID : " +
		// NetworkMessage.getMessageID(byteArray));
		writeToServer(outputStream, byteArray);

		while (true) {
			byteArray = readFromServer(inputStream);
			if (NetworkMessage.getMessageType(byteArray) != ProtoHead.ENetworkMessage.REGISTER_RSP)
				continue;

			return cutResult(byteArray);
		}
	}

	/**
	 * 测试登陆功能(由JUnit调用)
	 * 
	 * @param userId
	 * @param userPassword
	 * @return
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public byte[] testLogin_JUint(String userId, String userPassword) throws UnknownHostException, IOException {
		LoginMsg.LoginReq.Builder builder = LoginMsg.LoginReq.newBuilder();
		builder.setUserId(userId);
		builder.setUserPassword(userPassword);

		byte[] byteArray = NetworkMessage.packMessage(ProtoHead.ENetworkMessage.LOGIN_REQ.getNumber(), builder.build()
				.toByteArray());
		// outputStream = socket.getOutputStream();
		writeToServer(byteArray);

		// inputStream = socket.getInputStream();
		while (true) {
			byteArray = readFromServer();

			ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
					HEAD_INT_SIZE));

			if (type == ProtoHead.ENetworkMessage.LOGIN_RSP) {
				return byteArray;
			}
		}
	}

	/**
	 * 测试登陆功能
	 */
	public void testLogin() {

		LoginMsg.LoginReq.Builder builder = LoginMsg.LoginReq.newBuilder();
		builder.setUserId("a");
		builder.setUserPassword("aa");
		System.out.println("Start Test Login!");
		try {
			socket = new Socket(host, port);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			byte[] byteArray = NetworkMessage.packMessage(ProtoHead.ENetworkMessage.LOGIN_REQ.getNumber(), builder.build()
					.toByteArray());
			// outputStream = socket.getOutputStream();
			writeToServer(byteArray);

			// inputStream = socket.getInputStream();
			while (true) {
				byteArray = readFromServer();
				int size = DataTypeTranslater.bytesToInt(byteArray, 0);
				System.out.println("size: " + size);

				ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
						HEAD_INT_SIZE));
				System.out.println("Type : " + type.toString());

				if (type == ProtoHead.ENetworkMessage.LOGIN_RSP) {
					byte[] objBytes = new byte[size - NetworkMessage.getMessageObjectStartIndex()];
					for (int i = 0; i < objBytes.length; i++)
						objBytes[i] = byteArray[NetworkMessage.getMessageObjectStartIndex() + i];

					LoginMsg.LoginRsp response = LoginMsg.LoginRsp.parseFrom(objBytes);

					System.out
							.println("Response : " + LoginMsg.LoginRsp.ResultCode.valueOf(response.getResultCode().getNumber()));
					break;
				}
			}
			inputStream.close();
			outputStream.close();
			socket.close();
			link();
			//断后重测
			System.out.println("断后重测");
			byteArray = NetworkMessage.packMessage(ProtoHead.ENetworkMessage.LOGIN_REQ.getNumber(), builder.build()
					.toByteArray());
			writeToServer(byteArray);
			while (true) {
				byteArray = readFromServer();
				int size = DataTypeTranslater.bytesToInt(byteArray, 0);
				System.out.println("size: " + size);

				ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
						HEAD_INT_SIZE));
				System.out.println("Type : " + type.toString());

				if (type == ProtoHead.ENetworkMessage.LOGIN_RSP) {
					byte[] objBytes = new byte[size - NetworkMessage.getMessageObjectStartIndex()];
					for (int i = 0; i < objBytes.length; i++)
						objBytes[i] = byteArray[NetworkMessage.getMessageObjectStartIndex() + i];

					LoginMsg.LoginRsp response = LoginMsg.LoginRsp.parseFrom(objBytes);

					System.out
							.println("Response : " + LoginMsg.LoginRsp.ResultCode.valueOf(response.getResultCode().getNumber()));
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试个人设置功能
	 * @author WangFei
	 */
	public void testPersonalSettings() {
		PersonalSettingsMsg.PersonalSettingsReq.Builder builder = PersonalSettingsMsg.PersonalSettingsReq.newBuilder();
		builder.setUserName("bbbss");
		builder.setUserPassword("s123");
		builder.setHeadIndex(1);
		System.out.println("start personalSettings test! ----------------------------");
		try {
			Socket socket = new Socket(host, port);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			LoginMsg.LoginReq.Builder loginBuilder = LoginMsg.LoginReq.newBuilder();
			loginBuilder.setUserId("a");
			loginBuilder.setUserPassword("aa");
			byte[] loginByteArray = NetworkMessage.packMessage(ProtoHead.ENetworkMessage.LOGIN_REQ.getNumber(), loginBuilder.build()
					.toByteArray());
			writeToServer(loginByteArray);
			
			byte[] byteArray = NetworkMessage.packMessage(ProtoHead.ENetworkMessage.PERSONALSETTINGS_REQ.getNumber(), builder
					.build().toByteArray());
			writeToServer(byteArray);
			while (true) {
				byteArray = readFromServer();
				int size = DataTypeTranslater.bytesToInt(byteArray, 0);
				System.out.println("size: " + size);

				ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
						HEAD_INT_SIZE));
				System.out.println("Type : " + type.toString());

				if (type == ProtoHead.ENetworkMessage.PERSONALSETTINGS_RSP) {
					byte[] objBytes = new byte[size - NetworkMessage.getMessageObjectStartIndex()];
					for (int i = 0; i < objBytes.length; i++)
						objBytes[i] = byteArray[NetworkMessage.getMessageObjectStartIndex() + i];

					PersonalSettingsMsg.PersonalSettingsRsp response = PersonalSettingsMsg.PersonalSettingsRsp
							.parseFrom(objBytes);

					System.out.println("Response : "
							+ PersonalSettingsMsg.PersonalSettingsRsp.ResultCode.valueOf(response.getResultCode().getNumber()));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试个人设置--JUnit调用
	 * @param userName
	 * @param userPassword
	 * @return
	 * @throws IOException
	 * @author wangfei
	 */
	public byte[] testPersonalSettings_JUnit(String userName,String userPassword,int headIndex) throws IOException{
		PersonalSettingsMsg.PersonalSettingsReq.Builder builder = PersonalSettingsMsg.PersonalSettingsReq.newBuilder();
		builder.setUserName(userName);
		builder.setUserPassword(userPassword);
		builder.setHeadIndex(headIndex);
		byte[] byteArray = NetworkMessage.packMessage(ProtoHead.ENetworkMessage.PERSONALSETTINGS_REQ.getNumber(), builder
				.build().toByteArray());
		writeToServer(byteArray);
		while(true){
			byteArray = readFromServer();
			ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
					HEAD_INT_SIZE));
			if (type == ProtoHead.ENetworkMessage.PERSONALSETTINGS_RSP) {
				return byteArray;
			}
		}
		
		
	}

	/**
	 * 用于剪切从服务器发过来的byte[]
	 * @param byteArray
	 * @return
	 */
	public byte[] cutResult(byte[] byteArray) {
		int size = DataTypeTranslater.bytesToInt(byteArray, 0);
		byte[] result = new byte[size];
		for (int i = 0; i < size; i++)
			result[i] = byteArray[i];

		return result;
	}
	
	/**
	 * 测试获取用户信息功能
	 * @author wangfei
	 */
	public void testGetUserInfo(){
		GetUserInfoMsg.GetUserInfoReq.Builder builder = GetUserInfoMsg.GetUserInfoReq.newBuilder();
		builder.setTargetUserId("Fuck");
		System.out.println("start test SearchUser! -----------------------");
		try{
			Socket socket = new Socket(host,port);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			LoginMsg.LoginReq.Builder loginBuilder = LoginMsg.LoginReq.newBuilder();
			loginBuilder.setUserId("a");
			loginBuilder.setUserPassword("aa");
			byte[] loginByteArray = NetworkMessage.packMessage(ProtoHead.ENetworkMessage.LOGIN_REQ.getNumber(), loginBuilder.build()
					.toByteArray());
			writeToServer(loginByteArray);
			
			byte[] byteArray =NetworkMessage.packMessage(ProtoHead.ENetworkMessage.GETUSERINFO_REQ.getNumber(), 
					builder.build().toByteArray());
			
			writeToServer(byteArray);
			while(true){
				byteArray = readFromServer();
				int size = DataTypeTranslater.bytesToInt(byteArray, 0);
				System.out.println("size: " + size);

				ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
						HEAD_INT_SIZE));
				System.out.println("Type : " + type.toString());

				if (type == ProtoHead.ENetworkMessage.GETUSERINFO_RSP) {
					byte[] objBytes = new byte[size - NetworkMessage.getMessageObjectStartIndex()];
					for (int i = 0; i < objBytes.length; i++)
						objBytes[i] = byteArray[NetworkMessage.getMessageObjectStartIndex() + i];

					GetUserInfoMsg.GetUserInfoRsp response = GetUserInfoMsg.GetUserInfoRsp.parseFrom(objBytes);

					System.out.println("Response : "
							+ GetUserInfoMsg.GetUserInfoRsp.ResultCode.valueOf(response.getResultCode().getNumber()));
					if(response.getResultCode().equals(GetUserInfoMsg.GetUserInfoRsp.ResultCode.SUCCESS)){
						System.out.println("searchResult UserId:"+response.getUserItem().getUserId()+"  UserName:"+response.getUserItem().getUserName());
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试获取用户信息--JUnit调用
	 * @param targetUserId
	 * @return
	 * @throws IOException
	 * @author wangfei
	 */
	public byte[] testGetUserInfo_JUnit(String targetUserId) throws IOException{
		GetUserInfoMsg.GetUserInfoReq.Builder builder = GetUserInfoMsg.GetUserInfoReq.newBuilder();
		builder.setTargetUserId(targetUserId);
		byte[] byteArray =NetworkMessage.packMessage(ProtoHead.ENetworkMessage.GETUSERINFO_REQ.getNumber(), 
				builder.build().toByteArray());
		writeToServer(byteArray);
		while(true){
			byteArray = readFromServer();
			ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
					HEAD_INT_SIZE));
			if (type == ProtoHead.ENetworkMessage.GETUSERINFO_RSP) {
				return byteArray;
			}
		}
		
	}
	
	/**
	 * 测试添加好友功能
	 * @author wangfei
	 */
	public void testAddFriend(){
		AddFriendMsg.AddFriendReq.Builder builder = AddFriendMsg.AddFriendReq.newBuilder();
		builder.setFriendUserId("a3");
		System.out.println("start test AddFriend! -----------------------");
		try{
			Socket socket = new Socket(host,port);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			LoginMsg.LoginReq.Builder loginBuilder = LoginMsg.LoginReq.newBuilder();
			loginBuilder.setUserId("a2");
			loginBuilder.setUserPassword("aa");
			byte[] loginByteArray = NetworkMessage.packMessage(ProtoHead.ENetworkMessage.LOGIN_REQ.getNumber(), loginBuilder.build()
					.toByteArray());
			writeToServer(loginByteArray);
			
			byte[] byteArray =NetworkMessage.packMessage(ProtoHead.ENetworkMessage.ADDFRIEND_REQ.getNumber(), 
					builder.build().toByteArray());
			
			writeToServer(byteArray);
			while(true){
				byteArray = readFromServer();
				int size = DataTypeTranslater.bytesToInt(byteArray, 0);
				System.out.println("size: " + size);

				ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
						HEAD_INT_SIZE));
				System.out.println("Type : " + type.toString());

				if (type == ProtoHead.ENetworkMessage.ADDFRIEND_RSP) {
					byte[] objBytes = new byte[size - NetworkMessage.getMessageObjectStartIndex()];
					for (int i = 0; i < objBytes.length; i++)
						objBytes[i] = byteArray[NetworkMessage.getMessageObjectStartIndex() + i];
					AddFriendMsg.AddFriendRsp response = AddFriendMsg.AddFriendRsp.parseFrom(objBytes);

					System.out.println("Response : "
							+ AddFriendMsg.AddFriendRsp.ResultCode.valueOf(response.getResultCode().getNumber()));
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试添加好友--JUnit调用
	 * @param friendUserId
	 * @return
	 * @throws IOException
	 */
	public byte[] testAddFriend_JUnit(String friendUserId) throws IOException{
		AddFriendMsg.AddFriendReq.Builder builder = AddFriendMsg.AddFriendReq.newBuilder();
		builder.setFriendUserId(friendUserId);
		
		byte[] byteArray =NetworkMessage.packMessage(ProtoHead.ENetworkMessage.ADDFRIEND_REQ.getNumber(), 
					builder.build().toByteArray());
		writeToServer(byteArray);
		while(true){
			byteArray = readFromServer();
			ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
						HEAD_INT_SIZE));
			if (type == ProtoHead.ENetworkMessage.ADDFRIEND_RSP) {
				return byteArray;
			}
		}
	}
	
	/**
	 * 测试删除好友功能
	 * @author wangfei
	 */
	public void testDeleteFriend(){
		DeleteFriendMsg.DeleteFriendReq.Builder builder = DeleteFriendMsg.DeleteFriendReq.newBuilder();
		builder.setFriendUserId("a1");
		System.out.println("start test DeleteFriend! -----------------------");
		try{				
			Socket socket = new Socket(host,port);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			LoginMsg.LoginReq.Builder loginBuilder = LoginMsg.LoginReq.newBuilder();
			loginBuilder.setUserId("a2");
			loginBuilder.setUserPassword("aa");
			byte[] loginByteArray = NetworkMessage.packMessage(ProtoHead.ENetworkMessage.LOGIN_REQ.getNumber(), loginBuilder.build()
					.toByteArray());
			writeToServer(loginByteArray);
			byte[] byteArray =NetworkMessage.packMessage(ProtoHead.ENetworkMessage.DELETEFRIEND_REQ.getNumber(), 
					builder.build().toByteArray());
		
			writeToServer(byteArray);
			while(true){
				byteArray = readFromServer();
				int size = DataTypeTranslater.bytesToInt(byteArray, 0);
				System.out.println("size: " + size);

				ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
						HEAD_INT_SIZE));
				System.out.println("Type : " + type.toString());

				if (type == ProtoHead.ENetworkMessage.DELETEFRIEND_RSP) {
					byte[] objBytes = new byte[size - NetworkMessage.getMessageObjectStartIndex()];
					for (int i = 0; i < objBytes.length; i++)
						objBytes[i] = byteArray[NetworkMessage.getMessageObjectStartIndex() + i];
					DeleteFriendMsg.DeleteFriendRsp response = DeleteFriendMsg.DeleteFriendRsp.parseFrom(objBytes);

					System.out.println("Response : "
							+ DeleteFriendMsg.DeleteFriendRsp.ResultCode.valueOf(response.getResultCode().getNumber()));
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试删除好友--JUnit调用
	 * @param friendUserId
	 * @return
	 * @throws IOException
	 * @author wangfei
	 */
	public byte[] testDeleteFriend_JUnit(String friendUserId) throws IOException{
		DeleteFriendMsg.DeleteFriendReq.Builder builder = DeleteFriendMsg.DeleteFriendReq.newBuilder();
		builder.setFriendUserId(friendUserId);
		byte[] byteArray =NetworkMessage.packMessage(ProtoHead.ENetworkMessage.DELETEFRIEND_REQ.getNumber(), 
				builder.build().toByteArray());
		writeToServer(byteArray);
		while(true){
			byteArray = readFromServer();
			ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
					HEAD_INT_SIZE));
			if (type == ProtoHead.ENetworkMessage.DELETEFRIEND_RSP) {
				return byteArray;
			}
		}
	}
	
	public void testLogout(){
		LogoutMsg.LogoutReq.Builder builder = LogoutMsg.LogoutReq.newBuilder();
		try{
			Socket socket = new Socket(host,port);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			LoginMsg.LoginReq.Builder loginBuilder = LoginMsg.LoginReq.newBuilder();
			loginBuilder.setUserId("a2");
			loginBuilder.setUserPassword("aa");
			byte[] loginByteArray = NetworkMessage.packMessage(ProtoHead.ENetworkMessage.LOGIN_REQ.getNumber(), loginBuilder.build()
					.toByteArray());
			writeToServer(loginByteArray);
			byte[] byteArray =NetworkMessage.packMessage(ProtoHead.ENetworkMessage.LOGOUT_REQ.getNumber(), 
					builder.build().toByteArray());
		
			writeToServer(byteArray);
			while(true){
				byteArray = readFromServer();
				int size = DataTypeTranslater.bytesToInt(byteArray, 0);
				ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
						HEAD_INT_SIZE));
				if (type == ProtoHead.ENetworkMessage.LOGOUT_RSP) {
					byte[] objBytes = new byte[size - NetworkMessage.getMessageObjectStartIndex()];
					for (int i = 0; i < objBytes.length; i++)
						objBytes[i] = byteArray[NetworkMessage.getMessageObjectStartIndex() + i];
					LogoutMsg.LogoutRsp response = LogoutMsg.LogoutRsp.parseFrom(objBytes);
					System.out.println("Response : "
							+ LogoutMsg.LogoutRsp.ResultCode.valueOf(response.getResultCode().getNumber()));
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试退出登录--JUnit调用
	 * @return
	 * @throws IOException
	 * @author wangfei
	 */
	public byte[] testLogout_JUnit() throws IOException{
		LogoutMsg.LogoutReq.Builder builder = LogoutMsg.LogoutReq.newBuilder();
		byte[] byteArray =NetworkMessage.packMessage(ProtoHead.ENetworkMessage.LOGOUT_REQ.getNumber(), 
				builder.build().toByteArray());
		writeToServer(byteArray);
		while(true){
			byteArray = readFromServer();
			ProtoHead.ENetworkMessage type = ProtoHead.ENetworkMessage.valueOf(DataTypeTranslater.bytesToInt(byteArray,
					HEAD_INT_SIZE));
			if (type == ProtoHead.ENetworkMessage.LOGOUT_RSP) {
				return byteArray;
			}
		}
	}
	
}
