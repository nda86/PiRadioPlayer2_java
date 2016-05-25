package org.nda.javafx.piradioplayer2.utils;

import com.jcraft.jsch.*;
import org.nda.javafx.piradioplayer2.Main;
import org.nda.javafx.piradioplayer2.models.ModelPlayer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by NDA on 15.05.2016.
 */
public class SshClient {
    private static SshClient instance;

    private SshClient(){

    }

    public static SshClient getInstance(){
        if (instance == null){
            instance = new SshClient();
        }
        return instance;
    }

    private Session session = null;

    public String sendCommand(String command){
        StringBuilder outputBuffer = new StringBuilder();
        try {
            Channel channel = session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            InputStream commandOutput = channel.getInputStream();
            channel.connect();
            int read = commandOutput.read();
            while (read != 0xffffffff){
                outputBuffer.append((char)read);
                read = commandOutput.read();
            }
            channel.disconnect();
        } catch (JSchException | IOException e) {
            new ErrorHandler(e, null);
        }

        return outputBuffer.toString();
    }

    public void connect(String username, String host, int port, String password){
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
        } catch (JSchException e) {
            new ErrorHandler(e, null);
        }
    }

    public boolean isConnected(){
        return session.isConnected();
    }

    public void disconnect(){
        session.disconnect();
    }
}
