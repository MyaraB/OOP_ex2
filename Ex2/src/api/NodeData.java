package api;

import gameClient.util.Point3D;

public class NodeData implements node_data {
    private geo_location location;
    private double weight;
    private int tag, key;
    private String info;
    private static int unique = 0;
    public NodeData(){
        this.key = unique;
        this.info = "";
        this.tag = 0;
        this.weight = 0;
        location = null;
        unique++;
    }
    public NodeData(node_data n){
        key = n.getKey();
        info = n.getInfo();
        tag = n.getTag();
        weight = n.getWeight();
        setLocation(n.getLocation());
    }
    public NodeData(int key){
        this.key = key;
    }
    @Override
    public int getKey() {
        return key;
    }

    @Override
    public geo_location getLocation() {
        return location;
    }

    @Override
    public void setLocation(geo_location p) {
        location = new Point3D(p.x(), p.y(), p.z());
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        weight = w;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        info = s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        tag = t;
    }
}

