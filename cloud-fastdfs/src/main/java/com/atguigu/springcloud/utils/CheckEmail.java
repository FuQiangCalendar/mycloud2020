package com.atguigu.springcloud.utils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.StringJoiner;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.telnet.TelnetClient;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

import lombok.extern.slf4j.Slf4j;
 
@Slf4j
public class CheckEmail {
	
//	public static final String SENDER_EMAIL = "no-reply@domain.com";//"no-reply@domain.com";
	
	public static final String SENDER_EMAIL = "2295827430@qq.com";//"no-reply@domain.com";
	public static final String SENDER_EMAIL_SERVER = SENDER_EMAIL.split("@")[1];//"domain.com";
	public static final String PASS_WORD = "gzustqkzlfewebdi"; //xwxedweikptadife
	public static final int CONNECT_TIMEOUT = 8000;
	
	
	/**
	 * 
	 * @param email  The recipient's email address, it need to be validate if it is real exists or doesn't exists.
	 * @return True if email is real exists, false if not.
	 */
	public boolean checkEmailMethod(String email) {
		if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
			System.err.println("Format error");
			return false;
		}
 
		String log = "";
		String host = "";
		String hostName = email.split("@")[1];
		Record[] result = null;
		SMTPClient client = new SMTPClient();
		client.setConnectTimeout(8000);  //设置连接超时时间,有些服务器比较慢
 
		try {
			// 查找MX记录
			Lookup lookup = new Lookup(hostName, Type.MX);
			lookup.run();
			if (lookup.getResult() != Lookup.SUCCESSFUL) {
				log += "找不到MX记录\n";
				return false;
			} else {
				result = lookup.getAnswers();
			}
/*
			 if(result.length > 1) { // 优先级排序
	                List<Record> arrRecords = new ArrayList<Record>();
	                Collections.addAll(arrRecords, result);
	                Collections.sort(arrRecords, new Comparator<Record>() {
	                    
	                    public int compare(Record o1, Record o2) {
	                        return new CompareToBuilder().append(((MXRecord)o1).getPriority(), ((MXRecord)o2).getPriority()).toComparison();
	                    }
	                    
	                });
	                host = ((MXRecord)arrRecords.get(0)).getTarget().toString();
	            }
 * 
 */
			// 连接到邮箱服务器
			
			for (int i = 0; i < result.length; i++) {
				System.out.println(result[i].getAdditionalName().toString());
				System.out.println(((MXRecord)result[i]).getPriority());
			}
			int count=0;  
//			String tempLog ="";
			for (int i = 0; i < result.length; i++) {
				log="";
				host = result[i].getAdditionalName().toString();
				try{
					client.connect(host);	//连接到接收邮箱地址的邮箱服务器
				}catch(Exception e){		//捕捉连接超时的抛出的异常
					count++;
					if(count>=result.length){	//如果由MX得到的result服务器都连接不上，则认定email无效
						log +="Connect mail server timeout...\n";
						return false;
					}
				}
				
				if (!SMTPReply.isPositiveCompletion(client.getReplyCode())) {	//服务器通信不成功
					client.disconnect();
					continue;
				} else {
					log += "MX record about " + hostName + " exists.\n";
					log += "Connection succeeded to " + host + "\n";
					log += client.getReplyString();
					
					// HELO <$SENDER_EMAIL_SERVER>   //domain.com
					try{
						client.login(SENDER_EMAIL_SERVER);   //这一步可能会出现空指针异常
					}catch(Exception e){
						return false;
					}
					log += ">HELO "+SENDER_EMAIL_SERVER+"\n";
					log += "=" + client.getReplyString();
					
					client.setSender(SENDER_EMAIL);
					if(client.getReplyCode()!=250){		//为解决hotmail有的MX可能出现=550 OU-001 (SNT004-MC1F43) Unfortunately, messages from 116.246.2.245 weren't sent.
						client.disconnect();
						continue;							//把client.login 和client.setSender放在循环体内，这样所有的如果某mx不行就换其他mx，但这样会对无效的邮箱进行所有mx遍历，耗时
					}
					log += ">MAIL FROM: <"+SENDER_EMAIL+">\n";
					log += "=" + client.getReplyString();
					// RCPT TO: <$email>
					try{
						client.addRecipient(email);
					}catch(Exception e){
						return false;
					}
					log += ">RCPT TO: <" + email + ">\n";
					log += "=" + client.getReplyString();
					
					//最后从收件邮箱服务器返回true，说明服务器中能够找到此收件地址，邮箱有效
					if (250 == client.getReplyCode()) {
						return true;
					}
					client.disconnect();
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.disconnect();
			} catch (IOException e) {
			}
			// print log
			System.out.println(log);
		}
		return false;
	}
	
