package gameClient;

import Server.Game_Server_Ex2;
import api.node_data;
import gameClient.Arena;
import gameClient.CL_Agent;
import gameClient.CL_Pokemon;
import gameClient.MyFrame;
import api.DWGraph_DS;
import api.DWGraph_Algo;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.edge_data;
import api.game_service;
import org.json.JSONException;
import org.json.JSONObject;
//import gameClient.login;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Ex2 extends Arena implements Runnable{
    private static MyFrame _win;
    private static Arena _ar;
    private static dw_graph_algorithms gg = new DWGraph_Algo();

    public static void main(String[] a){
        Thread client = new Thread(new Ex2());
        //Thread loginScreen = new Thread((Runnable) new login());
        //loginScreen.run();
        client.start();
    }
    @Override
    public void run() {
        //int level_num = login.getLevel();
        game_service game = Game_Server_Ex2.getServer(5);
      //  game.login(login.getID());
        String g = game.getGraph();
        String pkm = game.getPokemons();
        gg.load(g);
        directed_weighted_graph graph = gg.getGraph();
        init(game);

        game.startGame();
        _win.setTitle("Ex2 gotta catch them all" + game.toString());
        int ind = 0;
        long dt = 100;

        while (game.isRunning()) {
            moveAgents(game, graph);
        }
    }

    private static void moveAgents(game_service game, directed_weighted_graph gg){
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg,gg);
        _ar.setAgents(log);
        String fs = game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        _ar.setPokemons(ffs);
        for(int i=0; i<log.size();i++){
            CL_Agent ag = log.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            if(dest==-1){
                dest = nextNode(gg,src);
            }
            game.chooseNextEdge(ag.getID(),dest);
            System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
        }
    }
    private static int nextNode(directed_weighted_graph g, int src){
        List<node_data> tempPath;
        double min = 10000;
        Collection<CL_Pokemon> pkmCl = _ar.getPokemons();
        Collection<CL_Agent> agtCl = _ar.getAgents();
        Iterator<CL_Agent> agnItr = agtCl.iterator();
        while (agnItr.hasNext()) {
            CL_Agent agtNow = agnItr.next();
            if(agtNow.get_path().isEmpty()){
                Iterator<CL_Pokemon> pkmItr = pkmCl.iterator();
                while (pkmItr.hasNext()) {
                    CL_Pokemon pkmNow = pkmItr.next();
                    double dista = agtNow.getLocation().distance(pkmNow.getLocation());
                    if (dista < min) {
                        min = dista;
                        agtNow.set_curr_fruit(pkmNow);
                    }
                }
                pkmCl.remove(agtNow.get_curr_fruit());
                int srcNode = agtNow.getSrcNode();

                int fruitDestA = agtNow.get_curr_fruit().get_edge().getSrc();
                int fruitDestB = agtNow.get_curr_fruit().get_edge().getDest();

                if ((fruitDestA < fruitDestB && agtNow.get_curr_fruit().getType() == 1) ||
                        (fruitDestA > fruitDestB && agtNow.get_curr_fruit().getType() == -1)) {

                    tempPath = gg.shortestPath(srcNode, fruitDestA); // need to make sure the fruitDestB is added to end of List
                    tempPath.add(gg.getGraph().getNode(fruitDestB));
                    agtNow.set_path(tempPath);
                } else {
                    tempPath = gg.shortestPath(srcNode, fruitDestB); //need to make sure the fruitDestA is added to end of List
                    tempPath.add(gg.getGraph().getNode(fruitDestA));
                    agtNow.set_path(tempPath);
                }
                int newDest = agtNow.get_path().get(0).getKey();
                agtNow.get_path().remove(0);
                agtNow.setNextNode(newDest);
                return newDest;

            }
            int destPath = agtNow.get_path().get(0).getKey();
            agtNow.get_path().remove(0);
            agtNow.setNextNode(destPath);
            return destPath;
        }
        int destPath = agnItr.next().get_path().get(0).getKey();
        agnItr.next().get_path().remove(0);
        agnItr.next().setNextNode(destPath);
        return destPath;
    }

    private void init(game_service game){
        String g = game.getGraph();
        gg.load(g);
        String fp = game.getPokemons();
        String fa = game.getAgents();

        List<CL_Agent> agnt;
        directed_weighted_graph graph = gg.getGraph();
        agnt = getAgents(fa, graph);
        ArrayList<CL_Pokemon> pkms;
        pkms = json2Pokemons(fp);
        _ar = new Arena();
        _ar.setPokemons(pkms);
        _ar.setAgents(agnt);
        _ar.setGraph(graph);

        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);
        _win.show();
        String info = game.toString();
        JSONObject line;

        try{
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            int src_node = 0; // change to better start location
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for(int a = 0;a<cl_fs.size();a++)
                Arena.updateEdge(cl_fs.get(a),graph);
            for(int a = 0;a<rs;a++) {
                int ind = a%cl_fs.size();
                CL_Pokemon c = cl_fs.get(ind);
                int nn = c.get_edge().getDest();
                if(c.getType()<0 ) {nn = c.get_edge().getSrc();}
                game.addAgent(nn);
            }
        }
        catch (JSONException e) {e.printStackTrace();}
    }
}

