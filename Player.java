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
public class Player {
    private int id;
    private String name;
    private String kit;
    private int kills;
    private int deaths;
    private int suicides;
    private String team;
    private int level;
    private int score;
    private int ping;
    private String ip;
    private int playerId;
    private int vip;
    private int heroId;

    public Player(int id, String name, String kit, int kills, int deaths, int suicides, int team,
                  int level, int score, int ping, String ip, int playerId, int vip, int heroId) {
        this.id = id;
        this.name = name;
        if (kit.equals("NA_Gunner_kit") || kit.equals("RA_Gunner_kit")) this.kit = "Gunner";
        if (kit.equals("NA_Soldier_kit") || kit.equals("RA_Soldier_kit")) this.kit = "Soldier";
        if (kit.equals("NA_Commando_kit") || kit.equals("RA_Commando_kit")) this.kit = "Commando";
        else this.kit = "Dead";
        this.kit = kit;
        this.kills = kills;
        this.deaths = deaths;
        this.suicides = suicides;
        if (team == 1) this.team = "National";
        else this.team = "Royal";
        this.level = level;
        this.score = score;
        this.ping = ping;
        this.ip = ip;
        this.playerId = playerId;
        this.vip = vip;
        this.heroId = heroId;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getKit() { return this.kit; }
    public int getKills() { return this.kills; }
    public int getDeaths() { return this.deaths; }
    public int getSuicides() { return this.suicides; }
    public String getTeam() { return this.team; }
    public int getLevel() { return this.level; }
    public int getScore() { return this.score; }
    public int getPing() { return this.ping; }
    public String getIp() { return this.ip; }
    public int getPlayerId() { return this.playerId; }
    public Boolean isVip() {
        if (this.vip == 1) return true;
        else return false;
    }
    public int getHeroId() { return this.heroId; }
}