	/**
	 * This method is more accurate than checkEmailMethod(String email);
	 * 
	 * @param email  The recipient's email address, it need to be validate if it is real exists or doesn't exists.
	 * @return True if email is real exists, false if not.
	 */
	public boolean checkEmail(String email){
		/*if(email.split("@")[1].equals("qq.com")){
			if( checkEmailMethod(email) && checkEmailMethod(email) && checkEmailMethod(email)){
				return true;
			}else{
				return false;
			}
		}*/
		return checkEmailMethod(email);
	}
	
	
	public static boolean checkEmailMethod2 (String email) {
		/*if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
			System.err.println("Format error");
			return false;
		}*/
		if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
			System.err.println("Format error");
			return false;
		}
		
		StringJoiner logMsg = new StringJoiner ("\n");
		String host = "";
		String hostName = email.split("@")[1];
		Record[] result = null;
		AuthenticatingSMTPClient client = null;
		
		try {
			client = new AuthenticatingSMTPClient();
			client.setConnectTimeout(CONNECT_TIMEOUT);  //设置连接超时时间,有些服务器比较慢
			// 查找MX记录
			Lookup lookup = new Lookup(hostName, Type.MX);
			lookup.run();
			if (lookup.getResult() != Lookup.SUCCESSFUL) {
				logMsg.add("找不到MX记录");
				return false;
			} else {
				result = lookup.getAnswers();
			}
			
			client.connect("smtp." + hostName);
			if (!SMTPReply.isPositiveCompletion(client.getReplyCode())) {	//服务器通信不成功
				client.disconnect();
				return false;
			} else {
				logMsg.add("MX record about " + hostName + " exists.");
				logMsg.add("Connection succeeded to " + host);
				logMsg.add(client.getReplyString());
				// HELO <$SENDER_EMAIL_SERVER>   //domain.com
				try{
					boolean login = client.login(SENDER_EMAIL_SERVER);   //这一步可能会出现空指针异常
				}catch(Exception e){
					return false;
				}
				
				logMsg.add(">HELO "+SENDER_EMAIL_SERVER);
				logMsg.add("=" + client.getReplyString());
				System.out.println("client.getReplyString() >>>" + client.getReplyString());
				
				client.auth(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN, SENDER_EMAIL, PASS_WORD);
				client.setSender(SENDER_EMAIL);
				if(client.getReplyCode()!=250){		//为解决hotmail有的MX可能出现=550 OU-001 (SNT004-MC1F43) Unfortunately, messages from 116.246.2.245 weren't sent.
					client.disconnect();			//把client.login 和client.setSender放在循环体内，这样所有的如果某mx不行就换其他mx，但这样会对无效的邮箱进行所有mx遍历，耗时
					return false;
				}
				
				logMsg.add(">MAIL FROM: <"+SENDER_EMAIL+">");
				logMsg.add("=" + client.getReplyString());
				// RCPT TO: <$email>
				try{
					client.addRecipient(email);
				}catch(Exception e){
					return false;
				}
				
				logMsg.add(">RCPT TO: <" + email + ">");
				logMsg.add("=" + client.getReplyString());
				
				//最后从收件邮箱服务器返回true，说明服务器中能够找到此收件地址，邮箱有效
				if (250 == client.getReplyCode()) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			try {
				if (client != null) {
					client.disconnect();
				}
			} catch (IOException e) {
			}
			
			log.info(logMsg.toString());
		}
		return false;
	}
	
	
	public static void checkEmailByTelnet () {
		TelnetClient telnetClient = new TelnetClient("smtp.qq.com");
		telnetClient.setConnectTimeout(8000);
		telnetClient.setCharset(Charset.defaultCharset());
	}
	public static void main(String[] args) {
		boolean checkEmailMethod2 = checkEmailMethod2("fq2295827430@126.com");
		System.out.println(checkEmailMethod2);
	}
 
}