import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/*
        Base for connecting and querying BFH/BFP4F/BF2 servers using RCON.
        Copyright (C) 2013  Scott Davey, www.sd149.co.uk

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

public class ServerBase {
    private Socket server;
    private BufferedReader input;
    private DataOutputStream output;
    private String ip;
    private int port;
    private String pass;

    public ServerBase(String ip, int port, String pass) {
        this.ip = ip;
        this.port = port;
        this.pass =  pass;
    }

    public String getIp() {return ip;}
    public int getPort() {return port;}
    public String getPassword() {return pass;}

    public String connect() throws IOException {
        server = new Socket(ip, port);
        input = new BufferedReader(new InputStreamReader(server.getInputStream()));
        output = new DataOutputStream(server.getOutputStream());
        input.readLine();    //the welcome by modmanager
        String digest = input.readLine().substring(17,33); //always at this position
        digest += this.pass; //add the admin pass on to the digest
        digest = md5(digest);
        output.writeBytes("login "+digest+"\n"); //send it to the server
        input.readLine(); //blank line is output here
        return input.readLine();  //this will tell you if it failed, or succeeded, from the server.
    }

    private void send(String command) throws IOException {
        output.writeBytes("\u0002"+command+"\n");
    }

    private String recv() throws IOException {
        StringBuffer line = new StringBuffer();
        int myChar;
        while ((myChar = input.read()) != 4) {
            line.append((char)myChar);
        }
        return line.toString();
    }

    public String query(String command) throws IOException {
        this.send(command);
        return this.recv();
    }

    public void close() {
        try {
            server.close();
            output.close();
            input.close();
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }
	
	public String getServerName() {
        String s = "";
        try{
            s = this.query("exec sv.serverName");
        } catch (IOException e) {
            e.printStackTrace();
        }
        name = s;
        return s;
    }

    public LinkedList<Player> getPlayers() {
        String res;
        LinkedList<Player> players = null;
        try {
            res = this.query("bf2cc pl");
            players = new LinkedList<Player>();
            String[] rows = res.split("\\r");
            for (int i = 0; i<rows.length; i++) {
                if (rows[i].length() > 3) {
                    String[] playerAtt = rows[i].split("\\t");
                    players.add(
                            new Player(
                                    Integer.parseInt(playerAtt[0]),
                                    playerAtt[1],
                                    playerAtt[34],
                                    Integer.parseInt(playerAtt[4]),
                                    Integer.parseInt(playerAtt[8]),
                                    Integer.parseInt(playerAtt[31]),
                                    Integer.parseInt(playerAtt[36]),
                                    Integer.parseInt(playerAtt[30]),
                                    Integer.parseInt(playerAtt[2]),
                                    Integer.parseInt(playerAtt[39]),
                                    Integer.parseInt(playerAtt[37]),
                                    Integer.parseInt(playerAtt[3]),
                                    playerAtt[18],
                                    playerAtt[47],
                                    Integer.parseInt(playerAtt[46]),
                                    Integer.parseInt(playerAtt[10])
                            )
                    );
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return players;
    }

    public void kickPlayer(int id, String reason) {
        try {
            this.query("kick "+id+" \""+reason+"\"");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	

    private static String md5(String md5) {
        /*
        Source: http://m2tec.be/blog/2010/02/03/java-md5-hex-0093
        I take no credit for any of this, if you use this make sure you reference the above source.
         */
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch (java.io.UnsupportedEncodingException e) {
        }
        return null;
    }

}

